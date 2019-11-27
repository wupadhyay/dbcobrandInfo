/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */package com.yodlee.iae.commons.dbcobrandinfo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandSiteCacheManager;
import com.yodlee.iae.commons.dbcobrandinfo.cache.DefaultCobrandCacheManager;
import com.yodlee.iae.commons.dbcobrandinfo.config.MELMaster;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 11-Apr-2016
 */
@Component
public class CacheScheduler {

  private static Logger logger = LoggerFactory.getLogger(CacheScheduler.class);

  @Autowired
  private DefaultCobrandCacheManager cobrandCacheManager;
  
  @Autowired
  private CobrandSiteCacheManager cobSiteCacheManager;
  
  @Autowired
  private MELMaster melMaster;

  /**
   *
   */
 
  @Scheduled(initialDelay = 1800000, fixedDelayString = "${tools.dbcobrandinfo.cobrandcache.reschedule.milliseconds:1800000}")
  public void recache() {

		logger.info("re-loading cobrand cache");
		if (melMaster.isMaster()) {
			this.cobrandCacheManager.initialize();
			
		}
		logger.info("re-loading cache done");
	}
  
  @Scheduled(initialDelay = 120000, fixedDelayString = "${tools.dbcobrandinfo.cobrandsitecache.reschedule.milliseconds:2700000}")
  public void cacheCobrandSiteData() {

		logger.info("re-loading cobrand and site cache");
		if (melMaster.isMaster()) {
			this.cobSiteCacheManager.initialize();
			
		}
		logger.info("re-loading cache done for cacheCobrandSiteData");
	}
  
  
  
  
}
