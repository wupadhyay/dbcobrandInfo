/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.client;

import static com.yodlee.iae.commons.dbcobrandinfo.web.ServiceRoutes.*;
import static org.springframework.http.HttpMethod.GET;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.DBInfo;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.EnvironmentInfo;
import com.yodlee.iae.framework.client.http.AbstractExchangeHandler;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 16-May-2016
 */
@Service
@Primary
public class DefaultDBCobrandInfoFetcher extends AbstractExchangeHandler implements DBCobrandInfoFetcher {

  private static Logger logger = LoggerFactory.getLogger(DefaultDBCobrandInfoFetcher.class);

  @Value("${tools.dbcobrandinfo.server.url}")
  private String dbCobrandInfoURI;

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#
   * fetchEnvironments()
   */
  @Override
  public Set<EnvironmentInfo> fetchEnvironments() {
    logger.info("fetching configured environments");
    final String uri = this.dbCobrandInfoURI + ENVIRONMENT;
    return super.exchangeForInfos(uri, GET, EnvironmentInfo.class);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#fetchDBs(
   * boolean)
   */
  @Override
  public Set<DBInfo> fetchDBs(boolean firemem) {
    String uri = this.dbCobrandInfoURI + DATABASE;
    if (firemem) {
      logger.info("fetching dbinfo for all firemem oltp's");
      uri += FIREMEM;
    } else {
      logger.info("fetching dbinfo for all oltp's");
      uri += OLTP;
    }
    return super.exchangeForInfos(uri, GET, DBInfo.class);
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#
   * fetchFirememDB(com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment)
   */
  @Override
  public DBInfo fetchFirememDB(Environment environment) {
    logger.info("fetching dbinfo for firemem oltp in environment: {}", environment.getName());
    final String uri = this.dbCobrandInfoURI + DATABASE + FIREMEM + "/" + environment.getName();
    return super.exchangeForInfoO(uri, GET, DBInfo.class);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#fetchDBs(com
   * .yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment)
   */
  @Override
  public Set<DBInfo> fetchDBs(Environment environment) {
    logger.info("fetching dbinfo for oltp db in environment: {}", environment.getName());
    final String uri = this.dbCobrandInfoURI + DATABASE + OLTP + "/" + environment.getName();
    return super.exchangeForInfos(uri, GET, DBInfo.class);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#fetchDBInfo(
   * java.lang.String, boolean)
   */
  @Override
  public DBInfo fetchDBInfo(String dbname, boolean firemem) {
    String uri = this.dbCobrandInfoURI + DATABASE;
    if (firemem) {
      logger.info("fetching firemem dbinfo for {} db", dbname);
      uri += FIREMEM + "/" + dbname;
    } else {
      logger.info("fetching dbinfo for {} db", dbname);
      uri += OLTP + "/" + dbname;
    }
    return super.exchangeForInfoO(uri, GET, DBInfo.class);
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#
   * fetchCobrands(com.yodlee.iae.commons.dbcobrandinfo.lookup.Environment)
   */
  @Override
  public Set<CobrandInfo> fetchCobrands(Environment environment) {
    logger.info("fetching cobrands for environment: {}", environment.getName());
    final String uri = this.dbCobrandInfoURI + COBRAND + "/" + environment.getName();
    return super.exchangeForInfos(uri, GET, CobrandInfo.class);
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#
   * fetchCobrands(com.yodlee.iae.commons.dbcobrandinfo.lookup.DataCenter)
   */
  @Override
  public Set<CobrandInfo> fetchCobrands(DataCenter dataCenter) {
    logger.info("fetching cobrands for datacenter: {}", dataCenter.getName());
    final String uri = this.dbCobrandInfoURI + COBRAND + "/" + dataCenter.getName();
    return super.exchangeForInfos(uri, GET, CobrandInfo.class);
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#
   * fetchCobrands(java.lang.String)
   */
  @Override
  public Set<CobrandInfo> fetchCobrands(String dbName) {
    logger.info("fetching cobrandInfos for db: {}", dbName);
    final String uri = this.dbCobrandInfoURI + COBRAND + "/" + dbName;
    return super.exchangeForInfos(uri, GET, CobrandInfo.class);
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher#
   * fetchCobrand(java.lang.String, java.lang.Long)
   */
  @Override
  public Set<CobrandInfo> fetchCobrand(String dbName, Long cobrandId) {
    logger.info("fetching cobrandInfo for db: {}; cobrandId: {}", dbName, cobrandId);
    final String uri = this.dbCobrandInfoURI + COBRAND + "/" + dbName + "/" + cobrandId;
    return super.exchangeForInfos(uri, GET, CobrandInfo.class);
  }
}
