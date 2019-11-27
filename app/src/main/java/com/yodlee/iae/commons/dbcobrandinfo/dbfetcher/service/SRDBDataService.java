/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.cache.CustomBeanPropertyRowMapper;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.QueryConstant;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.SRDBData;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;
import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.DBDescriptor;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;


/**
 * @author Waivaw Upadhyay (wupadhyay@yodlee.com)
 * @since June 21, 2019
 */

@Service
public class SRDBDataService {
	private static Logger logger = LoggerFactory.getLogger(SRDBDataService.class);

	private HashMap<Long, List<String>> cobSupportedContMap = new HashMap<>();
	
	@Autowired
	private CobrandInfoCache cobInfoCache;

	
	@Value("${tools.dbcobrandinfo.srdb.default.datacenter.id}")
	private int defaultDataCenterId;

	public void fetchSRData(String dbName, Environment env, DataSource dataSource, DataCenter dataCenter)
			throws SQLException {
		logger.info("Inside fetchSRData" + this.cobInfoCache.getSrdbDatasourceMap().toString());
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		if (dataCenter.getId() == defaultDataCenterId) {
			try {
				Set<SRDBData> dataList = new HashSet<SRDBData>();
				dataList = new HashSet<>(jdbcTemplate.query(QueryConstant.COBRAND_SUPPORTED_CONTAINER_QUERY,
						new CustomBeanPropertyRowMapper<>(SRDBData.class)));
				for (SRDBData rs : dataList) {
					Long product_catalog_id = rs.getProductCatalogId();
					String containers = rs.getTag();
					List<String> containerList = new ArrayList<String>(Arrays.asList(containers.split("\\s*,\\s*")));
					this.cobInfoCache.getCobSupportedContMap().put(product_catalog_id, containerList);
				}
			} catch (Exception e) {
				CobrandUtil.printException(logger, e);
			}
		}
		logger.debug("Exiting fetchSRData:" + cobSupportedContMap.toString());
	}

	
	@PostConstruct
	private void init() {
		logger.info("Inside Init method");
		try {
			final Set<DBDescriptor> srDbs = this.cobInfoCache.getSrdbDescriptors();
			final List<DBDescriptor> o1 = new ArrayList<>();
			srDbs.forEach(i -> o1.add(i));
			Collections.sort(o1, Comparator.comparing(DBDescriptor::getDataCenterId));
			for (final DBDescriptor dbDescriptor : o1) {
				final String dbName = dbDescriptor.getName();
				final Environment environment = Environment.from(dbDescriptor.getEnvironmentId());
				final DataCenter dataCenter = DataCenter.from(dbDescriptor.getDataCenterId());

				logger.info("loading cobrands for srdb: {}; EnvironmentId: {}, EnvironmentName: {}, Datacenter: {}",
						dbName, environment.getId(), environment.name(), dataCenter.getName());
				logger.info("===========================================");
				try {
					fetchSRData(dbName, environment, this.cobInfoCache.getSrdbDatasourceMap().get(dbName), dataCenter);
				} catch (SQLException e) {
					CobrandUtil.printException(logger, e);
				}
				logger.info("===========================================");
			}
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.info("Exiting Init method");

	}

}