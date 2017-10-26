package com.apin.airline.flight.dto;

/**
 * @author huanglei
 * @date 2017/10/25
 * @remark
 */
public class HasListDto {
    /**
     * 包机商id
     */
    private String accountId;
    /**
     * 分销商id
     */
    private String ownerId;

    public HasListDto() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
