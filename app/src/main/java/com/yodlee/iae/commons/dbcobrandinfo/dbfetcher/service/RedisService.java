/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;
import com.yodlee.iae.kcache.core.CacheBuilder;
import com.yodlee.iae.kcache.exception.InitializationException;
import com.yodlee.iae.kcache.exception.KCacheStorageException;
import com.yodlee.iae.kcache.handler.KCache;

import jodd.util.Base64;

@Service
public class RedisService {

	@Value("${tools.dbcobrandinfo.database.redis.cobrand.namespace}")
	private String cobrand_namespace;

	@Value("${tools.dbcobrandinfo.database.redis.site.namespace}")
	private String site_namespace;
	
	private KCache cache = null;
	
	private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
	
	public void insertCobrandDataToCache(Long cobrandId, String cobrandData) throws Exception {
		logger.info("Inside insertCobrandDataToCache");
		String cobrand_Id = Long.toString(cobrandId).trim();
		String compressedData = null;
		String key = cobrand_Id;
		logger.info("Key is" + key);
		try {
			compressedData = compress(cobrandData);
			
			if (this.cache.isExists(cobrand_namespace, cobrand_Id, null)) {
				this.cache.update(cobrand_namespace, cobrand_Id, compressedData, null);
			} else {
				this.cache.insert(cobrand_namespace, cobrand_Id, compressedData, null);
			}
			
		} catch (KCacheStorageException e) {
			CobrandUtil.printException(logger, e);
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.info("Exiting insertCobrandDataToCache");
	}

	public void insertSiteDataToCache(String siteId, String siteData) throws Exception {
		logger.info("Inside insertSiteDataToCache");
		String compressedData = null;
		logger.info("Key is" + siteId);
		try {
			compressedData = compress(siteData);
			if (this.cache.isExists(site_namespace, siteId, null)) {
				this.cache.update(site_namespace, siteId, compressedData, null);
			} else {
				this.cache.insert(site_namespace, siteId, compressedData, null);
			}
		} catch (KCacheStorageException e) {
			CobrandUtil.printException(logger, e);
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.info("Exiting insertSiteDataToCache");
	}

	@PostConstruct
	public void initializeRedisClient() {
		try {
			if (cache == null)
				cache = new CacheBuilder().havingRedisCache().useRMapType().build();
		} catch (InitializationException e) {
			CobrandUtil.printException(logger, e);
		}
	}
	
	public String compress(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		String outStr = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			try (ZipOutputStream zos = new ZipOutputStream(out)) {
				ZipEntry zipEntry = new ZipEntry("cobsite.json");
				zos.putNextEntry(zipEntry);
				zos.write(str.getBytes());
				zos.closeEntry();
			}
			outStr = Base64.encodeToString(out.toByteArray());
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		return outStr;
	}
	

	public String decompress(String json) {
		String decompressedData = "";
		try {
			byte[] bytestream = Base64.decode(json);

			ZipInputStream gis = new ZipInputStream(new ByteArrayInputStream(bytestream));
			BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));

			String line;
			while ((line = bf.readLine()) != null) {
				decompressedData += line;
			}
		} catch (Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.info("Decompressed String--" + decompressedData.trim());
		return decompressedData.trim();
	}

}
