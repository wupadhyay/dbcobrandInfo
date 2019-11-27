/**
 * Copyright (c) 2016 Yodlee Inc. All Rights
 * Reserved. This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 */

package com.yodlee.iae.commons.dbcobrandinfo.jpa;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.BaseADTEntity;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Mar 17, 2016
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = BaseADTEntity.class, entityManagerFactoryRef = "adtEntityManagerFactory", transactionManagerRef = "adtTransactionManager")
public class ADTJPAConfig extends AbstractJPAConfig {

  @Value("${tools.dbcobrandinfo.database.adt.url}")
  private String url;

  @Value("${tools.dbcobrandinfo.database.adt.username}")
  private String username;

  @Value("${tools.dbcobrandinfo.database.adt.password}")
  private String password;

  @Value("${tools.dbcobrandinfo.database.adt.pool.size.min}")
  private int minPoolsize;

  @Value("${tools.dbcobrandinfo.database.adt.pool.size.max}")
  private int maxPoolsize;

  /**
   * @return
   */
  @Bean(name = { "adtDataSource" }, destroyMethod = "close")
  public DataSource dataSource() {
    return super.getDataSource(this.url, this.username, this.password, this.minPoolsize, this.maxPoolsize);
  }

  /**
   * @param dataSource
   * @return
   */
  @Autowired
  @Bean(name = "adtEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("adtDataSource") final DataSource dataSource) {
    return super.getEntityManagerFactory(dataSource, "adt", BaseADTEntity.class.getPackage().getName());
  }

  /**
   * @param sessionFactory
   * @return
   */
  @Autowired
  @Bean(name = "adtTransactionManager")
  public JpaTransactionManager transactionManager(
      @Qualifier("adtEntityManagerFactory") final EntityManagerFactory entityManagerFactory,
      @Qualifier("adtDataSource") final DataSource dataSource) {
    return super.getTransactionManager(dataSource, entityManagerFactory);
  }
}
