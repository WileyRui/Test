package com.apin.airline.line.utils;

import java.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * @author wileyt
 * @date 2017/10/12
 * @remark fastJson工具类
 */
public class FastJsonUtils {
	/**
	 * 将json格式的字符串 放到map中
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> parseToMap(String jsonStr){
		return parseObjToMap(JSON.parseObject(jsonStr));
	}

	/**
	 * 将obj对象转换成map
	 * @param jsonObj
	 * @return
	 */
	public  static Map<String, String> parseObjToMap(JSONObject jsonObj) {
		/*Map<String, String> m= JSON.parseObject(jsonObj.toJSONString(), LinkedHashMap.class, Feature.OrderedField);  */
		Map<String, String> resultMap = new HashMap<>();
		Iterator<String> it = jsonObj.keySet().iterator();  
		while (it.hasNext()) {
		  String key = it.next();
		  resultMap.put(key, jsonObj.getString(key));
		}
		return resultMap;
	}
	
	
	/**
	 * 将array形式的json 转化成 map 放入list中
	 * @param jsonStr
	 * @return List
	 */
	public static List<Map<String, String>> parseMapInList(String jsonStr){
		JSONArray jsonArray = JSON.parseArray(jsonStr);
		List<Map<String, String>> resultList = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			resultList.add(parseObjToMap((JSONObject)jsonArray.get(i)));
		}
		return resultList;
	}
	
	/*public static void main(String[] args) {
	//	String str="{\"code\":\"10000\",\"charge\":false,\"msg\":\"查询成功\",\"result\":{\"output\":{\"result\":[{\"arrScheduled\":\"2017-06-20T12:10:00Z\",\"arrActual\":\"0001-01-01T00:00:00Z\",\"depEstimated\":\"0001-01-01T00:00:00Z\",\"depTerminal\":\"T2\",\"depActual\":\"0001-01-01T00:00:00Z\",\"arrCode\":\"HKG\",\"depCity\":\"北京首都\",\"depScheduled\":\"2017-06-20T08:25:00Z\",\"arrCity\":\"香港赤鱲角\",\"flightNo\":\"CZ310\",\"rate\":\"43.00\",\"depCode\":\"PEK\",\"arrTerminal\":\"T1\",\"arrEstimated\":\"0001-01-01T00:00:00Z\",\"depPort\":\"BJS\",\"arrPort\":\"HKG\"}]}}}";
	//	System.out.println(FastJsonUtils.parseObjToMap(JSON.parseObject(str)));
		String str="{arrActual=0001-01-01T00:00:00Z, depScheduled=2017-06-01T08:35:00Z, arrCode=HGH, arrCity=杭州萧山, depCity=北京首都, depPort=BJS, depEstimated=0001-01-01T00:00:00Z, depCode=PEK, flightNo=CA1595, arrPort=HGH, arrScheduled=2017-06-01T10:55:00Z, rate=85.67, arrEstimated=0001-01-01T00:00:00Z, depTerminal=T3, arrTerminal=, depActual=0001-01-01T00:00:00Z}";
		System.out.println(JSONObject.parseObject(str));
		
		//System.out.println(FastJsonUtils.parseObjToMap(JSON.parseObject(str)));
		//错误信息没测试
	//	System.out.println(JSON.parseObject(str).get("result"));
	//	Object result =JSON.parseObject(str).get("result");
	//	System.out.println(JSON.parseObject(result.toString()).get("output"));
	//	Object output=JSON.parseObject(result.toString()).get("output");
	////	System.out.println(JSON.parseObject(output.toString()).get("result"));
	//	Object resultSize=JSON.parseObject(output.toString()).get("result");
	//	System.out.println(JSON.parseArray(resultSize.toString()));
	//	System.err.println(FastJsonUtils.parseMapInList(resultSize.toString()));
	
	}*/
	
	
	
}
