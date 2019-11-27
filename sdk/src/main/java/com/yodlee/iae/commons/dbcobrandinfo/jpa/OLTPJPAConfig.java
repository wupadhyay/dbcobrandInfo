/**
 * Copyright (c) 2016 Yodlee Inc. All Rights
 * Reserved. This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 */

package com.yodlee.iae.commons.dbcobrandinfo.jpa;

import java.util.List;
import java.util.Map;

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

import com.yodlee.iae.commons.dbcobrandinfo.condition.ConditionalOnOLTPConnectionCacheEnabled;
import com.yodlee.iae.commons.dbcobrandinfo.datasource.MultiDataSource;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Mar 17, 2016
 */
@Configuration
@ConditionalOnOLTPConnectionCacheEnabled
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "${tools.dbcobrandinfo.oltp.repositories.package}", entityManagerFactoryRef = "oltpEntityManagerFactory", transactionManagerRef = "oltpTransactionManager")
public class OLTPJPAConfig extends AbstractJPAConfig {

  @Value("${tools.dbcobrandinfo.oltp.pool.size.min}")
  private int minPoolsize;

  @Value("${tools.dbcobrandinfo.oltp.pool.size.max}")
  private int maxPoolsize;

  @Value("#{'${tools.dbcobrandinfo.oltp.entities.package}'.split(',')}")
  private List<String> entitiesPackage;

  @Autowired
  @Bean(name = "oltpDataSources")
  public MultiDataSource getOLTPRoutingDataSource(
      @Qualifier("oltpDatasources") final Map<String, DataSource> dataSources,
      @Qualifier("defaultDatasource") final DataSource defaultDatasource) {
    return super.getMultiDataSource(dataSources, defaultDatasource);
  }

  @Autowired
  @Bean(name = "oltpEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("oltpDataSources") final DataSource dataSource) {
    return super.getEntityManagerFactory(dataSource, "oltp", this.entitiesPackage.toArray(new String[0]));
  }

  /**
   * @param sessionFactory
   * @return
   */
  @Autowired
  @Bean(name = "oltpTransactionManager")
  public JpaTransactionManager transactionManager(
      @Qualifier("oltpEntityManagerFactory") final EntityManagerFactory entityManagerFactory,
      @Qualifier("oltpDataSources") final DataSource dataSource) {
    return super.getTransactionManager(dataSource, entityManagerFactory);
  }
}
