/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

import java.util.List;

public class Site {

	private String siteId;
	private String siteName;
	private List<String> metaContainer;
	private List<Long> cobrandId;
	private List<SiteLocale> siteLocale;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public List<String> getMetaContainer() {
		return metaContainer;
	}

	public void setMetaContainer(List<String> metaContainer) {
		this.metaContainer = metaContainer;
	}

	
	public List<SiteLocale> getSiteLocale() {
		return siteLocale;
	}

	public void setSiteLocale(List<SiteLocale> siteLocale) {
		this.siteLocale = siteLocale;
	}

	public List<Long> getCobrandId() {
		return cobrandId;
	}

	public void setCobrandId(List<Long> cobrandList) {
		this.cobrandId = cobrandList;
	}
	
	

}
