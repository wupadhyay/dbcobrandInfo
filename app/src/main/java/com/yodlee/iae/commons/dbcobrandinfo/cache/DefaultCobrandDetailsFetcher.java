/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.cache;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.CobrandStatus;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 4, 2017
 */
@Repository
@Scope(SCOPE_PROTOTYPE)
@Transactional(readOnly = true, transactionManager = "oltpTransactionManager")
public class DefaultCobrandDetailsFetcher implements CobrandDetailsFetcher {

	private static Logger logger = LoggerFactory.getLogger(DefaultCobrandDetailsFetcher.class);

	
	protected Set<CobrandInfo> cobrandInfos = null;

	private final String dbName;

	private final Environment environment;

	private final DataCenter dataCenter;

	private final DataSource datsSource;

	

	private final String cobrandFetchQuery = "SELECT COBRAND_ID, COBRAND_NAME, CHANNEL_ID, DEPLOYMENT_MODE,"
			+ "         INITCAP(ENVIRONMENT) ENVIRONMENT_MODE, "
			+ "         INITCAP(COBRAND_STATUS) COBRAND_PRESENT_STATUS, "
			+ "         INITCAP(DECODE(IS_CHANNEL, 1, 'True', 'False')) CHANNEL,"
			+ "         INITCAP(DECODE(IS_CACHERUN_DISABLED, 1, 'True', 'False')) CACHE_RUN_DISABLED,"
			+ "         COBRAND_NAME || DECODE(ENVIRONMENT, 'PRE-PRODUCTION', '- (Private)', '') || ' [' || COBRAND_ID || ']' COBRAND_DISPLAY_NAME,"
			+ "         NVL(IS_SITE_REFRESH_ENABLED, (SELECT INITCAP(DEFAULT_VALUE) FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.SITE_REFRESH.ENABLED')) SITE_REFRESH_ENABLED,"
			+ "         NVL(IS_MFA_ENABLED, (SELECT INITCAP(DEFAULT_VALUE) FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.MFA.ENABLED')) MFA_ENABLED,"
			+ "         NVL(IS_DOCUMENT_DOWNLOAD_ENABLED, (SELECT INITCAP(DEFAULT_VALUE) FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.REFRESH.DOCUMENT_DOWNLOAD.ENABLED')) DOCUMENT_DOWNLOAD_ENABLED,"
			+ "         NVL(TYPE_OF_DOCS_ALLOWED, (SELECT DEFAULT_VALUE FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.REFRESH.DOCUMENT_DOWNLOAD.INITIATION')) TYPE_OF_DOCS_ALLOWED,"
			+ "         NVL(NUMBER_OF_DAYS_DOCUMENTS, (SELECT DEFAULT_VALUE FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.REFRESH.DOCUMENT_DOWNLOAD.NUM_OF_DAYS_DOCS.INACTIVE_USER'))  NUMBER_OF_DAYS_DOCUMENTS,"
			+ "		  NVL(IS_OAUTH_ENABLED, (SELECT INITCAP(DEFAULT_VALUE) FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.OAUTH_BASED_AGGREGATION.ENABLED')) OAUTH_ENABLED"
			+ "   FROM (SELECT COBRAND_ID, C.NAME COBRAND_NAME, COBRAND_STATUS_ID,  "
			+ "           NVL(C.CHANNEL_ID, 0) CHANNEL_ID, C.IS_CHANNEL IS_CHANNEL, C.DEPLOYMENT_MODE, C.ENVIRONMENT ENVIRONMENT, C.IS_CACHERUN_DISABLED IS_CACHERUN_DISABLED,"
			+ "             (SELECT INITCAP(CP.PARAM_VALUE) FROM COB_PARAM CP"
			+ "               WHERE CP.COBRAND_ID = C.COBRAND_ID AND ROWNUM=1 AND CP.PARAM_KEY_ID = (SELECT PARAM_KEY_ID FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.SITE_REFRESH.ENABLED')) IS_SITE_REFRESH_ENABLED,  "
			+ "             (SELECT INITCAP(CP.PARAM_VALUE) FROM COB_PARAM CP"
			+ "               WHERE CP.COBRAND_ID = C.COBRAND_ID AND ROWNUM=1 AND CP.PARAM_KEY_ID = (SELECT PARAM_KEY_ID FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.MFA.ENABLED')) IS_MFA_ENABLED,"
			+ "             (SELECT INITCAP(CP.PARAM_VALUE) FROM COB_PARAM CP"
			+ "               WHERE CP.COBRAND_ID = C.COBRAND_ID AND ROWNUM=1 AND CP.PARAM_KEY_ID = (SELECT PARAM_KEY_ID FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.REFRESH.DOCUMENT_DOWNLOAD.ENABLED')) IS_DOCUMENT_DOWNLOAD_ENABLED,"
			+ "             (SELECT CP.PARAM_VALUE FROM COB_PARAM CP "
			+ "               WHERE CP.COBRAND_ID = C.COBRAND_ID AND ROWNUM=1 AND CP.PARAM_KEY_ID = (SELECT PARAM_KEY_ID FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.REFRESH.DOCUMENT_DOWNLOAD.INITIATION')) TYPE_OF_DOCS_ALLOWED,"
			+ "             (SELECT CP.PARAM_VALUE FROM COB_PARAM CP"
			+ "               WHERE CP.COBRAND_ID = C.COBRAND_ID AND ROWNUM=1 AND CP.PARAM_KEY_ID = (SELECT PARAM_KEY_ID FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.REFRESH.DOCUMENT_DOWNLOAD.NUM_OF_DAYS_DOCS.INACTIVE_USER')) NUMBER_OF_DAYS_DOCUMENTS,"
			+ " 			  (SELECT CP.PARAM_VALUE FROM COB_PARAM CP"
			+ "             WHERE CP.COBRAND_ID = C.COBRAND_ID AND ROWNUM=1 AND CP.PARAM_KEY_ID = (SELECT PARAM_KEY_ID FROM PARAM_KEY WHERE UPPER(PARAM_KEY_NAME) = 'COM.YODLEE.CORE.OAUTH_BASED_AGGREGATION.ENABLED')) IS_OAUTH_ENABLED"
			+ "         FROM COBRAND C ORDER BY COBRAND_ID, CHANNEL_ID) C, COBRAND_STATUS CS WHERE C.COBRAND_STATUS_ID = CS.COBRAND_STATUS_ID";

	/**
	 * @param dbName
	 * @param environment
	 * @param dataCenter
	 * @param dataSource
	 */
	public DefaultCobrandDetailsFetcher(final String dbName, final Environment environment, final DataCenter dataCenter,
			DataSource dataSource) {
		this.dbName = dbName;
		this.environment = environment;
		this.dataCenter = dataCenter;
		this.datsSource = dataSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandDetailsFetcher#
	 * getCobrandInfos()
	 */
	@Override
	public Set<CobrandInfo> getCobrandInfos() {
		return this.cobrandInfos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandDetailsFetcher#initialize()
	 */
	@Override
	public void initialize() {
		this.init();
	}

	/*
	 * Method to get all the connection related details of datasource,to keep a
	 * track
	 */
	public void displayDatasourceData() {
		ComboPooledDataSource cmdd = (ComboPooledDataSource) this.datsSource;
		try {
			if (null != cmdd) {
				logger.info("Below Information is of DataSource: {}", cmdd.getDataSourceName());
				logger.info("Number of Connections: {}", cmdd.getNumConnections());
				logger.info("Number of Idle Connections: {}", cmdd.getNumIdleConnections());
				logger.info("Number of busy connections: {}", cmdd.getNumBusyConnections());
				logger.info("Maximum Pool Size: {}", cmdd.getMaxPoolSize());
				logger.info("Minimum Pool Size: {}", cmdd.getMinPoolSize());
				logger.info("IdleTimeExcessConnections: {}", cmdd.getMaxIdleTimeExcessConnections());
				logger.info("IdleConnectionTestPeriod(): {}", cmdd.getIdleConnectionTestPeriod());
				logger.info("Number of threads waiting checkout: {}", cmdd.getNumThreadsAwaitingCheckoutDefaultUser());
				logger.info("**************************************************************");
			}

		} catch (SQLException e) {
			logger.error("Error when getting the info of pooled conection -- doesnot affect the application");
			logger.error("Error Message : {}", e);
		}
	}

	/**
	 *
	 */
	private void init() {
		logger.info("Inside DefaultCobrandDetailsFetcher initialize");
		displayDatasourceData();
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(this.datsSource);
		
		this.cobrandInfos = new HashSet<>(
				jdbcTemplate.query(this.cobrandFetchQuery, new CustomBeanPropertyRowMapper<>(CobrandInfo.class)));
		this.cobrandInfos.forEach(c -> {
			logger.trace("\t Reading Cobrand from DB: {}; ", c.getCobrandDisplayName());
			c.setDbName(this.dbName).setEnvironment(this.environment).setDataCenter(this.dataCenter)
					.setCobrandStatus(CobrandStatus.fromStatus(c.getCobrandPresentStatus()));
		});
	}

}
