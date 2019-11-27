/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.test.entities;

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
@Table(name = "SUM_INFO")
public class SumInfo extends BaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = -8288655839796526242L;

  @Id
  @Column(name = "SUM_INFO_ID")
  private Long sumInfoId;

  @Column(name = "CLASS_NAME")
  private String agentName;

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
   * @return the agentName
   */
  public String getAgentName() {
    return this.agentName;
  }

  /**
   * @param agentName
   *          the agentName to set
   */
  public void setAgentName(final String agentName) {
    this.agentName = agentName;
  }
}
