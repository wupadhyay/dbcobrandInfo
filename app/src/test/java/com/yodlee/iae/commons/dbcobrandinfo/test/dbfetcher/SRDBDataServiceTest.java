/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.test.dbfetcher;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.testng.annotations.Test;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.cache.DBInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service.SRDBDataService;


public class SRDBDataServiceTest extends BaseCobrandTest{

	
	@InjectMocks
	@Spy
	SRDBDataService srDbService;
	
	@Mock
	private DBInfoCache dataSource;
	
	@Mock
	Connection con;
	
	@Mock
	Statement stmt;
	
	@Mock 
	ResultSet rs;
	
	@Mock
	private CobrandInfoCache cobInfoCache;
	
	@Test
	public void testgetDataFromSRDB() throws Exception {
		
		when(rs.getLong("PRODUCT_CATALOG_ID")).thenReturn((long) 1, (long) 11, (long) 8);
		when(rs.getString("tag")).thenReturn(
				"bank,stocks,credits,loans,mortgage,insurance,minutes,bills,utilities,telephone,cable_satellite,isp,investment,loan,bill",
				"bank,news,auction,orders,apt_rental", "bill_payment");
		when(stmt.executeQuery(Mockito.anyString())).thenReturn(rs);
		HashMap<Long, List<String>> cobSupportedContMap = new HashMap<Long, List<String>>();

		try {
			cobSupportedContMap = this.cobInfoCache.getCobSupportedContMap();
			assertNotNull(cobSupportedContMap);
		} catch (Exception e) {
			assertEquals(true, false);
			e.printStackTrace();
		}
	}

}
