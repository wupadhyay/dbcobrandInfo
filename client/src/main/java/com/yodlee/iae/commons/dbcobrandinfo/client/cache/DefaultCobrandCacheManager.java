
/**
 * Copyright (c) Yodlee, Inc. All Rights Reserved.
 */ 
package com.yodlee.iae.commons.dbcobrandinfo.client.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.yodlee.iae.commons.dbcobrandinfo.cache.AbstractCobrandCache;
import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache;
import com.yodlee.iae.commons.dbcobrandinfo.condition.ConditionalOnCobrandCacheEnabled;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.DBInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 10, 2017
 */
@Service
@Primary
@ConditionalOnCobrandCacheEnabled
public class DefaultCobrandCacheManager extends AbstractCobrandCache implements CobrandCache {

  private static Logger logger = LoggerFactory.getLogger(DefaultCobrandCacheManager.class);

  @Autowired
  private DBInfoCache dBInfoCache;

  @Value("${tools.dbcobrandinfo.cobrandcache.ignore.inactive:true}")
  private boolean ignoreInactive;

  @Value("${tools.dbcobrandinfo.display.ignoring.warning:true}")
  private boolean displayIgnoreWarn;

  /**
   *
   */
  public DefaultCobrandCacheManager() {
    logger.info("Creating cobrand cache...");
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.cache.AbstractCobrandCache#
   * isIgnoreInActive()
   */
  @Override
  protected boolean isIgnoreInActive() {
    return this.ignoreInactive;
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.cache.AbstractCobrandCache#
   * displayIgnoringCobrandDetails()
   */
  @Override
  protected boolean displayIgnoringCobrandDetails() {
    return this.displayIgnoreWarn;
  }

  /**
   *
   */
  @PostConstruct
  public void initialize() {
    this.dBInfoCache.getEnvironmentInfos().forEach(super::initializeRef);

    final Set<DBInfo> oltpDbs = this.dBInfoCache.getOltpDBInfos();
	final List<DBInfo> o1 = new ArrayList<>();
	oltpDbs.forEach(i -> o1.add(i));

	Collections.sort(o1, Comparator.comparing(DBInfo::getDataCenterId));
    for (final DBInfo dbInfo : o1) {
      final String dbName = dbInfo.getName();
      final Environment environment = Environment.from(dbInfo.getEnvironmentId());
      final DataCenter dataCenter = DataCenter.from(dbInfo.getDataCenterId());
      super.initializeDatacenter(dataCenter);

      logger.info("===========================================");
      logger.info("caching cobrands for DB: {}; Environment: {}, Datacenter: {}", dbName, environment.getName(),
          dataCenter.getName());
      super.initializeCobrandCache(dbName, environment, dataCenter);
      super.refreshMaps();
      logger.info("===========================================");  
    }
  }
}
