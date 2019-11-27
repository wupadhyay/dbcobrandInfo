/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.config;

import org.springframework.stereotype.Component;

import com.yodlee.mel.MasterElectionCallable;

@Component
public class MELMaster extends MasterElectionCallable 
{

	@Override
	public void designatedMaster() {
		setMaster(true);
	}

	@Override
	public void designatedSlave() {
		setMaster(false);

	}


}
