package com.apin.airline.common.plugin;

import com.apin.util.Generator;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Irvin on 2016/2/22.
 */
public class QiniuPic {
    private String ak="yFnb1L-yqxkEjfjOwiQzb5wsRcIQRoaZUbrhFupD";
    private String sk="vfmYoka7B74ikLzGcgeCqfqlytOskqwU7mMu3QgX";
    private String bucketName="apin-voucher";
    private String publicURi="http://voucher.apin.com/";


    public QiniuPic() {
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getPublicURi() {
        return publicURi;
    }

    public void setPublicURi(String publicURi) {
        this.publicURi = publicURi;
    }
    private Auth auth = Auth.create(ak,sk);
    Zone z = Zone.autoZone();
    Configuration c = new Configuration(z);
    UploadManager uploadManager = new UploadManager(c);
    BucketManager bucketManager = new BucketManager(auth,c);

    public String getUpToken(String fileName) {
        return auth.uploadToken(bucketName,fileName);
    }

    public void upload(String path,String fileName){
        try {
            Response res = uploadManager.put(path, fileName, getUpToken(fileName));
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

    public String downLoad(String fileName){
        return publicURi+fileName;
    }

    public String process(String fileName){
        try {
            FileInfo info = bucketManager.stat(bucketName, fileName);
            return info.hash;
        } catch (QiniuException e) {
            return "no";
        }
    }

    public void delete(String fileName){
        try {
            bucketManager.delete(bucketName, fileName);
        } catch (QiniuException e) {
        }
    }
    public String toFile(String licenseImage){
        String[] licenseStr = licenseImage.split(";base64,");
        String fileName = Generator.uuid() +"."+ licenseStr[0].split("data:image/")[1];
        String path = System.getProperty("user.dir");
        String targetPath = path + fileName;
        String json = licenseStr[1].replace(" ","+");
        if (json == null) { // 图像数据为空
            return fileName;
        }
        try {
            OutputStream out = new FileOutputStream(targetPath);
            byte[] bytes = Base64.decodeBase64(json);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            out.write(bytes);
            out.flush();
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e){

        }
        upload(path + fileName, fileName);
        return fileName;
    }
}
