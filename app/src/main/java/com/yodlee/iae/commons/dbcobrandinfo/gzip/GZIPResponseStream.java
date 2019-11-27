/**
 *
 * Copyright (c) 2016 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nitish Kashyap (nkashyap@yodlee.com)
 * @since Aug 21, 2019
 */

public class GZIPResponseStream extends ServletOutputStream {
	
	  private static Logger logger = LoggerFactory.getLogger(GZIPResponseStream.class);
	
	  protected ByteArrayOutputStream baos = null;
	  protected GZIPOutputStream gzipstream = null;
	  protected boolean closed = false;
	  protected HttpServletResponse response = null;
	  protected ServletOutputStream output = null;
	  
	 
	  
	  public GZIPResponseStream(HttpServletResponse response,int inpCompressionLevel) throws IOException {
	    super();
	    closed = false;
	    this.response = response;
	    this.output = response.getOutputStream();
	    baos = new ByteArrayOutputStream();
	    //gzipstream = new GZIPOutputStream(baos);

	    int level=Deflater.NO_COMPRESSION;
	    
	    switch(inpCompressionLevel)
	    {
	    	case 1:  level=Deflater.BEST_COMPRESSION;
	    			 break;
	    	case 2:  level=Deflater.BEST_SPEED;
	    			 break;
	    	default: level=Deflater.NO_COMPRESSION;		
	    }
	    final int finalLevel=level;
	    //System.out.println("nkash: "+level);
	    logger.info("compression level is set at: "+level);
	    gzipstream = new GZIPOutputStream(baos){{def.setLevel(finalLevel);}};
	  }

	  public void close() throws IOException {
	    if (closed) {
	      throw new IOException("This output stream has already been closed");
	    }
	    gzipstream.finish();

	    byte[] bytes = baos.toByteArray();


	    response.addHeader("Content-Length", 
	                       Integer.toString(bytes.length)); 
	    response.addHeader("Content-Encoding", "gzip");
	    output.write(bytes);
	    output.flush();
	    output.close();
	    closed = true;
	  }

	  public void flush() throws IOException {
	    if (closed) {
	      throw new IOException("Cannot flush a closed output stream");
	    }
	    gzipstream.flush();
	  }

	  public void write(int b) throws IOException {
	    if (closed) {
	      throw new IOException("Cannot write to a closed output stream");
	    }
	    gzipstream.write((byte)b);
	  }

	  public void write(byte b[]) throws IOException {
	    write(b, 0, b.length);
	  }

	  public void write(byte b[], int off, int len) throws IOException {
	    if (closed) {
	      throw new IOException("Cannot write to a closed output stream");
	    }
	    gzipstream.write(b, off, len);
	  }

	  public boolean closed() {
	    return (this.closed);
	  }
	  
	  public void reset() {
	    //noop
	  }

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener arg0) {
		// TODO Auto-generated method stub
		
	}
	}