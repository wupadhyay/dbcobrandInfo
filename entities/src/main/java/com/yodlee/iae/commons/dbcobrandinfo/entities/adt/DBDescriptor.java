/**
 * Copyright (c) 2016 Yodlee Inc. All
 * Rights Reserved. This software is the confidential and proprietary
 * information of Yodlee, Inc. Use is subject to license terms.
 */

package com.yodlee.iae.commons.dbcobrandinfo.entities.adt;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yodlee.iae.framework.entities.BaseEntity;
import com.yodlee.iae.framework.entities.converter.NumberToBooleanConverter;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Mar 2, 2016
 */
@Entity
@Table(name = "DEPLOY_DB_DESCRIPTOR")
public class DBDescriptor extends BaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = -9187538046392785246L;

  @Id
  @Column(name = "DEPLOY_DB_DESCRIPTOR_ID")
  private Long descriptorId;

  @Column(name = "JDBC_DRIVER_NAME")
  private String driverName;

  @Column(name = "JDBC_DRIVER_TYPE")
  private String driverType;

  @Column(name = "HOST_ADDRESS")
  private String hostname;

  @Column(name = "PORT_NUMBER")
  private Integer port;

  @Column(name = "DATABASE_SID")
  private String name;

  @Column(name = "ENCRYPTED_USERNAME")
  private String username;

  @Column(name = "ENCRYPTED_PASSWORD")
  private String password;

  @Column(name = "IS_OLTP")
  @Convert(converter = NumberToBooleanConverter.class)
  private Boolean oltp;

  @Column(name = "IS_SR")
  @Convert(converter = NumberToBooleanConverter.class)
  private Boolean sr;

  @Column(name = "IS_DELETED")
  @Convert(converter = NumberToBooleanConverter.class)
  private Boolean deleted;

  @Column(name = "IS_FIREMEM_OLTP")
  @Convert(converter = NumberToBooleanConverter.class)
  private Boolean firemem;

  @Column(name = "FDT_ENVIRONMENT_ID")
  private Integer environmentId;

  @Column(name = "DATA_CENTRE_ID")
  private Integer dataCenterId;

  @Column(name = "SR_OLTP")
  private String srOltp;

  /**
   * @return
   */
  public static DBDescriptor build() {
    return new DBDescriptor();
  }

  /**
   * @return the descriptorId
   */
  public Long getDescriptorId() {
    return this.descriptorId;
  }

  /**
   * @param descriptorId
   *          the descriptorId to set
   * @return
   */
  public DBDescriptor setDescriptorId(final Long descriptorId) {
    this.descriptorId = descriptorId;
    return this;
  }

  /**
   * @return the driverName
   */
  public String getDriverName() {
    return this.driverName;
  }

  /**
   * @param driverName
   *          the driverName to set
   * @return
   */
  public DBDescriptor setDriverName(final String driverName) {
    this.driverName = driverName;
    return this;
  }

  /**
   * @return the driverType
   */
  public String getDriverType() {
    return this.driverType;
  }

  /**
   * @param driverType
   *          the driverType to set
   * @return
   */
  public DBDescriptor setDriverType(final String driverType) {
    this.driverType = driverType;
    return this;
  }

  /**
   * @return the hostname
   */
  public String getHostname() {
    return this.hostname;
  }

  /**
   * @param hostname
   *          the hostname to set
   * @return
   */
  public DBDescriptor setHostname(final String hostname) {
    this.hostname = hostname;
    return this;
  }

  /**
   * @return the port
   */
  public Integer getPort() {
    return this.port;
  }

  /**
   * @param port
   *          the port to set
   * @return
   */
  public DBDescriptor setPort(final Integer port) {
    this.port = port;
    return this;
  }

  /**
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name
   *          the name to set
   * @return
   */
  public DBDescriptor setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * @param username
   *          the username to set
   * @return
   */
  public DBDescriptor setUsername(final String username) {
    this.username = username;
    return this;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * @param password
   *          the password to set
   * @return
   */
  public DBDescriptor setPassword(final String password) {
    this.password = password;
    return this;
  }

  /**
   * @return the oltp
   */
  public Boolean getOltp() {
    return this.oltp;
  }

  /**
   * @param oltp
   *          the oltp to set
   * @return
   */
  public DBDescriptor setOltp(final Boolean oltp) {
    this.oltp = oltp;
    return this;
  }

  /**
   * @return the sr
   */
  public Boolean getSr() {
    return this.sr;
  }

  /**
   * @param sr
   *          the sr to set
   * @return
   */
  public DBDescriptor setSr(final Boolean sr) {
    this.sr = sr;
    return this;
  }

  /**
   * @return the deleted
   */
  public Boolean getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted
   *          the deleted to set
   * @return
   */
  public DBDescriptor setDeleted(final Boolean deleted) {
    this.deleted = deleted;
    return this;
  }

  /**
   * @return the firemem
   */
  public Boolean getFiremem() {
    return this.firemem;
  }

  /**
   * @param firemem
   *          the firemem to set
   * @return
   */
  public DBDescriptor setFiremem(final Boolean firemem) {
    this.firemem = firemem;
    return this;
  }

  /**
   * @return the environmentId
   */
  public Integer getEnvironmentId() {
    return this.environmentId;
  }

  /**
   * @param environmentId
   *          the environmentId to set
   * @return
   */
  public DBDescriptor setEnvironmentId(final Integer environmentId) {
    this.environmentId = environmentId;
    return this;
  }

  /**
   * @return the dataCenterId
   */
  public Integer getDataCenterId() {
    return this.dataCenterId;
  }

  /**
   * @param dataCenterId
   *          the dataCenterId to set
   * @return
   */
  public DBDescriptor setDataCenterId(final Integer dataCenterId) {
    this.dataCenterId = dataCenterId;
    return this;
  }

  /**
   * @return the srOltp
   */
  public String getSrOltp() {
    return this.srOltp;
  }

  /**
   * @param srOltp
   *          the srOltp to set
   * @return
   */
  public DBDescriptor setSrOltp(final String srOltp) {
    this.srOltp = srOltp;
    return this;
  }
}
