/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.client.test.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.yodlee.iae.commons.dbcobrandinfo.client.test.entities.CacheInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 16-May-2016
 */
@Repository
public class CacheInfoDAO {

  @PersistenceContext
  private EntityManager em;

  public List<CacheInfo> findByID(final Long id) {
    if (this.em == null) {
      return Collections.emptyList();
    }

    final String query = "SELECT c FROM CacheInfo c WHERE c.cacheItemId = ?1";
    final TypedQuery<CacheInfo> qu = this.em.createQuery(query, CacheInfo.class);
    qu.setParameter(1, id);
    return qu.getResultList();
  }
}
