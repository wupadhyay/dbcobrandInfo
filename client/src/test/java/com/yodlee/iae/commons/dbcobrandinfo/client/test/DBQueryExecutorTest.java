package com.yodlee.iae.commons.dbcobrandinfo.client.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.yodlee.iae.commons.dbcobrandinfo.client.test.dao.CacheInfoDAO;
import com.yodlee.iae.commons.dbcobrandinfo.client.test.entities.CacheInfo;
import com.yodlee.iae.commons.dbcobrandinfo.client.test.repository.CacheInfoRepository;
import com.yodlee.iae.commons.dbcobrandinfo.datasource.DBContext;
import com.yodlee.iae.framework.test.client.ClientIntegrationTest;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 15, 2016
 */
public class DBQueryExecutorTest extends ClientIntegrationTest {

  @Autowired
  private CacheInfoRepository cacheInfoRepository;

  @Autowired
  private CacheInfoDAO cacheInfoDAO;

  /**
   *
   */
  @DataProvider
  private Object[][] cacheItems() {
    return new Object[][] { { "ibdv1012", 11158134L, 982L, "cacheInfo1" },
        { "ibtsbld", 1345678L, 20549L, "cacheInfo2" } };
  }

  /**
   * @param db
   * @param itemId
   * @param sid
   * @param msg
   */
  @Test(dataProvider = "cacheItems")
  public void testRepositoryCacheInfoTest(final String db, final Long itemId, final Long sid, final String msg) {
    DBContext.setDBName(db);
    final CacheInfo cacheInfo1 = this.cacheInfoRepository.findById(itemId).get();
    Assert.assertEquals(cacheInfo1.getSumInfoId(), sid, msg + " CacheInfo-SumInfo Repository Mismatch");
  }

  /**
   * @param db
   * @param itemId
   * @param sid
   * @param msg
   */
  @Test(dataProvider = "cacheItems")
  public void testEntityManagerCacheInfoTest(final String db, final Long itemId, final Long sid, final String msg) {
    DBContext.setDBName(db);
    final CacheInfo cacheInfo1 = this.cacheInfoDAO.findByID(itemId).get(0);
    Assert.assertEquals(cacheInfo1.getSumInfoId(), sid, msg + " CacheInfo-SumInfo EM Mismatch");
  }
}
