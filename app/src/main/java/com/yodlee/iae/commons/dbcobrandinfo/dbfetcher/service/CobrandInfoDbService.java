/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.Brand;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainer;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobSite;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainers;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.Site;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.SiteLocale;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;

@Service
public class CobrandInfoDbService {

	static Logger logger = LoggerFactory.getLogger(CobrandInfoDbService.class);
	
	@Autowired
	private CobrandInfoCache cobInfoCache;
	
	@Autowired
	private RedisService redisService;
	
	public void buildandSaveData() {
		logger.info("Here inside buildandSaveData");
		HashMap<String, List<String>> siteToCobrandListMap = this.cobInfoCache.getSiteToCobrandListMap();
		HashMap<String, List<String>> siteToLocaleMap = this.cobInfoCache.getSiteToLocaleMap();
		HashMap<Long, String> cobIdtoNameMap = this.cobInfoCache.getCobIdtoNameMap();
		HashMap<String, List<String>> siteToMetaContainerMap = this.cobInfoCache.getSiteToMetaContainerMap();
		HashMap<Long, HashMap<String, HashMap<String, CobContainers>>> cobrandDataMap = this.cobInfoCache
				.getCobrandDataMap();
		HashMap<Long, List<CobContainer>> cobEnabledCont = this.cobInfoCache.getCobEnabledCont();
		HashMap<String, String> siteIdToName = this.cobInfoCache.getSiteNameMap();
		try {
			for (Long cobrandId : cobrandDataMap.keySet()) {
				Brand brand = new Brand();
				brand.setCobrandId(cobrandId);
				brand.setName(cobIdtoNameMap.get(cobrandId));
				brand.setCobContainer(cobEnabledCont.get(cobrandId));
				HashMap<String, HashMap<String, CobContainers>> siteToSumInfoMap = cobrandDataMap.get(cobrandId);
				List<CobSite> siteList = new ArrayList<CobSite>();
				for (String siteId : siteToSumInfoMap.keySet()) {
					CobSite site = new CobSite();
					site.setSiteId(siteId);
					site.setCobSiteDisplayName(siteIdToName.get(siteId));
					HashMap<String, CobContainers> sumInfoMap = siteToSumInfoMap.get(siteId);
					List<CobContainers> siteContainerList = new ArrayList<CobContainers>();
					for (String sumInfoId : sumInfoMap.keySet()) {
						siteContainerList.add(sumInfoMap.get(sumInfoId));
					}
					site.setCobContainers(siteContainerList);
					siteList.add(site);
				}
				brand.setCobSite(siteList);
				String jsonObject = parseObjectToJson(brand);

				redisService.insertCobrandDataToCache(cobrandId, jsonObject);
			}
		} catch (Exception e) {

			CobrandUtil.printException(logger, e);
		}

		try {
			for (String siteId : siteToCobrandListMap.keySet()) {
				Site site = new Site();
				site.setSiteId(siteId);
				site.setSiteName(siteIdToName.get(siteId));
				List<String> cobrands = siteToCobrandListMap.get(siteId);
				List<Long> cobList = new ArrayList<>();
				for (String cobId : cobrands) {
					Long cobrandId = Long.parseLong(cobId);
					cobList.add(cobrandId);
				}
				List<String> localeIdList = siteToLocaleMap.get(siteId);
				site.setCobrandId(cobList);
				site.setMetaContainer(siteToMetaContainerMap.get(siteId));
				List<SiteLocale> localeList = new ArrayList<>();
				if(localeIdList!=null)
				for (String localeId : localeIdList) {
					String isPrimary = "false";
					SiteLocale locale = new SiteLocale();
					locale.setValue(this.cobInfoCache.getLocaleIdToCountry().get(localeId));
					logger.info("Primary Locale --"+this.cobInfoCache.getSiteToPrimaryLocale().get(siteId));
					if (this.cobInfoCache.getSiteToPrimaryLocale().containsKey(siteId)) {
						String primaryLocaleId = this.cobInfoCache.getSiteToPrimaryLocale().get(siteId);
						if (primaryLocaleId != null && primaryLocaleId.equals(localeId.trim()))
							isPrimary = "true";
					}
					locale.setIsPrimary(isPrimary);
					localeList.add(locale);
				}
				site.setSiteLocale(localeList);
				String jsonObject = parseObjectToJson(site);
				redisService.insertSiteDataToCache(siteId, jsonObject);
			}
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.info("Exiting buildandSaveData");
	}
	
	public String parseObjectToJson(Object obj) {
		String finalJsonString = null;
		try {
			finalJsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			CobrandUtil.printException(logger, e);
		}
		return finalJsonString;
	}

	
	
}
