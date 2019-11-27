/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util;

/**
 * @author wupadhyay
 *
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CobrandUtil {

	static Logger logger = LoggerFactory.getLogger(CobrandUtil.class);

	public static void printException(Logger logger, Exception e) {
		StringWriter sw = new StringWriter();
		try {
			e.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
		} catch (Exception e1) {
			logger.error("Exception from Util.printExzception", e1);
		}
	}

}
