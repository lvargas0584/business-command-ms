package com.lamark.business.command.process;

public class Account {

    private Long accountId;

    private String msisdn;

    private Integer statusId;

    private Integer site;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMsisdn() {
        return this.msisdn;
    }

    public void setMsisdn(final String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getStatusId() {
        return this.statusId;
    }

    public void setStatusId(final Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getSite() {
        return this.site;
    }

    public void setSite(final Integer site) {
        this.site = site;
    }


}
