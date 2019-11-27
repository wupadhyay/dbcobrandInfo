/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

import java.util.List;

public class CobSite {

	private String siteId;
	private List<CobContainers> cobContainers;
	private String cobSiteDisplayName;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public List<CobContainers> getCobContainers() {
		return cobContainers;
	}

	public void setCobContainers(List<CobContainers> cobSiteContainer) {
		this.cobContainers = cobSiteContainer;
	}

	public String getCobSiteDisplayName() {
		return cobSiteDisplayName;
	}

	public void setCobSiteDisplayName(String cobSiteDisplayName) {
		this.cobSiteDisplayName = cobSiteDisplayName;
	}
	
	
	
}
