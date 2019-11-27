/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

import java.util.List;

public class Brand {

	private long cobrandId;
	private String name;
	private List<CobContainer> cobContainer;
	private List<CobSite> cobSite;

	public long getCobrandId() {
		return cobrandId;
	}

	public void setCobrandId(long cobrandId) {
		this.cobrandId = cobrandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CobContainer> getCobContainer() {
		return cobContainer;
	}

	public void setCobContainer(List<CobContainer> containers) {
		this.cobContainer = containers;
	}

	public List<CobSite> getCobSite() {
		return cobSite;
	}

	public void setCobSite(List<CobSite> cobSite) {
		this.cobSite = cobSite;
	}
	
	

}
