/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.client.test.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yodlee.iae.framework.entities.BaseEntity;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 11, 2016
 */
@Entity
@Table(name = "CACHE_INFO")
public class CacheInfo extends BaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = -8288655839796526242L;

  @Id
  @Column(name = "CACHE_ITEM_ID")
  private Long cacheItemId;

  @Column(name = "SUM_INFO_ID")
  private Long sumInfoId;

  @Column(name = "CODE")
  private Integer code;

  /**
   * @return the cacheItemId
   */
  public Long getCacheItemId() {
    return this.cacheItemId;
  }

  /**
   * @param cacheItemId
   *          the cacheItemId to set
   */
  public void setCacheItemId(final Long cacheItemId) {
    this.cacheItemId = cacheItemId;
  }

  /**
   * @return the sumInfoId
   */
  public Long getSumInfoId() {
    return this.sumInfoId;
  }

  /**
   * @param sumInfoId
   *          the sumInfoId to set
   */
  public void setSumInfoId(final Long sumInfoId) {
    this.sumInfoId = sumInfoId;
  }

  /**
   * @return the code
   */
  public Integer getCode() {
    return this.code;
  }

  /**
   * @param code
   *          the code to set
   */
  public void setCode(final Integer code) {
    this.code = code;
  }
}
