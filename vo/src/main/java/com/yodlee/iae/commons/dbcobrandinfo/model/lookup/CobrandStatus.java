package com.yodlee.iae.commons.dbcobrandinfo.model.lookup;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 9, 2017
 */
public enum CobrandStatus {

  ACTIVE("Active"),

  IN_ACTIVE("Inactive"),

  DELETED("Deleted")

  ;

  String status;

  /**
   * @param status
   */
  private CobrandStatus(final String status) {
    this.status = status;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return this.status;
  }

  /**
   * @param status
   * @return
   */
  public static CobrandStatus fromStatus(final String status) {
    CobrandStatus ret = null;
    for (final CobrandStatus cs : CobrandStatus.values()) {
      if (cs.getStatus().equals(status)) {
        ret = cs;
        break;
      }
    }
    return ret;
  }
}
