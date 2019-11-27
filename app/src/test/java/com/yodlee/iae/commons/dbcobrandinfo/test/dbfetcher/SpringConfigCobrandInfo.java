/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.test.dbfetcher;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * @author wupadhyay
 *
 */
@Configuration
@ComponentScan({"com.yodlee.iae.commons.dbcobrandinfo","com.yodlee.tools"})
@PropertySource("classpath:config.properties")
@ImportResource("classpath:TestData.xml")
public class SpringConfigCobrandInfo {

	
}