/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.cache;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service.CobrandContainerService;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service.CobrandSiteMapperService;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;

@Repository
@Transactional(readOnly = true, transactionManager = "oltpTransactionManager")
public class CobrandSiteDetailsFetcher {

	
	private DataSource dataSource;
	
	private static Logger logger = LoggerFactory.getLogger(DefaultCobrandDetailsFetcher.class);
	
	@Autowired
	private CobrandSiteMapperService cobDepSite;

	@Autowired
	private CobrandContainerService cobContainerfetcher;

	public void initialize(DataSource dataSource) {
		logger.info("Inside CobrandSiteDetailsFetcher initialize");
		this.dataSource=dataSource;
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
			try {
				cobContainerfetcher.getCobrandContainerData(jdbcTemplate);
				cobDepSite.mapCobrandSiteData(jdbcTemplate);
			} catch (Exception e) {
				CobrandUtil.printException(logger, e);
			}
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
	}
}
