/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

public class CobContainers {

	private Long tagId;
	private String sumInfoId;
	private String status;
	private String className;

	public String getSumInfoId() {
		return sumInfoId;
	}

	public void setSumInfoId(String sumInfoId) {
		this.sumInfoId = sumInfoId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

}
