/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CustomBeanPropertyRowMapper;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobrandResult;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.QueryConstant;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.SiteResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.handler.CobrandSiteHandler;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;
/**
 * @author wupadhyay
 *
 */
@Service
public class CobrandSiteMapperService {

	private static Logger logger = LoggerFactory.getLogger(CobrandSiteMapperService.class);
	
	@Autowired
	private CobrandSiteHandler siteHandler;

	public void mapCobrandSiteData(JdbcTemplate jdbcTemplate) {
		logger.info("Inside fetchCobSiteData");
		try {
			getCobMap(jdbcTemplate);
			getSiteMap(jdbcTemplate);
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.info("Exiting mapCobrandSiteData");
	}

	
	public void getCobMap(JdbcTemplate jdbcTemplate) {
		logger.info("Inside getCobMap" + new Date(System.currentTimeMillis()));
		try {
			Set<CobrandResult> cobList = new HashSet<CobrandResult>();
			logger.info("Query to be executed--"+QueryConstant.COBRAND_SPECIFIC_SUM_INFO_SITE_QUERY);
			cobList = new HashSet<>(jdbcTemplate.query(QueryConstant.COBRAND_SPECIFIC_SUM_INFO_SITE_QUERY,
					new CustomBeanPropertyRowMapper<>(CobrandResult.class)));
			siteHandler.cobrandMapCreator(cobList);
		} catch (Exception e) {
			// TODO: handle exception
			CobrandUtil.printException(logger, e);
		}
		logger.info("Exiting CobMap" + new Date(System.currentTimeMillis()));
	}

	public void getSiteMap(JdbcTemplate jdbcTemplate) {
		logger.info("Inside getSiteMap");
		try {
			Set<SiteResultSet> siteList = new HashSet<SiteResultSet>();
			siteList = new HashSet<>(jdbcTemplate.query(QueryConstant.SITE_SPECIFIC_DETAILS_QUERY,
					new CustomBeanPropertyRowMapper<>(SiteResultSet.class)));
			siteHandler.siteMapCreator(siteList);
		} catch (Exception e) {
			// TODO: handle exception
			CobrandUtil.printException(logger, e);
		}
		logger.info("Exiting getSiteMap");
	}

}
