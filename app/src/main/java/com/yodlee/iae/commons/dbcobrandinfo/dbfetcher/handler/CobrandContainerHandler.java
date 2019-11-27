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
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainer;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainerResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobrandProductResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.TagResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;


/**
 * @author Waivaw Upadhyay wupadhyay@yodlee.com
 * @since July 1, 2019
 */

@Component
public class CobrandContainerHandler {
	private static Logger logger = LoggerFactory.getLogger(CobrandContainerHandler.class);
	
	@Autowired
	private CobrandInfoCache cobInfoCache;

	public HashMap<Long, List<Long>> getCobProductIdMap(Set<CobrandProductResultSet> cobProdSet,
			HashMap<Long, List<Long>> cobProductIdMap) throws Exception {
		logger.info("Entering getCobProductIdMap");
		try {
			for (CobrandProductResultSet rs : cobProdSet) {
				Long cobrand_id = rs.getCobrandId();
				Long product_catalog_id = rs.getProductCatalogId();

				List<Long> productCatList = new ArrayList<Long>();
				if (cobProductIdMap.containsKey(cobrand_id)) {
					productCatList = cobProductIdMap.get(cobrand_id);
					if (!productCatList.contains(product_catalog_id)) {
						productCatList.add(product_catalog_id);
					}
				} else {
					productCatList.add(product_catalog_id);
				}
				cobProductIdMap.put(cobrand_id, productCatList);
			}

		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.info("Exiting getCobProductIdMap");
		return cobProductIdMap;
	}

	

	public HashMap<Long, List<String>> getDisabledContainer(Set<CobContainerResultSet> cobContList,
			HashMap<Long, List<String>> cobDisabledContMap) throws Exception {
		logger.info("Entering getDisabledContainer");
		try {

			for (CobContainerResultSet rs : cobContList) {
				Long cobrand_id = rs.getCobrandId();
				String containers = rs.getContainers();
				List<String> containerList = null;
				// disabledCont.setCobrandId(cobrand_id);
				if (containers != null) {
					containerList = new ArrayList<String>(Arrays.asList(containers.split("\\s*,\\s*")));

				}
				cobDisabledContMap.put(cobrand_id, containerList);

			}

			 logger.debug("Exiting getDisabledContainer");
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		return cobDisabledContMap;

	}

	public HashMap<String, String> getTagMap(Set<TagResultSet> tagList, HashMap<String, String> tagMap)
			throws Exception {
		logger.info("Entering getTagMap");
		try {

			for (TagResultSet rs : tagList) {
				String tag_id = rs.getTagId();
				String tag_name = rs.getTag();
				tagMap.put(tag_name, tag_id);

			}
			logger.debug("Exiting getTagMap" + tagMap.toString());
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		return tagMap;
	}

	public HashMap<Long, List<String>> getCobContainerMap(HashMap<Long, List<Long>> cobToProductIdMap) {
		HashMap<Long, List<String>> cobToSupportContMap = new HashMap<Long, List<String>>();
		logger.info("Entering getCobContainerMap:");
		try {
			for (Long cobrand_id : cobToProductIdMap.keySet()) {
				List<Long> productCatalogIdList = cobToProductIdMap.get(cobrand_id);

				HashSet<String> containerList = new HashSet<String>();

				for (Long productCatalogId : productCatalogIdList) {

					List<String> tagList = this.cobInfoCache.getCobSupportedContMap().get(productCatalogId);
					if (tagList != null && !tagList.isEmpty()) {

						for (String tag : tagList) {
							containerList.add(tag);
						}
					}
				}
				List<String> mainList = new ArrayList<String>();
				mainList.addAll(containerList);

				cobToSupportContMap.put(cobrand_id, mainList);

			}

			logger.debug("Exiting getCobContainerMap"+cobToSupportContMap.toString());
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		return cobToSupportContMap;
	}

	
	public void getCobEnabledCont(HashMap<Long, List<String>> cobSupportedCont,
			HashMap<Long, List<String>> cobDisabledContMap, HashMap<String, String> tagMap) {
		logger.info("Entering getCobEnabledCont:");

		try {
			for (Long cobrandId : cobSupportedCont.keySet()) {

				List<String> disabledContList = cobDisabledContMap.get(cobrandId);

				List<String> supportedContList = cobSupportedCont.get(cobrandId);

				if (disabledContList != null) {
					supportedContList.removeAll(disabledContList);
				}

				List<CobContainer> cobContainerList = new ArrayList<CobContainer>();
				if (supportedContList != null && !supportedContList.isEmpty())
					for (String tag_name : supportedContList) {
						String tag_id = tagMap.get(tag_name);
						if(tag_id!=null) {
						CobContainer cobContainer = new CobContainer();
						cobContainer.setTagId(tag_id);
						cobContainer.setTagName(tag_name);
						cobContainer.setStatus("Enabled");
						cobContainerList.add(cobContainer);
						}
					}
				this.cobInfoCache.getCobEnabledCont().put(cobrandId, cobContainerList);
			}
			logger.debug("Exiting getCobEnabledCont:" + cobInfoCache.getCobEnabledCont().toString());
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}

	}

	
	

}
