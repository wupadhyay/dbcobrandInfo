/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

public class CobrandResult {
	private Long cobrandId;
	private String cobrandName;
	private String sumInfoId;
	private String status;
	private String siteId;
	private String tag;
	private Long tagId;
	private String siteName;
	private String sitePrimaryLocale;
	private String className;
	
	public Long getCobrandId() {
		return cobrandId;
	}

	public void setCobrandId(Long cobrandId) {
		this.cobrandId = cobrandId;
	}

	public String getCobrandName() {
		return cobrandName;
	}

	public void setCobrandName(String cobrandName) {
		this.cobrandName = cobrandName;
	}

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

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSitePrimaryLocale() {
		return sitePrimaryLocale;
	}

	public void setSitePrimaryLocale(String sitePrimaryLocale) {
		this.sitePrimaryLocale = sitePrimaryLocale;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
