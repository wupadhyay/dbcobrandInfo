/**
 * Copyright (c) 2016 Yodlee Inc. All Rights
 * Reserved. This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 */

package com.yodlee.iae.commons.dbcobrandinfo.jpa;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yodlee.iae.commons.dbcobrandinfo.datasource.MultiDataSource;
import com.yodlee.iae.framework.jpa.JPAConfig;

import oracle.jdbc.driver.OracleDriver;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Mar 17, 2016
 */
public abstract class AbstractJPAConfig implements JPAConfig {

  /**
   * @param url
   * @param username
   * @param password
   * @param minPoolsize
   * @param maxPoolsize
   * @return
   */
  protected DataSource getDataSource(final String url, final String username, final String password,
      final int minPoolsize, final int maxPoolsize) {
    final ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setDriverClass(OracleDriver.class.getName());
      //dataSource.setUnreturnedConnectionTimeout(180);
    } catch (final PropertyVetoException ex) {
      throw new RuntimeException("Failed to load ADT DB Driver class.", ex);
    }
    dataSource.setJdbcUrl(url);
    dataSource.setUser(username);
    dataSource.setPassword(password);
    dataSource.setMinPoolSize(minPoolsize);
    dataSource.setMaxPoolSize(maxPoolsize);
    return dataSource;
  }

  /**
   * @param dataSources
   * @param defaultDatasource
   * @return
   */
  protected MultiDataSource getMultiDataSource(final Map<String, DataSource> dataSources,
      final DataSource defaultDatasource) {
    final MultiDataSource oltpDataSources = new MultiDataSource();
    oltpDataSources.setTargetDataSources(new HashMap<>(dataSources));
    oltpDataSources.setDefaultTargetDataSource(defaultDatasource);
    return oltpDataSources;
  }

  /**
   * @param dataSource
   * @param name
   * @param basePackage
   * @return
   */
  protected LocalContainerEntityManagerFactoryBean getEntityManagerFactory(final DataSource dataSource,
      final String name, String... basePackage) {
    final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setPackagesToScan(basePackage);
    return entityManagerFactoryBean;
  }

  /**
   * @param entityManagerFactory
   * @param dataSource
   * @return
   */
  protected JpaTransactionManager getTransactionManager(final DataSource dataSource,
      final EntityManagerFactory entityManagerFactory) {
    final JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setDataSource(dataSource);
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }
}
