/**
 * Copyright (c) Yodlee, Inc. All Rights Reserved.
 */ 

package com.yodlee.iae.commons.dbcobrandinfo.client.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yodlee.iae.commons.dbcobrandinfo.client.cache.DefaultCobrandCacheManager;

@Component
public class ClientCacheScheduler {
	
	 private static Logger logger = LoggerFactory.getLogger(ClientCacheScheduler.class);
	
	@Autowired
	  private DefaultCobrandCacheManager cobrandClientCacheManager;
	
	/*
	 * 
	 */
	@Scheduled(initialDelay = 1200000, fixedDelayString = "${tools.dbcobrandinfo.cobrandclientcache.reschedule.milliseconds:1200000}")
	  public void recacheClient() {
	    logger.info("re-caching Client cobrand cache");
	    this.cobrandClientCacheManager.initialize();
	    logger.info("re-caching Client cache done");
	  }

}
