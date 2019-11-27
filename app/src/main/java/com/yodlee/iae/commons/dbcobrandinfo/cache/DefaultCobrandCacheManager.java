  
/**
 * Copyright (c) Yodlee, Inc. All Rights Reserved.
 */  

package com.yodlee.iae.commons.dbcobrandinfo.cache;

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
import org.springframework.stereotype.Repository;

import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.DBDescriptor;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 10, 2017
 */
@Repository
public class DefaultCobrandCacheManager extends AbstractCobrandCache implements CobrandCache {

	private static Logger logger = LoggerFactory.getLogger(DefaultCobrandCacheManager.class);

	@Autowired
	private DBInfoCache dBInfoCache;
	
	@Value("${tools.dbcobrandinfo.cobrandcache.ignore.inactive:true}")
	private boolean ignoreInActive;

	/**
	 *
	 */
	public DefaultCobrandCacheManager() {
		logger.info("Creating cobrand cache...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yodlee.iae.commons.dbcobrandinfo.cache.AbstractCobrandCache#
	 * isIgnoreInActive()
	 */
	@Override
	protected boolean isIgnoreInActive() {
		return this.ignoreInActive;
	}

	/**
	 *
	 */
	@PostConstruct
	public void initialize() {
		this.dBInfoCache.getEnvironmentInfos().forEach(super::initializeRef);

		final Set<DBDescriptor> oltpDbs = this.dBInfoCache.getDbDescriptors();
		logger.info("original size" + oltpDbs.size());
		final List<DBDescriptor> o1 = new ArrayList<>();
		
		oltpDbs.forEach(i -> o1.add(i));

		Collections.sort(o1, Comparator.comparing(DBDescriptor::getDataCenterId));
		
		for (final DBDescriptor dbDescriptor : o1) {
			final String dbName = dbDescriptor.getName();
			final Environment environment = Environment.from(dbDescriptor.getEnvironmentId());
			final DataCenter dataCenter = DataCenter.from(dbDescriptor.getDataCenterId());
			super.initializeDatacenter(dataCenter);

			logger.info("loading cobrands from db: {}; EnvironmentId: {}, EnvironmentName: {}", dbName,
					environment.getId(), environment.name());
			logger.info("===========================================");
			super.initializeCobrandCache(dbName, environment, dataCenter,
					this.dBInfoCache.getDatasourceMap().get(dbName));
			logger.info("===========================================");
			this.refreshMaps();
			logger.debug("-->" + this.cobDuplicateEnvironment);
		}
		
	}

}
