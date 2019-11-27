/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.test.dbfetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainer;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobrandResult;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.SiteResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.handler.CobrandSiteHandler;


public class CobrandSiteHandlerTest extends BaseCobrandTest{
	
	@Autowired
	CobrandInfoCache cobInfoCache;
	
	@Test
	public void TestCobMap() throws Exception {
		
		Object object=testData.get("testCobrandSiteHandler");
		String data=object.toString().trim();
		JsonElement jsonElement = new JsonParser().parse(data);
		JsonElement notificationElement = jsonElement.getAsJsonObject().get("test");
		JsonArray json=notificationElement.getAsJsonArray();
		Iterator<JsonElement> it=json.iterator();
		Set<CobrandResult> resultSet=new HashSet<>();
		while(it.hasNext()) {
			JsonElement jsonData=it.next().getAsJsonObject();
			String jsonDt=jsonData.toString();
			CobrandResult result= new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).readValue(jsonDt,
					CobrandResult.class);
			resultSet.add(result);
		}
		
		CobrandSiteHandler cobHandler =new CobrandSiteHandler();
		HashMap<Long, List<CobContainer>> cobContainerMap =new HashMap<>();
		CobContainer cobContainer=new CobContainer();
		cobContainer.setTagId("5");
		cobContainer.setTagName("Bank");
		cobContainer.setStatus("Enable");
		List<CobContainer> cobContList=new ArrayList<>();
		cobContList.add(cobContainer);
		cobContainerMap.put(10000004l, cobContList);
		
		cobHandler.cobrandMapCreator(resultSet);;
		
		
	}
	
	@Test
	public void testResultMapReverser() throws Exception {
		CobrandSiteHandler cobHandler = new CobrandSiteHandler();
		Object objectSite = testData.get("testSitetoCobrandHandler");
		String dataSite = objectSite.toString().trim();
		JsonElement jsonElementSite = new JsonParser().parse(dataSite);
		JsonElement ElementSite = jsonElementSite.getAsJsonObject().get("test");
		JsonArray jsonSite = ElementSite.getAsJsonArray();
		Iterator<JsonElement> itSite = jsonSite.iterator();
		Set<SiteResultSet> resultSetSite = new HashSet<>();

		while (itSite.hasNext()) {
			JsonElement jsonData = itSite.next().getAsJsonObject();
			String jsonDt = jsonData.toString();
			SiteResultSet resultSite = new ObjectMapper()
					.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
					.readValue(jsonDt, SiteResultSet.class);
			resultSetSite.add(resultSite);
		}

		cobHandler.siteMapCreator(resultSetSite);
	}
	
	

}
