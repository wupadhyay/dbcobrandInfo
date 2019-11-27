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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * @author Nitish Kashyap (nkashyap@yodlee.com)
 * @since Aug 21, 2019
 */
public class GZIPResponseWrapper extends HttpServletResponseWrapper {
	  protected HttpServletResponse origResponse = null;
	  protected ServletOutputStream stream = null;
	  protected PrintWriter writer = null;
	  protected int inpCompressionLevel;

	  public GZIPResponseWrapper(HttpServletResponse response, int inpCompressionLevel) {
	    super(response);
	    origResponse = response;
	    this.inpCompressionLevel=inpCompressionLevel;
	  }

	  public ServletOutputStream createOutputStream() throws IOException {
	    return (new GZIPResponseStream(origResponse,inpCompressionLevel));
	  }

	  public void finishResponse() {
	    try {
	      if (writer != null) {
	        writer.close();
	      } else {
	        if (stream != null) {
	          stream.close();
	        }
	      }
	    } catch (IOException e) {}
	  }

	  public void flushBuffer() throws IOException {
	    stream.flush();
	  }

	  public ServletOutputStream getOutputStream() throws IOException {
	    if (writer != null) {
	      throw new IllegalStateException("getWriter() has already been called!");
	    }

	    if (stream == null)
	      stream = createOutputStream();
	    return (stream);
	  }

	  public PrintWriter getWriter() throws IOException {
	    if (writer != null) {
	      return (writer);
	    }

	    if (stream != null) {
	      throw new IllegalStateException("getOutputStream() has already been called!");
	    }

	   stream = createOutputStream();
	   writer = new PrintWriter(new OutputStreamWriter(stream, "UTF-8"));
	   return (writer);
	  }

	  public void setContentLength(int length) {}
	}