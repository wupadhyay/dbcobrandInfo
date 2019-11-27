/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

public class SRDBData {
	
	private long productCatalogId;
	private String tag;
	public long getProductCatalogId() {
		return productCatalogId;
	}
	public void setProductCatalogId(long productCatalogId) {
		this.productCatalogId = productCatalogId;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

}
