/**
 *
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 */

package com.yodlee.iae.commons.dbcobrandinfo.client.cache;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandDetailsFetcher;
import com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.CobrandStatus;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 4, 2017
 */
@Repository
@Scope(SCOPE_PROTOTYPE)
@Transactional(readOnly = true, transactionManager = "oltpTransactionManager")
public class DefaultCobrandDetailsFetcher implements CobrandDetailsFetcher {

  private static Logger logger = LoggerFactory.getLogger(DefaultCobrandDetailsFetcher.class);

  @Autowired
  private DBCobrandInfoFetcher dbCobrandInfoFetcher;

  protected Set<CobrandInfo> cobrandInfos = null;

  private final String dbName;

  private final Environment environment;

  private final DataCenter dataCenter;

  /**
   * @param dbName
   * @param environment
   * @param dataCenter
   */
  public DefaultCobrandDetailsFetcher(final String dbName, final Environment environment,
      final DataCenter dataCenter) {
    this.dbName = dbName;
    this.environment = environment;
    this.dataCenter = dataCenter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandDetailsFetcher#
   * getCobrandInfos()
   */
  @Override
  public Set<CobrandInfo> getCobrandInfos() {
    return this.cobrandInfos;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandDetailsFetcher#initialize
   * ()
   */
  @Override
  public void initialize() {
    this.init();
  }

  /**
   *
   */
  private void init() {
    try {
      this.cobrandInfos = this.dbCobrandInfoFetcher.fetchCobrands(this.dbName);
      this.cobrandInfos.forEach(c -> {
        logger.trace("\t Reading Cobrand from DB: {}; ", c.getCobrandDisplayName());

        c.setDbName(this.dbName).setEnvironment(this.environment).setDataCenter(this.dataCenter)
            .setCobrandStatus(CobrandStatus.fromStatus(c.getCobrandPresentStatus()));
      });
    } catch (Throwable e) {
      logger.info("loading failed due to runtime exception");
    }
  }
}
