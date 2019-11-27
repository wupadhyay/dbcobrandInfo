/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.yodlee.iae.commons.dbcobrandinfo.cache.DBInfoCache;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 11, 2016
 */
public class DBInfoRepositoryTest extends BaseDBCobrandInfoTest {

  private static Logger logger = LoggerFactory.getLogger(DBInfoRepositoryTest.class);

  @Autowired
  private DBInfoCache dbInfoCache;

  @Test
  public void testLoadingOfAllOltps() {
    final List<Integer> ids = this.dbInfoCache.getEnvironmentIds();
    logger.info("Environment Ids: {}", ids);
  }
}
