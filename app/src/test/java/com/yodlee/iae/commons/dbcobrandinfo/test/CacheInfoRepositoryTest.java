/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.yodlee.iae.commons.dbcobrandinfo.datasource.DBContext;
import com.yodlee.iae.commons.dbcobrandinfo.test.entities.CacheInfo;
import com.yodlee.iae.commons.dbcobrandinfo.test.repository.CacheInfoRepository;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 15, 2016
 */
public class CacheInfoRepositoryTest extends BaseDBCobrandInfoTest {

  @Autowired
  private CacheInfoRepository cacheInfoRepository;

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
  public void testFetchCacheItemIdTest(final String db, final Long itemId, final Long sid, final String msg) {
    DBContext.setDBName(db);
    final CacheInfo cacheInfo1 = this.cacheInfoRepository.findById(itemId).get();
    Assert.assertEquals(cacheInfo1.getSumInfoId(), sid, msg + " CacheInfo-SumInfo Mismatch");
  }
}
