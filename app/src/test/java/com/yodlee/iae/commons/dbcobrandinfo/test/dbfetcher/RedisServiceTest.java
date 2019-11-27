/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.test.dbfetcher;

import java.util.Map;

import javax.annotation.Resource;

import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.testng.annotations.Test;

import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service.RedisService;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;


public class RedisServiceTest extends BaseCobrandTest {

	@InjectMocks
	@Spy
	RedisService redisService;

	@Resource(name = "testData")
	protected Map<String, Object> testNotificationData;

	@Test
	public void testCobrandDataCaching() throws Exception {

		String compressedData = redisService.compress(testNotificationData.get("testCobrandData").toString());
		redisService.insertCobrandDataToCache(1l, compressedData);
	}

	@Test
	public void testSiteDataCaching() throws Exception {

		String compressedData = redisService.compress(testNotificationData.get("testSiteData").toString());
		redisService.insertSiteDataToCache("21782", compressedData);
		;
	}

}
