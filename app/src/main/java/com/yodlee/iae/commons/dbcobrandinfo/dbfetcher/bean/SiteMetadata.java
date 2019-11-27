/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

public class SiteMetadata {
	
	private String MCKey;
	private String siteName;
	public String getMCKey() {
		return MCKey;
	}
	public void setMCKey(String mCKey) {
		MCKey = mCKey;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}
