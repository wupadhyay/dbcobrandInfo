/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.cache;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yodlee.iae.commons.dbcobrandinfo.config.DBConnectionValidator;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainer;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainers;
import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.DBDescriptor;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.tools.modules.crypto.CryptoManager;

import oracle.jdbc.driver.OracleDriver;

@Repository
public class CobrandInfoCache {
	
	private static Logger logger = LoggerFactory.getLogger(CobrandInfoCache.class);
	
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
	
	private final Map<String, DataSource> srdbDatasourceMap = new ConcurrentHashMap<>();
	
	private final Map<String, DBDescriptor> dbDescriptorMap = new LinkedHashMap<>();
	
	private final Map<String, DBDescriptor> srdbDescriptorMap = new LinkedHashMap<>();
	
	private final Set<DBDescriptor> srdbDescriptors = new HashSet<>();
	
	private final Set<DBDescriptor> dbDescriptors = new HashSet<>();

	private HashMap<String,String> siteToPrimaryLocale = new HashMap<String,String>();
	
	private HashMap<String,String> localeIdToCountry=new HashMap<String,String>();
	
	private HashMap<Long, String> cobIdtoNameMap = new HashMap<Long, String>();
	
	private HashMap<String, List<String>> siteToMetaContainerMap = new HashMap<>();
	
	private HashMap<String, List<String>> siteToCobrandListMap = new HashMap<>();
	
	private HashMap<Long, HashMap<String, HashMap<String,CobContainers>>> cobrandDataMap = new HashMap<>();
	
	private HashMap<Long, List<CobContainer>> cobEnabledCont = new HashMap<Long, List<CobContainer>>();
	
	private HashMap<String,String> siteNameMap=new HashMap<>();
	
	private HashMap<String,List<String>> siteToLocaleMap=new HashMap<>();
	
	private final Map<Environment, Set<String>> envMap = new LinkedHashMap<>();
	
	private HashMap<Long, List<String>> cobSupportedContMap = new HashMap<>();

	public Map<String, DataSource> getSrdbDatasourceMap() {
		return srdbDatasourceMap;
	}

	public Map<String, DBDescriptor> getSrdbDescriptorMap() {
		return srdbDescriptorMap;
	}

	public Set<DBDescriptor> getSrdbDescriptors() {
		return srdbDescriptors;
	}
	
	
	public HashMap<Long, String> getCobIdtoNameMap() {
		return cobIdtoNameMap;
	}

	public void setCobIdtoNameMap(HashMap<Long, String> cobIdtoNameMap) {
		this.cobIdtoNameMap = cobIdtoNameMap;
	}

	public HashMap<String, List<String>> getSiteToMetaContainerMap() {
		return siteToMetaContainerMap;
	}

	public void setSiteToMetaContainerMap(HashMap<String, List<String>> siteToMetaContainerMap) {
		this.siteToMetaContainerMap = siteToMetaContainerMap;
	}

	public HashMap<String, List<String>> getSiteToCobrandListMap() {
		return siteToCobrandListMap;
	}

	public void setSiteToCobrandListMap(HashMap<String, List<String>> siteToCobrandListMap) {
		this.siteToCobrandListMap = siteToCobrandListMap;
	}

	
	public HashMap<Long, List<CobContainer>> getCobEnabledCont() {
		return cobEnabledCont;
	}

	public void setCobEnabledCont(HashMap<Long, List<CobContainer>> cobEnabledCont) {
		this.cobEnabledCont = cobEnabledCont;
	}

	public HashMap<Long, HashMap<String, HashMap<String,CobContainers>>> getCobrandDataMap() {
		return cobrandDataMap;
	}

	public void setCobrandDataMap(HashMap<Long, HashMap<String, HashMap<String,CobContainers>>> cobrandDataMap) {
		this.cobrandDataMap = cobrandDataMap;
	}

	public HashMap<String,String> getSiteNameMap() {
		return siteNameMap;
	}

	public void setSiteNameMap(HashMap<String,String> siteNameMap) {
		this.siteNameMap = siteNameMap;
	}

	
	/**
	 * @return the environmentIds
	 */
	public List<Integer> getEnvironmentIds() {
		return this.environmentIds;
	}

	
	
	
	public void createSRDBDataSource(final DBDescriptor db) {
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

		this.srdbDatasourceMap.put(dbName, dataSource);
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

	public Map<String, DataSource> getDatasourceMap() {
		// TODO Auto-generated method stub
		return datasourceMap;
	}

	public Map<Environment, Set<String>> getEnvMap() {
		return envMap;
	}

	public Map<String, DBDescriptor> getDbDescriptorMap() {
		return dbDescriptorMap;
	}

	public Set<DBDescriptor> getDbDescriptors() {
		return dbDescriptors;
	}

	public HashMap<String,String> getSiteToPrimaryLocale() {
		return siteToPrimaryLocale;
	}

	public void setSiteToPrimaryLocale(HashMap<String,String> siteToPrimaryLocale) {
		this.siteToPrimaryLocale = siteToPrimaryLocale;
	}

	public HashMap<String,String> getLocaleIdToCountry() {
		return localeIdToCountry;
	}

	public void setLocaleIdToCountry(HashMap<String,String> localeIdToCountry) {
		this.localeIdToCountry = localeIdToCountry;
	}

	public HashMap<String,List<String>> getSiteToLocaleMap() {
		return siteToLocaleMap;
	}

	public void setSiteToLocaleMap(HashMap<String,List<String>> siteToLocaleMap) {
		this.siteToLocaleMap = siteToLocaleMap;
	}

	
	public void clearCache() {
		siteToMetaContainerMap = new HashMap<>();
		siteToCobrandListMap = new HashMap<>();
		cobrandDataMap = new HashMap<>();
		cobEnabledCont = new HashMap<Long, List<CobContainer>>();
	}

	public HashMap<Long, List<String>> getCobSupportedContMap() {
		return cobSupportedContMap;
	}

	public void setCobSupportedContMap(HashMap<Long, List<String>> cobSupportedContMap) {
		this.cobSupportedContMap = cobSupportedContMap;
	}
	



}
