/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.client.config;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yodlee.iae.commons.dbcobrandinfo.client.DBCobrandInfoFetcher;
import com.yodlee.iae.commons.dbcobrandinfo.client.cache.DBInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.DBInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 11, 2016
 */
@Configuration
public class OLTPDBConfig {

  private static Logger logger = LoggerFactory.getLogger(OLTPDBConfig.class);

  @Value("${tools.dbcobrandinfo.default.db.name}")
  private String defaultDB;

  @Value("#{'${tools.dbcobrandinfo.supported.environment.ids}'.split(',')}")
  private List<Integer> environmentIds;

  @Autowired
  private DBInfoCache dbInfoCache;

  @Autowired
  private DBCobrandInfoFetcher dbCobrandInfoFetcher;

  @Bean(name = "defaultDatasource")
  public DataSource defaultDatasource() {
    return this.dbInfoCache.getDatasourceMap().get(this.defaultDB);
  }

  /**
   * @param oltpRepository
   * @return
   */
  @Autowired
  @Bean(name = "oltpDatasources")
  public Map<String, DataSource> createOLTPDatasources() {
    this.dbCobrandInfoFetcher.fetchEnvironments().forEach(e -> {
      if (!this.environmentIds.contains(e.getId())) {
        logger.info("Environment: {} will be skipped as it is not configured for current setup", e.getName());
        return;
      }
      this.dbInfoCache.getEnvironmentInfos().add(e);
    });
    final Set<DBInfo> dbInfos = new HashSet<>();
    this.dbInfoCache.getEnvironmentInfos().forEach(e -> {
      dbInfos.addAll(this.dbCobrandInfoFetcher.fetchDBs(Environment.from(e.getId())));
      dbInfos.add(this.dbCobrandInfoFetcher.fetchFirememDB(Environment.from(e.getId())));
    });
    logger.info("total db fetched from cache: {}", dbInfos.size());
    dbInfos.forEach(this.dbInfoCache::createDataSource);
    return this.dbInfoCache.getDatasourceMap();
  }
}
