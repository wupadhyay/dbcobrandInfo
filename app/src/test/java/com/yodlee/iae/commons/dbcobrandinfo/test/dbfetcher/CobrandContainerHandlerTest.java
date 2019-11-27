/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.test.dbfetcher;

import static org.testng.Assert.assertNotNull;

import java.sql.SQLException;
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
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobContainerResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.CobrandProductResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean.TagResultSet;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.handler.CobrandContainerHandler;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.service.SRDBDataService;


public class CobrandContainerHandlerTest extends BaseCobrandTest {

	@Autowired
	SRDBDataService service;
	
	@Autowired
	CobrandInfoCache cobInfoCache;

	@Test
	public void testCobProductIdMap() throws Exception {
		HashMap<Long, List<Long>> cobProductIdMap = new HashMap<>();
		Set<CobrandProductResultSet> resultSet = new HashSet<CobrandProductResultSet>();
		CobrandContainerHandler cobrandContainerHandler = new CobrandContainerHandler();

		Object object = testData.get("testCobrandProdData");
		String data = object.toString().trim();
		JsonElement jsonElement = new JsonParser().parse(data);
		JsonElement notificationElement = jsonElement.getAsJsonObject().get("test");
		JsonArray json = notificationElement.getAsJsonArray();
		Iterator<JsonElement> it = json.iterator();

		while (it.hasNext()) {
			JsonElement jsonData = it.next().getAsJsonObject();
			String jsonDt = jsonData.toString();
			CobrandProductResultSet result = new ObjectMapper()
					.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
					.readValue(jsonDt, CobrandProductResultSet.class);
			resultSet.add(result);
		}

		cobProductIdMap = cobrandContainerHandler.getCobProductIdMap(resultSet, cobProductIdMap);
		//assertNotNull(cobProductIdMap);
	}

	@Test
	public void testDisabledContainer() throws Exception {
		HashMap<Long, List<String>> cobDisabledContMap = new HashMap<>();
		Set<CobContainerResultSet> resultSet = new HashSet<CobContainerResultSet>();
		CobrandContainerHandler cobrandContainerHandler = new CobrandContainerHandler();
		Object object = testData.get("testDisabledContainerData");
		String data = object.toString().trim();
		JsonElement jsonElement = new JsonParser().parse(data);
		JsonElement notificationElement = jsonElement.getAsJsonObject().get("test");
		JsonArray json = notificationElement.getAsJsonArray();
		Iterator<JsonElement> it = json.iterator();

		while (it.hasNext()) {
			JsonElement jsonData = it.next().getAsJsonObject();
			String jsonDt = jsonData.toString();
			CobContainerResultSet result = new ObjectMapper()
					.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
					.readValue(jsonDt, CobContainerResultSet.class);
			resultSet.add(result);
		}

		cobDisabledContMap = cobrandContainerHandler.getDisabledContainer(resultSet, cobDisabledContMap);
		assertNotNull(cobDisabledContMap);
	}

	@Test
	public void testTagMapper() throws Exception {
		Set<TagResultSet> resultSet = new HashSet<TagResultSet>();
		HashMap<String, String> tagMap = new HashMap<>();
		CobrandContainerHandler cobrandContainerHandler = new CobrandContainerHandler();

		Object object = testData.get("testTagData");
		String data = object.toString().trim();
		JsonElement jsonElement = new JsonParser().parse(data);
		JsonElement notificationElement = jsonElement.getAsJsonObject().get("test");
		JsonArray json = notificationElement.getAsJsonArray();
		Iterator<JsonElement> it = json.iterator();

		while (it.hasNext()) {
			JsonElement jsonData = it.next().getAsJsonObject();
			String jsonDt = jsonData.toString();
			TagResultSet result = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
					.readValue(jsonDt, TagResultSet.class);
			resultSet.add(result);
		}

		tagMap = cobrandContainerHandler.getTagMap(resultSet, tagMap);
		assertNotNull(tagMap);

	}

	@Test
	public void testCobrandToAllContainerMapper() throws SQLException, Exception {
		HashMap<Long, List<String>> cobToSupportContMap = new HashMap<>();
		HashMap<Long, List<String>> srdbDatap = new HashMap<>();
		HashMap<Long, List<Long>> cobProductIdMap = new HashMap<>();
		Set<CobrandProductResultSet> resultSet = new HashSet<CobrandProductResultSet>();
		CobrandContainerHandler cobrandContainerHandler = new CobrandContainerHandler();

		Object object = testData.get("testCobrandProdData");
		String data = object.toString().trim();
		JsonElement jsonElement = new JsonParser().parse(data);
		JsonElement notificationElement = jsonElement.getAsJsonObject().get("test");
		JsonArray json = notificationElement.getAsJsonArray();
		Iterator<JsonElement> it = json.iterator();

		while (it.hasNext()) {
			JsonElement jsonData = it.next().getAsJsonObject();
			String jsonDt = jsonData.toString();
			CobrandProductResultSet result = new ObjectMapper()
					.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
					.readValue(jsonDt, CobrandProductResultSet.class);
			resultSet.add(result);
		}

		cobProductIdMap = cobrandContainerHandler.getCobProductIdMap(resultSet, cobProductIdMap);

		
		cobToSupportContMap = cobrandContainerHandler.getCobContainerMap(cobProductIdMap);
		assertNotNull(cobToSupportContMap);
	}

	@Test
	public void testCobrandEnabledContainerMapper() throws Exception {
		Set<TagResultSet> resultSet = new HashSet<TagResultSet>();
		HashMap<String, String> tagMap = new HashMap<>();
		CobrandContainerHandler cobrandContainerHandler = new CobrandContainerHandler();

		Object object = testData.get("testTagData");
		String data = object.toString().trim();
		JsonElement jsonElement = new JsonParser().parse(data);
		JsonElement notificationElement = jsonElement.getAsJsonObject().get("test");
		JsonArray json = notificationElement.getAsJsonArray();
		Iterator<JsonElement> it = json.iterator();

		while (it.hasNext()) {
			JsonElement jsonData = it.next().getAsJsonObject();
			String jsonDt = jsonData.toString();
			TagResultSet result = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
					.readValue(jsonDt, TagResultSet.class);
			resultSet.add(result);
		}
		tagMap = cobrandContainerHandler.getTagMap(resultSet, tagMap);

		HashMap<Long, List<String>> cobDisabledContMap = new HashMap<>();
		Set<CobContainerResultSet> resultSet1 = new HashSet<CobContainerResultSet>();

		Object object1 = testData.get("testDisabledContainerData");
		String data1 = object1.toString().trim();
		JsonElement jsonElement1 = new JsonParser().parse(data1);
		JsonElement notificationElement1 = jsonElement1.getAsJsonObject().get("test");
		JsonArray json1 = notificationElement1.getAsJsonArray();
		Iterator<JsonElement> it1 = json1.iterator();

		while (it1.hasNext()) {
			JsonElement jsonData = it1.next().getAsJsonObject();
			String jsonDt = jsonData.toString();
			CobContainerResultSet result = new ObjectMapper()
					.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
					.readValue(jsonDt, CobContainerResultSet.class);
			resultSet1.add(result);
		}
		cobDisabledContMap = cobrandContainerHandler.getDisabledContainer(resultSet1, cobDisabledContMap);

		HashMap<Long, List<String>> cobToSupportContMap = new HashMap<>();
		HashMap<Long, List<String>> srdbDatap = new HashMap<>();
		HashMap<Long, List<Long>> cobProductIdMap = new HashMap<>();
		Set<CobrandProductResultSet> resultSet11 = new HashSet<CobrandProductResultSet>();

		Object object11 = testData.get("testCobrandProdData");
		String data11 = object11.toString().trim();
		JsonElement jsonElement11 = new JsonParser().parse(data11);
		JsonElement notificationElement11 = jsonElement11.getAsJsonObject().get("test");
		JsonArray json11 = notificationElement11.getAsJsonArray();
		Iterator<JsonElement> it11 = json11.iterator();

		while (it11.hasNext()) {
			JsonElement jsonData = it11.next().getAsJsonObject();
			String jsonDt = jsonData.toString();
			CobrandProductResultSet result = new ObjectMapper()
					.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
					.readValue(jsonDt, CobrandProductResultSet.class);
			resultSet11.add(result);
		}

		cobProductIdMap = cobrandContainerHandler.getCobProductIdMap(resultSet11, cobProductIdMap);

		cobToSupportContMap = cobrandContainerHandler.getCobContainerMap(cobProductIdMap);

		cobrandContainerHandler.getCobEnabledCont(cobToSupportContMap, cobDisabledContMap,
				tagMap);
		
	}

}
