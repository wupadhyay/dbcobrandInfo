/**
 *
 * Copyright (c) 2016 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.gzip;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Nitish Kashyap (nkashyap@yodlee.com)
 * @since Aug 21, 2019
 */

@Component
public class GZIPFilter implements Filter {

	 @Value("${tools.compression.level:0}")
	 private int inpCompressionLevel;
	 
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
		throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String ae = request.getHeader("accept-encoding");
			//System.out.println("nkash: "+req.toString());
			//System.out.println("nkash: "+req.get);
			//logger.info("Request: "+req.toString());
			if (ae != null && ae.indexOf("gzip") != -1) {
				GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response,inpCompressionLevel);
				chain.doFilter(req, wrappedResponse);
				wrappedResponse.finishResponse();
				return;
			}
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) {
		// noop
	}

	public void destroy() {
		// noop
	}
}