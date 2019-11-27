/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.client;

import java.util.Set;

import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.DBInfo;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.EnvironmentInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 16-May-2016
 */
public interface DBCobrandInfoFetcher {

  /**
   * @return
   */
  Set<EnvironmentInfo> fetchEnvironments();

  /**
   * @return
   */
  Set<DBInfo> fetchDBs(boolean firemem);

  /**
   * @return
   */
  Set<DBInfo> fetchDBs(Environment environment);

  /**
   * @param firemem
   * @return
   */
  DBInfo fetchFirememDB(Environment environment);

  /**
   * @param dbname
   * @param firemem
   * @return
   */
  DBInfo fetchDBInfo(String dbname, boolean firemem);

  /**
   * @param environment
   * @return
   */
  Set<CobrandInfo> fetchCobrands(String dbName);

  /**
   * @param dbName
   * @param cobrandId
   * @return
   */
  Set<CobrandInfo> fetchCobrand(String dbName, Long cobrandId);

  /**
   * @param environment
   * @return
   */
  Set<CobrandInfo> fetchCobrands(Environment environment);

  /**
   * @param dataCenter
   * @return
   */
  Set<CobrandInfo> fetchCobrands(DataCenter dataCenter);

}
