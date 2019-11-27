/**
 * Copyright (c) 2016 Yodlee Inc. All Rights
 * Reserved. This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 */

package com.yodlee.iae.commons.dbcobrandinfo.model.response;

import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.CobrandStatus;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.framework.model.model.BaseModel;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 9, 2017
 */
/**
 * @author skadam
 *
 */
public final class CobrandInfo extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 7253131966654708287L;

	private Long cobrandId;

	private Long channelId;

	private Integer numberOfDaysDocumentsDownload;

	private boolean channel;

	private boolean cacheRunDisabled;

	private boolean siteRefreshEnabled;

	private boolean mfaEnabled;

	private boolean documentDownloadEnabled;

	private String dbName;

	private String cobrandName;

	private String cobrandDisplayName;

	private String enviornmentMode;

	private String deploymentMode;

	private String cobrandPresentStatus;

	private String documentTypesAllowrd;

	private CobrandStatus cobrandStatus;

	private Environment environment;

	private DataCenter dataCenter;

	private boolean oauthEnabled;

	/**
	 *
	 */
	public CobrandInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the cobrandId
	 */
	public Long getCobrandId() {
		return this.cobrandId;
	}

	/**
	 * @param cobrandId the cobrandId to set
	 */
	public void setCobrandId(final Long cobrandId) {
		this.cobrandId = cobrandId;
	}

	/**
	 * @return the channelId
	 */
	public Long getChannelId() {
		return this.channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(final Long channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the numberOfDaysDocumentsDownload
	 */
	public Integer getNumberOfDaysDocumentsDownload() {
		return this.numberOfDaysDocumentsDownload;
	}

	/**
	 * @param numberOfDaysDocumentsDownload the numberOfDaysDocumentsDownload to set
	 */
	public void setNumberOfDaysDocumentsDownload(final Integer numberOfDaysDocumentsDownload) {
		this.numberOfDaysDocumentsDownload = numberOfDaysDocumentsDownload;
	}

	/**
	 * @return the channel
	 */
	public boolean isChannel() {
		return this.channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(final boolean channel) {
		this.channel = channel;
	}

	/**
	 * @return the cacheRunDisabled
	 */
	public boolean isCacheRunDisabled() {
		return this.cacheRunDisabled;
	}

	/**
	 * @param cacheRunDisabled the cacheRunDisabled to set
	 */
	public void setCacheRunDisabled(final boolean cacheRunDisabled) {
		this.cacheRunDisabled = cacheRunDisabled;
	}

	/**
	 * @return the siteRefreshEnabled
	 */
	public boolean isSiteRefreshEnabled() {
		return this.siteRefreshEnabled;
	}

	/**
	 * @param siteRefreshEnabled the siteRefreshEnabled to set
	 */
	public void setSiteRefreshEnabled(final boolean siteRefreshEnabled) {
		this.siteRefreshEnabled = siteRefreshEnabled;
	}

	/**
	 * @return the mfaEnabled
	 */
	public boolean isMfaEnabled() {
		return this.mfaEnabled;
	}

	/**
	 * @param mfaEnabled the mfaEnabled to set
	 */
	public void setMfaEnabled(final boolean mfaEnabled) {
		this.mfaEnabled = mfaEnabled;
	}

	/**
	 * @return the documentDownloadEnabled
	 */
	public boolean isDocumentDownloadEnabled() {
		return this.documentDownloadEnabled;
	}

	/**
	 * @param documentDownloadEnabled the documentDownloadEnabled to set
	 */
	public void setDocumentDownloadEnabled(final boolean documentDownloadEnabled) {
		this.documentDownloadEnabled = documentDownloadEnabled;
	}

	/**
	 * @return the cobrandName
	 */
	public String getCobrandName() {
		return this.cobrandName;
	}

	/**
	 * @param cobrandName the cobrandName to set
	 */
	public void setCobrandName(final String cobrandName) {
		this.cobrandName = cobrandName;
	}

	/**
	 * @return the cobrandDisplayName
	 */
	public String getCobrandDisplayName() {
		return this.cobrandDisplayName;
	}

	/**
	 * @param cobrandDisplayName the cobrandDisplayName to set
	 */
	public void setCobrandDisplayName(final String cobrandDisplayName) {
		this.cobrandDisplayName = cobrandDisplayName;
	}

	/**
	 * @return the enviornmentMode
	 */
	public String getEnviornmentMode() {
		return this.enviornmentMode;
	}

	/**
	 * @param enviornmentMode the enviornmentMode to set
	 */
	public void setEnviornmentMode(final String enviornmentMode) {
		this.enviornmentMode = enviornmentMode;
	}

	/**
	 * @return the deploymentMode
	 */
	public String getDeploymentMode() {
		return this.deploymentMode;
	}

	/**
	 * @param deploymentMode the deploymentMode to set
	 */
	public void setDeploymentMode(final String deploymentMode) {
		this.deploymentMode = deploymentMode;
	}

	/**
	 * @return the cobrandPresentStatus
	 */
	public String getCobrandPresentStatus() {
		return this.cobrandPresentStatus;
	}

	/**
	 * @param cobrandPresentStatus the cobrandPresentStatus to set
	 */
	public void setCobrandPresentStatus(final String cobrandPresentStatus) {
		this.cobrandPresentStatus = cobrandPresentStatus;
	}

	/**
	 * @return the documentTypesAllowrd
	 */
	public String getDocumentTypesAllowrd() {
		return this.documentTypesAllowrd;
	}

	/**
	 * @param documentTypesAllowrd the documentTypesAllowrd to set
	 */
	public void setDocumentTypesAllowrd(final String documentTypesAllowrd) {
		this.documentTypesAllowrd = documentTypesAllowrd;
	}

	/**
	 * @return the cobrandStatus
	 */
	public CobrandStatus getCobrandStatus() {
		return this.cobrandStatus;
	}

	/**
	 * @param cobrandStatus the cobrandStatus to set
	 * @return
	 */
	public CobrandInfo setCobrandStatus(final CobrandStatus cobrandStatus) {
		this.cobrandStatus = cobrandStatus;
		return this;
	}

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return this.environment;
	}

	/**
	 * @param environment the environment to set
	 * @return
	 */
	public CobrandInfo setEnvironment(final Environment environment) {
		this.environment = environment;
		return this;
	}

	/**
	 * @return the dataCenter
	 */
	public DataCenter getDataCenter() {
		return this.dataCenter;
	}

	/**
	 * @param dataCenter the dataCenter to set
	 * @return
	 */
	public CobrandInfo setDataCenter(final DataCenter dataCenter) {
		this.dataCenter = dataCenter;
		return this;
	}

	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return this.dbName;
	}

	/**
	 * @param dbName the dbName to set
	 * @return
	 */
	public CobrandInfo setDbName(final String dbName) {
		this.dbName = dbName;
		return this;
	}

	public boolean isOauthEnabled() {
		return this.oauthEnabled;
	}

	public void setOauthEnabled(boolean oauthEnabled) {
		this.oauthEnabled = oauthEnabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channelId == null) ? 0 : channelId.hashCode());
		result = prime * result + ((cobrandId == null) ? 0 : cobrandId.hashCode());
		result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CobrandInfo other = (CobrandInfo) obj;
		if (channelId == null) {
			if (other.channelId != null)
				return false;
		} else if (!channelId.equals(other.channelId))
			return false;
		if (cobrandId == null) {
			if (other.cobrandId != null)
				return false;
		} else if (!cobrandId.equals(other.cobrandId))
			return false;
		if (dbName == null) {
			if (other.dbName != null)
				return false;
		} else if (!dbName.equals(other.dbName))
			return false;
		return true;
	}

}
