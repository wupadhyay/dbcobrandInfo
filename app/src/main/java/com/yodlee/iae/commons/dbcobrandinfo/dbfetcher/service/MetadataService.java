/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.cache.CustomBeanPropertyRowMapper;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobrandResult;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.LocaleResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.QueryConstant;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.SiteMetadata;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;

@Service
@DependsOn("defaultDatasource")
public class MetadataService {
	
	private static Logger logger = LoggerFactory.getLogger(MetadataService.class);
	
	@Autowired
	private CobrandInfoCache cobrandInfoCache;
	
	@Autowired
	@Qualifier("defaultDatasource")
	private DataSource dataSource;

	public void getMetadata() {
		try{
			createLocaleMap();
			createSiteMap();
			getYodleeCobrandName();
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
	}
	
	public void getYodleeCobrandName() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Set<CobrandResult> cobrandName = new HashSet<>();

		cobrandName = new HashSet<>(jdbcTemplate.query(QueryConstant.YODLEE_COBRAND_QUERY,
				new CustomBeanPropertyRowMapper<>(CobrandResult.class)));
		
		for(CobrandResult rs : cobrandName) {
			this.cobrandInfoCache.getCobIdtoNameMap().put(rs.getCobrandId(), rs.getCobrandName());
		}
	}
	
	public void createSiteMap() {
		logger.info("Inside createSiteMap");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			Set<SiteMetadata> siteList = new HashSet<SiteMetadata>();
			siteList = new HashSet<>(jdbcTemplate.query(QueryConstant.SITE_METADATA_QUERY,
					new CustomBeanPropertyRowMapper<>(SiteMetadata.class)));
			for (SiteMetadata rs : siteList) {
				String MC_KEY = rs.getMCKey();
				String siteName = rs.getSiteName();
				if (MC_KEY.contains("new_display_name")) {
					MC_KEY = MC_KEY.substring(MC_KEY.indexOf("site") + 5, MC_KEY.length());
					if (!this.cobrandInfoCache.getSiteNameMap().containsKey(MC_KEY))
						this.cobrandInfoCache.getSiteNameMap().put(MC_KEY, siteName);
				} else {
					MC_KEY = MC_KEY.substring(MC_KEY.indexOf("site") + 5, MC_KEY.length());
					if (!this.cobrandInfoCache.getSiteNameMap().containsKey(MC_KEY))
						this.cobrandInfoCache.getSiteNameMap().put(MC_KEY, siteName);
				}
			}
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.debug("Exiting createSiteMap--" + this.cobrandInfoCache.getSiteNameMap().toString());
	}
	
	public void createLocaleMap() {
		logger.info("Inside createLocaleMap");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			Set<LocaleResultSet> siteList = new HashSet<LocaleResultSet>();
			siteList = new HashSet<>(jdbcTemplate.query(QueryConstant.SITE_SUPPORTED_LOCALE_QUERY,
					new CustomBeanPropertyRowMapper<>(LocaleResultSet.class)));
			for (LocaleResultSet rs : siteList) {
				String site_id = rs.getSiteId();
				String locale_id = rs.getLocaleId();
				String language = rs.getLanguage();
				String country = rs.getCountry();
				String country_name;
				if (country != null) {
					country_name = country.concat("(" + language + ")");
				} else {
					country_name = "US(EN)";
					country = "US";
				}
				if (!this.cobrandInfoCache.getLocaleIdToCountry().containsKey(locale_id)) {
					this.cobrandInfoCache.getLocaleIdToCountry().put(locale_id, country_name);
				}
				if (this.cobrandInfoCache.getSiteToLocaleMap().containsKey(site_id)) {
					List<String> localeList = this.cobrandInfoCache.getSiteToLocaleMap().get(site_id);
					localeList.add(locale_id);
					this.cobrandInfoCache.getSiteToLocaleMap().put(site_id, localeList);
				} else {
					List<String> localeList = new ArrayList<>();
					localeList.add(locale_id);
					this.cobrandInfoCache.getSiteToLocaleMap().put(site_id, localeList);
				}
			}
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.debug("Exiting createLocaleMap--" + this.cobrandInfoCache.getSiteToLocaleMap().toString());
	}
	
	@PostConstruct
	public void init() {
		logger.info("Inside Init method");
		try {
			getMetadata();
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}

	}
	
	
	
	
}
