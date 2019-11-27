/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.cache;

import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yodlee.iae.commons.dbcobrandinfo.config.DBConnectionValidator;
import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.DBDescriptor;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
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

	@Value("#{'${tools.dbcobrandinfo.environment.supported.ids}'.split(',')}")
	private List<Integer> environmentIds;

	@Value("${tools.dbcobrandinfo.dbinfocache.min.pool.size}")
	private int minPoolSize;

	@Value("${tools.dbcobrandinfo.dbinfocache.max.pool.size}")
	private int maxPoolSize;

	@Value("${tools.dbcobrandinfo.dbinfocache.acquire.increment}")
	private int acquireIncrement;

	@Value("${tools.dbcobrandinfo.dbinfocache.num.helper.threads}")
	private int numHelperthreads;

	@Value("${tools.dbcobrandinfo.dbinfocache.idle.connection.test.period}")
	private int idleConnectionTestPeriod;

	@Value("${tools.dbcobrandinfo.dbinfocache.max.idle.time.excess.connections}")
	private int maxIdleTimeExcessConnections;

	@Value("${tools.dbcobrandinfo.dbinfocache.test.connection.oncheckin}")
	private boolean testConnectionOnCheckin;

	private final Map<String, DataSource> datasourceMap = new ConcurrentHashMap<>();

	

	private final Map<String, DBDescriptor> dbDescriptorMap = new LinkedHashMap<>();

	

	private final Map<String, DBInfo> oltpDBInfoMap = new LinkedHashMap<>();
	
	private final Map<String, DBInfo> firememOLTPDBInfoMap = new LinkedHashMap<>();

	private final Map<Environment, Set<String>> envMap = new LinkedHashMap<>();

	private final Map<Environment, Set<DBInfo>> environmentDBInfos = new LinkedHashMap<>();

	private final Map<Environment, DBInfo> firememEnvironmentDBInfos = new LinkedHashMap<>();

	private final Set<DBDescriptor> dbDescriptors = new HashSet<>();

	

	private final Set<EnvironmentInfo> environmentInfos = new HashSet<>();

	/**
	 * @return the environmentIds
	 */
	public List<Integer> getEnvironmentIds() {
		return this.environmentIds;
	}

	/**
	 * @return the envMap
	 */
	public Map<Environment, Set<String>> getEnvMap() {
		return this.envMap;
	}

	/**
	 * @return the dbDescriptors
	 */
	public Set<DBDescriptor> getDbDescriptors() {
		return this.dbDescriptors;
	}

	
	public Map<String, DBDescriptor> getDbDescriptorMap() {
		return dbDescriptorMap;
	}
	
	/**
	 * @return the datasourceMap
	 */
	public Map<String, DataSource> getDatasourceMap() {
		return this.datasourceMap;
	}


	/**
	 * @return the oltpDBInfoMap
	 */
	public Map<String, DBInfo> getOltpDBInfoMap() {
		return this.oltpDBInfoMap;
	}
	
	/**
	 * @return the environmentInfos
	 */
	public Set<EnvironmentInfo> getEnvironmentInfos() {
		return this.environmentInfos;
	}

	/**
	 * @return the firememOLTPDBInfoMap
	 */
	public Map<String, DBInfo> getFirememOLTPDBInfoMap() {
		return this.firememOLTPDBInfoMap;
	}

	/**
	 * @param environment
	 * @return the firememEnvironmentDBInfos
	 */
	public DBInfo getFirememEnvironmentDBInfos(Environment environment) {
		return this.firememEnvironmentDBInfos.get(environment);
	}

	/**
	 * @return the environmentDBInfos
	 */
	public Map<Environment, Set<DBInfo>> getEnvironmentDBInfos() {
		return this.environmentDBInfos;
	}

	/**
	 *
	 */
	public void post() {
		this.getEnvironmentIds().forEach(id -> {
			final Environment e = Environment.from(id);
			final EnvironmentInfo info = new EnvironmentInfo();
			BeanUtils.copyProperties(e, info);
			this.environmentDBInfos.put(e, new HashSet<>());
			this.environmentInfos.add(info);
		});

		this.getDbDescriptors().forEach(db -> {
			final DBInfo dbInfo = new DBInfo();
			BeanUtils.copyProperties(db, dbInfo);

			if (db.getFiremem()) {
				this.firememEnvironmentDBInfos.put(Environment.from(db.getEnvironmentId()), dbInfo);
				this.firememOLTPDBInfoMap.put(db.getName(), dbInfo);
			} else {
				this.environmentDBInfos.get(Environment.from(db.getEnvironmentId())).add(dbInfo);
				this.oltpDBInfoMap.put(db.getName(), dbInfo);
			}
		});
	}
	

	/**
	 * @param db
	 */
	public void createDataSource(final DBDescriptor db) {
		final String dbName = db.getName();
		String username, password = null;
		try {
			username = this.cryptoManager.decryptDatabaseCredential(db.getUsername());
			password = this.cryptoManager.decryptDatabaseCredential(db.getPassword());
			logger.info("Username---"+username+"--Password--"+password+"---for db--"+dbName+"--HostName--"+db.getHostname()+" "+db.getPort()+" "+db.getName());
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
		// dataSource.setInitialPoolSize(10);
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setAcquireIncrement(acquireIncrement);
		dataSource.setNumHelperThreads(numHelperthreads);

		// TODO: Peru - AutoReconnect logic
		dataSource.setPreferredTestQuery("select sysdate from dual");

		// TODO: Peru - Either 1 of below config should exists, but not both;
		// Performance impact
		// TODO: Config: 1
		// dataSource.setTestConnectionOnCheckout(true);

		// TODO: Config: 2
		dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
		dataSource.setMaxIdleTimeExcessConnections(maxIdleTimeExcessConnections);
		dataSource.setTestConnectionOnCheckin(testConnectionOnCheckin);

		this.datasourceMap.put(dbName, dataSource);
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
