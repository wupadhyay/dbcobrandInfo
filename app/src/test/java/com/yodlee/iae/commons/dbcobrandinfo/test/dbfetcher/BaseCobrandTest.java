/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.test.dbfetcher;

import java.util.Map;

import javax.annotation.Resource;

import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

import com.yodlee.iae.commons.dbcobrandinfo.test.BaseDBCobrandInfoTest;


@ContextConfiguration(classes = { SpringConfigCobrandInfo.class })
public class BaseCobrandTest extends BaseDBCobrandInfoTest{

	@Resource(name = "testData")
	protected Map<String, Object> testData;
	
	@BeforeClass
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}
}
