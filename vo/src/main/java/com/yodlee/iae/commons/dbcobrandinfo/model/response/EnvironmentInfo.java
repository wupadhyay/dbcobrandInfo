/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.model.response;

import com.yodlee.iae.framework.model.model.BaseModel;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 18-May-2016
 */
public class EnvironmentInfo extends BaseModel {

  /**
   *
   */
  private static final long serialVersionUID = 6300036326225335561L;

  private Integer id;

  private String name;

  public static EnvironmentInfo build() {
    return new EnvironmentInfo();
  }

  /**
   * @return the id
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * @param id
   *          the id to set
   * @return
   */
  public EnvironmentInfo setId(Integer id) {
    this.id = id;
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
  public EnvironmentInfo setName(String name) {
    this.name = name;
    return this;
  }

}
