/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;
import com.yodlee.iae.framework.context.ApplicationContextProvider;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 10, 2016
 */
public class MultiDataSource extends AbstractRoutingDataSource {

  /*
   * (non-Javadoc)
   * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#
   * determineCurrentLookupKey()
   */
  @Override
  protected Object determineCurrentLookupKey() {
    final String ret = DBContext.get().getDbName();
    return ret == null ? this.getDBFromCobrandId() : ret;
  }

  /**
   * @return
   */
  private String getDBFromCobrandId() {
    final Long cobrandId = DBContext.get().getCobrandId();
    final Environment environment = DBContext.get().getEnvironment();
    if ((cobrandId == null) && (environment == null)) {
      return null;
    }
    if ((cobrandId == null) || (environment == null)) {
      throw new IllegalArgumentException("Both CobrandId & Environment values are mandate");
    }

    final CobrandInfo cobrandInfo = ApplicationContextProvider.getContext().getBean(CobrandCache.class)
        .getCobrand(environment, cobrandId);
    return cobrandInfo.getDbName();
  }
}
