/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.client.cache;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yodlee.iae.commons.dbcobrandinfo.config.DBConnectionValidator;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.DBInfo;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.EnvironmentInfo;
import com.yodlee.tools.modules.crypto.CryptoManager;

import oracle.jdbc.driver.OracleDriver;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 11, 2016
 */
@Repository
public class DBInfoCache {

  private static Logger logger = LoggerFactory.getLogger(DBInfoCache.class);

  @Autowired
  private CryptoManager cryptoManager;

  private Set<EnvironmentInfo> environmentInfos = new HashSet<>();

  private final Set<DBInfo> oltpDBInfos = new HashSet<>();

  private final Set<DBInfo> firememOLTPDBInfos = new HashSet<>();

  private final Map<String, DBInfo> dbInfoMap = new HashMap<>();

  private final Map<String, DataSource> datasourceMap = new ConcurrentHashMap<>();

  /**
   * @return the environmentInfos
   */
  public Set<EnvironmentInfo> getEnvironmentInfos() {
    return this.environmentInfos;
  }

  /**
   * @param environmentInfos
   *          the environmentInfos to set
   */
  public void setEnvironmentInfos(Set<EnvironmentInfo> environmentInfos) {
    this.environmentInfos = environmentInfos;
  }

  /**
   * @return the datasourceMap
   */
  public Map<String, DataSource> getDatasourceMap() {
    return this.datasourceMap;
  }

  /**
   * @return the dbInfoMap
   */
  public DBInfo getDBInfo(String dbName) {
    return this.dbInfoMap.get(dbName);
  }

  /**
   * @return the oltpDBInfos
   */
  public Set<DBInfo> getOltpDBInfos() {
    return this.oltpDBInfos;
  }

  /**
   * @return the firememOLTPDBInfos
   */
  public Set<DBInfo> getFirememOLTPDBInfos() {
    return this.firememOLTPDBInfos;
  }

  /**
   * @param db
   */
  public void createDataSource(final DBInfo db) {
    final String dbName = db.getName();
    String username, password = null;
    try {
      username = this.cryptoManager.decryptDatabaseCredential(db.getUsername());
      password = this.cryptoManager.decryptDatabaseCredential(db.getPassword());
    } catch (final Exception ex) {
      logger.error("Failed to decrypt credentials, unable to create data source for db: " + dbName);
      return;
    }

    final ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setDriverClass(OracleDriver.class.getName());
    } catch (final PropertyVetoException ex) {
      logger.error("Failed to load oracle driver class, unable to create data source for db: " + dbName);
    }

    final String url = this.getUrl(db.getDriverType(), db.getHostname(), username, password, dbName, db.getName(),
        db.getPort());
    if (url == null) {
      return;
    }

    dataSource.setJdbcUrl(url);
    dataSource.setUser(username);
    dataSource.setPassword(password);

    // TODO: Peru - Below configs need to be externalized
    dataSource.setInitialPoolSize(10);
    dataSource.setMinPoolSize(10);
    dataSource.setMaxPoolSize(50);
    dataSource.setAcquireIncrement(3);

    // TODO: Peru - AutoReconnect logic
    dataSource.setPreferredTestQuery("select sysdate from dual");

    // TODO: Peru - Either 1 of below config should exists, but not both;
    // Performance impact
    // TODO: Config: 1
    dataSource.setTestConnectionOnCheckout(true);

    // TODO: Config: 2
    dataSource.setIdleConnectionTestPeriod(300);
    dataSource.setMaxIdleTimeExcessConnections(240);
    dataSource.setTestConnectionOnCheckin(true);

    this.datasourceMap.put(dbName, dataSource);
    this.dbInfoMap.put(dbName, db);

    if (db.getFiremem()) {
      this.firememOLTPDBInfos.add(db);
    } else {
      this.oltpDBInfos.add(db);
    }

    logger.info("created new datasource for db: {}", dbName);
  }

  /**
   * @param username
   * @param password
   * @param dbName
   * @param sid
   * @param sid2
   * @param port
   * @return
   */
  private String getUrl(final String driverType, final String server, final String username, final String password,
      final String dbName, final String sid, final int port) {
    final String urlPrefix = "jdbc:" + driverType + ":@" + server + ":" + port;
    String url = urlPrefix + "/" + sid;
    if (!this.isDBConnectionSuccess(url, username, password, dbName)) {
      logger.info("oracle scan is not enabled for db: {}. Trying to connect via normal way", dbName);
      url = urlPrefix + ":" + sid;
      if (this.isDBConnectionSuccess(url, username, password, dbName)) {
        logger.info("'{}' db connection success via normal url, proceeding further", dbName);
      } else {
        logger.warn("Unable to create datasource either via scan or normal url for db: '{}'", dbName);
        url = null;
      }
    } else {
      logger.info("'{}' db connection success via scan url, proceeding further ", dbName);
    }

    return url;
  }

  /**
   * @param password
   * @param username
   * @param url
   * @param dbName
   * @return
   */
  private boolean isDBConnectionSuccess(final String url, final String username, final String password,
      final String dbName) {
    logger.info("checking '{}' db connectivity with url: {}", dbName, url);
    final DBConnectionValidator helper = new DBConnectionValidator();
    try {
      return helper.check(url, username, password);
    } catch (final Exception ex) {
      logger.info("Failed to validate oracle scan verification:", ex);
    }
    return false;
  }

}
