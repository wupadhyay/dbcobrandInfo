/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */

package com.yodlee.iae.commons.dbcobrandinfo.test.dbfetcher;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service.CobrandInfoDbService;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service.RedisService;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;

public class CobrandDbServiceTest extends BaseCobrandTest{

	@InjectMocks
	@Spy
	CobrandInfoDbService cobrandDbService;
	
	@Mock
	RedisService redisService;
	
	@Autowired
	CobrandUtil cobrandUtil;
	
	@Mock
	CobrandInfoCache cobInfoCache;
	
	public void buildAndSaveDataTest() {
		cobrandDbService.buildandSaveData();
	}
	
	
}
