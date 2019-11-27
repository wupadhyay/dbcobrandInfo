/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainers;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobrandResult;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.SiteResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;

@Component
public class CobrandSiteHandler {
	
	private static Logger logger = LoggerFactory.getLogger(CobrandSiteHandler.class);
	
	@Autowired
	private CobrandInfoCache cobrandInfoCache;

	public void cobrandMapCreator(Set<CobrandResult> cobList) {
		logger.info("Inside cobrandMapCreator");
		try {
			for (CobrandResult rs : cobList) {
				List<String> metaContainers = new ArrayList<String>();
				Long cobrand_id = rs.getCobrandId();
				String cobrand_name = rs.getCobrandName();
				String sum_info_id = rs.getSumInfoId();
				String status = rs.getStatus();
				String site_id = rs.getSiteId();
				String tag = rs.getTag();
				String site_prim_locale = rs.getSitePrimaryLocale();
				String className = rs.getClassName();
				Long tagId = rs.getTagId();

				if (cobrandInfoCache.getSiteToMetaContainerMap().containsKey(site_id)) {
					metaContainers = cobrandInfoCache.getSiteToMetaContainerMap().get(site_id);
					if (!metaContainers.contains(tag)) {
						metaContainers.add(tag);
						cobrandInfoCache.getSiteToMetaContainerMap().put(site_id, metaContainers);
					}
				} else {
					metaContainers.add(tag);
					cobrandInfoCache.getSiteToMetaContainerMap().put(site_id, metaContainers);
				}
				if (!cobrandInfoCache.getSiteToPrimaryLocale().containsKey(site_id)) {
					cobrandInfoCache.getSiteToPrimaryLocale().put(site_id, site_prim_locale);
				}
				if (!cobrandInfoCache.getCobIdtoNameMap().containsKey(cobrand_id)) {
					cobrandInfoCache.getCobIdtoNameMap().put(cobrand_id, cobrand_name);
				}
				if (!cobrandInfoCache.getCobrandDataMap().containsKey(cobrand_id)) {
					HashMap<String, HashMap<String, CobContainers>> siteToSumInfoMap = new HashMap<>();

					HashMap<String, CobContainers> sumInfoMap = new HashMap<>();
					CobContainers cobSiteContainer = new CobContainers();
					cobSiteContainer.setClassName(className);
					cobSiteContainer.setStatus(status);
					cobSiteContainer.setSumInfoId(sum_info_id);
					cobSiteContainer.setTagId(tagId);
					sumInfoMap.put(sum_info_id, cobSiteContainer);
					siteToSumInfoMap.put(site_id, sumInfoMap);
					cobrandInfoCache.getCobrandDataMap().put(cobrand_id, siteToSumInfoMap);
				} else {
					HashMap<String, HashMap<String, CobContainers>> siteToSumInfoMap = cobrandInfoCache
							.getCobrandDataMap().get(cobrand_id);
					if (siteToSumInfoMap.containsKey(site_id)) {
						CobContainers cobSiteContainer = new CobContainers();
						cobSiteContainer.setClassName(className);
						cobSiteContainer.setStatus(status);
						cobSiteContainer.setSumInfoId(sum_info_id);
						cobSiteContainer.setTagId(tagId);
						HashMap<String, CobContainers> sumInfoMap = siteToSumInfoMap.get(site_id);
						if (!sumInfoMap.containsKey(sum_info_id)) {
							sumInfoMap.put(sum_info_id, cobSiteContainer);
						}
						siteToSumInfoMap.put(site_id, sumInfoMap);
					} else {
						HashMap<String, CobContainers> sumInfoMap = new HashMap<>();
						CobContainers cobSiteContainer = new CobContainers();
						cobSiteContainer.setClassName(className);
						cobSiteContainer.setStatus(status);
						cobSiteContainer.setSumInfoId(sum_info_id);
						cobSiteContainer.setTagId(tagId);
						sumInfoMap.put(sum_info_id, cobSiteContainer);
						siteToSumInfoMap.put(site_id, sumInfoMap);
					}
					cobrandInfoCache.getCobrandDataMap().put(cobrand_id, siteToSumInfoMap);
				}
			
			}
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.debug("Exiting cobrandMapCreator");
	}

	public void siteMapCreator(Set<SiteResultSet> siteList) {
		logger.info("Inside siteMapCreator");
		try {
			for (SiteResultSet rs : siteList) {
				String site_id = rs.getSiteId();
				String cobrands = rs.getCobrands();
				
				List<String> cobrandIdList = new ArrayList<String>(Arrays.asList(cobrands.split("\\s*,\\s*")));
				if (cobrandInfoCache.getSiteToCobrandListMap().containsKey(site_id)) {
					List<String> cobList = cobrandInfoCache.getSiteToCobrandListMap().get(site_id);
					HashSet<String> cobrandSet = new HashSet<String>(cobList);
					cobrandSet.addAll(cobrandIdList);
					cobList.clear();
					cobList.addAll(cobrandSet);
					cobrandInfoCache.getSiteToCobrandListMap().put(site_id, cobList);
				} else {
					cobrandInfoCache.getSiteToCobrandListMap().put(site_id, cobrandIdList);
				}
				
			}
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.debug("Exiting siteMapCreator");
	}

}
