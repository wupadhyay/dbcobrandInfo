/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service.CobrandInfoDbService;
import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.DBDescriptor;

@Repository
@DependsOn({"srdbDatasources"})
public class CobrandSiteCacheManager {
	
	private static Logger logger = LoggerFactory.getLogger(CobrandSiteCacheManager.class);

	@Autowired
	private DBInfoCache dBInfoCache;
	
	@Autowired
	private CobrandInfoDbService dbService;
	
	@Autowired
	private CobrandSiteDetailsFetcher cobSiteDetailFetcher;
	
	@Autowired
	private CobrandInfoCache cobrandInfoCache;
	
	public void initialize() {
		final Set<DBDescriptor> oltpDbs = this.dBInfoCache.getDbDescriptors();
		logger.info("original size" + oltpDbs.size());
		final List<DBDescriptor> o1 = new ArrayList<>();

		
		oltpDbs.forEach(i -> o1.add(i));

		Collections.sort(o1, Comparator.comparing(DBDescriptor::getDataCenterId));

		for (final DBDescriptor dbDescriptor : o1) {
			final String dbName = dbDescriptor.getName();
			logger.info("dbName-" + dbName);
			cobSiteDetailFetcher.initialize(this.dBInfoCache.getDatasourceMap().get(dbName));
		}

		this.dbService.buildandSaveData();
		this.cobrandInfoCache.clearCache();
	}
}
