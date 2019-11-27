/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.model.response;

import com.yodlee.iae.framework.model.model.BaseModel;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 16-May-2016
 */
public class DBInfo extends BaseModel {

  /**
   *
   */
  private static final long serialVersionUID = 5005386239854547174L;

  private String hostname;

  private Integer port;

  private String driverType;

  private String name;

  private String username;

  private String password;

  private Boolean oltp;

  private Boolean sr;

  private Boolean deleted;

  private Boolean firemem;

  private Integer environmentId;

  private Integer dataCenterId;

  /**
   * @return the port
   */
  public Integer getPort() {
    return port;
  }

  /**
   * @param port
   *          the port to set
   */
  public void setPort(Integer port) {
    this.port = port;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username
   *          the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the oltp
   */
  public Boolean getOltp() {
    return oltp;
  }

  /**
   * @param oltp
   *          the oltp to set
   */
  public void setOltp(Boolean oltp) {
    this.oltp = oltp;
  }

  /**
   * @return the sr
   */
  public Boolean getSr() {
    return sr;
  }

  /**
   * @param sr
   *          the sr to set
   */
  public void setSr(Boolean sr) {
    this.sr = sr;
  }

  /**
   * @return the deleted
   */
  public Boolean getDeleted() {
    return deleted;
  }

  /**
   * @param deleted
   *          the deleted to set
   */
  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  /**
   * @return the firemem
   */
  public Boolean getFiremem() {
    return firemem;
  }

  /**
   * @param firemem
   *          the firemem to set
   */
  public void setFiremem(Boolean firemem) {
    this.firemem = firemem;
  }

  /**
   * @return the environmentId
   */
  public Integer getEnvironmentId() {
    return environmentId;
  }

  /**
   * @param environmentId
   *          the environmentId to set
   */
  public void setEnvironmentId(Integer environmentId) {
    this.environmentId = environmentId;
  }

  /**
   * @return the dataCenterId
   */
  public Integer getDataCenterId() {
    return dataCenterId;
  }

  /**
   * @param dataCenterId
   *          the dataCenterId to set
   */
  public void setDataCenterId(Integer dataCenterId) {
    this.dataCenterId = dataCenterId;
  }

  /**
   * @return the driverType
   */
  public String getDriverType() {
    return driverType;
  }

  /**
   * @param driverType
   *          the driverType to set
   */
  public void setDriverType(String driverType) {
    this.driverType = driverType;
  }

  /**
   * @return the hostname
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * @param hostname
   *          the hostname to set
   */
  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

}
