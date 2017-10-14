package com.sample;


public class Airway {

  private String id;
  private String iataCode;
  private String companyName;
  private String nationCode;
  private String logoIco;
  private java.sql.Timestamp updateTime;
  private java.sql.Timestamp createdTime;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getIataCode() {
    return iataCode;
  }

  public void setIataCode(String iataCode) {
    this.iataCode = iataCode;
  }


  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }


  public String getNationCode() {
    return nationCode;
  }

  public void setNationCode(String nationCode) {
    this.nationCode = nationCode;
  }


  public String getLogoIco() {
    return logoIco;
  }

  public void setLogoIco(String logoIco) {
    this.logoIco = logoIco;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }


  public java.sql.Timestamp getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(java.sql.Timestamp createdTime) {
    this.createdTime = createdTime;
  }

}
