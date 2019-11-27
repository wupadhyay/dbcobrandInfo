/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

public class CobContainerResultSet {

	private Long cobrandId;
	private String containers;

	public String getContainers() {
		return containers;
	}

	public void setContainers(String containers) {
		this.containers = containers;
	}

	public Long getCobrandId() {
		return cobrandId;
	}

	public void setCobrandId(Long cobrandId) {
		this.cobrandId = cobrandId;
	}

	

}
