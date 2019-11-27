/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.client.provider;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache;
import com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher;
import com.yodlee.iae.framework.context.ApplicationContextProvider;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 24-May-2016
 */
public final class DBCobrandInfoProvider {

  /**
   * @return
   */
  public static CobrandCache getCobrandCache() {
    return ApplicationContextProvider.getContext().getBean(CobrandCache.class);
  }

  /**
   * @return
   */
  public static DBCobrandInfoFetcher getDBCobrandInfoFetcher() {
    return ApplicationContextProvider.getContext().getBean(DBCobrandInfoFetcher.class);
  }
}
