/**
 * Copyright (c) 2016 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 */

package com.yodlee.iae.commons.dbcobrandinfo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Mar 31, 2016
 */
public class DBConnectionValidator {

  private static Logger logger = LoggerFactory.getLogger(DBConnectionValidator.class);

  /**
   * @param url
   * @param username
   * @param password
   * @return
   */
  public boolean check(final String url, final String username, final String password) {
    boolean ret = false;
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    try {
      connection = DriverManager.getConnection(url, username, password);
      pstmt = connection.prepareStatement("select sysdate as cnt from dual");
      rset = pstmt.executeQuery();
      rset.next();
      ret = true;
    } catch (final Exception ex) {
      logger.error("Failed to verify DB connections.", ex);
    } finally {
      this.close(connection, pstmt, rset);
      connection = null;
      pstmt = null;
      rset = null;
    }

    return ret;
  }

  /**
   * @param connection
   * @param pstmt
   * @param rset
   */
  private void close(final Connection connection, final Statement pstmt, final ResultSet rset) {
    if (rset != null) {
      try {
        rset.close();
      } catch (final Exception e) {
      }
    }

    if (pstmt != null) {
      try {
        pstmt.close();
      } catch (final Exception e) {
      }
    }

    if (connection != null) {
      try {
        connection.close();
      } catch (final Exception e) {
      }
    }
  }
}
