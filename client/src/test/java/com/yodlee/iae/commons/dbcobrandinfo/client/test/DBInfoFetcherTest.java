/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.client.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;

import com.yodlee.iae.framework.test.client.ClientIntegrationTest;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 16-May-2016
 */
@Service
public class DBInfoFetcherTest extends ClientIntegrationTest {

  private static Logger logger = LoggerFactory.getLogger(DBInfoFetcherTest.class);

  @Test
  public void testDBInfoFetcher() {
    logger.info("got it..");
  }
}
