/**
 * Copyright (c) 2016 Yodlee Inc. All Rights
 * Reserved. This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 */

package com.yodlee.iae.commons.dbcobrandinfo.entities.adt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yodlee.iae.framework.entities.BaseEntity;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 9, 2017
 */
@Entity
@Table(name = "DATA_CENTRE_DETAILS")
public class DataCenter extends BaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = 9063004579328089560L;

  @Id
  @Column(name = "DATA_CENTRE_ID")
  private Integer id;

  @Column(name = "DATA_CENTRE_NAME")
  private String name;

  /**
   * @return the id
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(final Integer id) {
    this.id = id;
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
   */
  public void setName(final String name) {
    this.name = name;
  }
}
