/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.datasource;

import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 21-May-2016
 */
public class ContextInfo {

  private String dbName;

  private Environment environment;

  private Long cobrandId;

  /**
   *
   */
  public ContextInfo() {

  }

  /**
   * @return the dbName
   */
  public String getDbName() {
    return this.dbName;
  }

  /**
   * @param dbName
   *          the dbName to set
   * @return
   */
  public ContextInfo setDbName(String dbName) {
    this.dbName = dbName;
    return this;
  }

  /**
   * @return the environment
   */
  public Environment getEnvironment() {
    return this.environment;
  }

  /**
   * @param environment
   *          the environment to set
   * @return
   */
  public ContextInfo setEnvironment(Environment environment) {
    this.environment = environment;
    return this;
  }

  /**
   * @return the cobrandId
   */
  public Long getCobrandId() {
    return this.cobrandId;
  }

  /**
   * @param cobrandId
   *          the cobrandId to set
   * @return
   */
  public ContextInfo setCobrandId(Long cobrandId) {
    this.cobrandId = cobrandId;
    return this;
  }
}
