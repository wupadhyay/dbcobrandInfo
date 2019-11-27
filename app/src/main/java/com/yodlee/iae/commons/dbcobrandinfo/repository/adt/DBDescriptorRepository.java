/**
 * Copyright (c) 2016 Yodlee Inc. All Rights
 * Reserved. This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 */

package com.yodlee.iae.commons.dbcobrandinfo.repository.adt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.DBDescriptor;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Mar 17, 2016
 */
public interface DBDescriptorRepository extends JpaRepository<DBDescriptor, Long> {

  @Query("FROM DBDescriptor WHERE oltp = :oltp AND deleted = :deleted AND firemem  = :firemem AND environment.id in :environment AND sr = :sr ORDER BY descriptorId")
  public List<DBDescriptor> findAll(@Param("oltp") boolean oltp, @Param("deleted") boolean deleted,
      @Param("firemem") boolean firemem, @Param("environment") List<Integer> environment, @Param("sr") boolean sr);

  /**
   * @param string
   * @return
   */
  public List<DBDescriptor> findByName(String name);
}
