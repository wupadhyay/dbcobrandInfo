package com.yodlee.iae.commons.dbcobrandinfo.cache;

import java.util.Set;

import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 9, 2017
 */
public interface CobrandDetailsFetcher {

  /**
   * @return
   */
  Set<CobrandInfo> getCobrandInfos();

  /**
   *
   */
  void initialize();

}
