/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CustomBeanPropertyRowMapper;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainerResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobrandProductResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.QueryConstant;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.TagResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.handler.CobrandContainerHandler;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;


/**
 * @author wupadhyay
 * @since June 21, 2019
 */

@Service
public class CobrandContainerService {

	private static Logger logger = LoggerFactory.getLogger(CobrandContainerService.class);
	
	@Autowired
	private CobrandContainerHandler cobContainer;
	
	public void getCobrandContainerData(JdbcTemplate jdbcTemplate) {
		logger.debug("Entering Cob Enabled Container");
		HashMap<Long, List<Long>> cobToProductIdMap = new HashMap<Long, List<Long>>();
		HashMap<Long, List<String>> cobToDisabledContainerMap = new HashMap<Long, List<String>>();
		HashMap<String, String> tagMap = new HashMap<String, String>();
		HashMap<Long, List<String>> cobToSupportedCont = new HashMap<Long, List<String>>();
		try {
			cobToProductIdMap = getCobrandProductMap(jdbcTemplate, cobToProductIdMap);
			cobToDisabledContainerMap = getDisabledCobrandContainer(jdbcTemplate, cobToDisabledContainerMap);
			tagMap = getTagMap(jdbcTemplate, tagMap);
			cobToSupportedCont = cobContainer.getCobContainerMap(cobToProductIdMap);
			cobContainer.getCobEnabledCont(cobToSupportedCont, cobToDisabledContainerMap, tagMap);
			logger.info("Exiting Cob Enabled Container");
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}

	}

	public HashMap<Long, List<String>> getDisabledCobrandContainer(JdbcTemplate jdbcTemplate,
			HashMap<Long, List<String>> cobToDisabledContainerMap) {
		logger.debug("Entering getDisabledCobrandContainer");
		try {
			Set<CobContainerResultSet> cobContList = new HashSet<CobContainerResultSet>();
			cobContList = new HashSet<>(jdbcTemplate.query(QueryConstant.COBRAND_DISABLED_CONTAINER_QUERY,
					new CustomBeanPropertyRowMapper<>(CobContainerResultSet.class)));
			cobToDisabledContainerMap = cobContainer.getDisabledContainer(cobContList, cobToDisabledContainerMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			CobrandUtil.printException(logger, e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CobrandUtil.printException(logger, e);
		}
		logger.debug("Exiting getDisabledCobrandContainer-" + cobToDisabledContainerMap.toString());
		return cobToDisabledContainerMap;
	}

	public HashMap<String, String> getTagMap(JdbcTemplate jdbcTemplate, HashMap<String, String> tagMap) {
		logger.debug("Entering getTagMap");
		try {
			Set<TagResultSet> tagList = new HashSet<>();
			tagList = new HashSet<>(
					jdbcTemplate.query(QueryConstant.TAG_QUERY, new CustomBeanPropertyRowMapper<>(TagResultSet.class)));
			tagMap = cobContainer.getTagMap(tagList, tagMap);

		} catch (SQLException e) {
			CobrandUtil.printException(logger, e);
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.debug("Exiting getTagMap");
		return tagMap;
	}

	public HashMap<Long, List<Long>> getCobrandProductMap(JdbcTemplate jdbcTemplate,
			HashMap<Long, List<Long>> cobToProductIdMap) {
		logger.debug("Inside getCobrandProductMap");
		try {
			Set<CobrandProductResultSet> cobProdSet = new HashSet<CobrandProductResultSet>();
			cobProdSet = new HashSet<>(jdbcTemplate.query(QueryConstant.COBRAND_PRODUCT_CATALOG_QUERY,
					new CustomBeanPropertyRowMapper<>(CobrandProductResultSet.class)));
			cobToProductIdMap = cobContainer.getCobProductIdMap(cobProdSet, cobToProductIdMap);
		} catch (SQLException e) {
			CobrandUtil.printException(logger, e);
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.debug("Exiting getCobrandProductMap");
		return cobToProductIdMap;
	}

}
