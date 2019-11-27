/**
 * Copyright (c) Yodlee, Inc. All Rights Reserved.
 */ 
package com.yodlee.iae.commons.dbcobrandinfo.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.CobrandStatus;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.EnvironmentInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 21-May-2016
 */
public abstract class AbstractCobrandCache implements CobrandCache {

  private static Logger logger = LoggerFactory.getLogger(AbstractCobrandCache.class);

  protected Map<Environment, Set<CobrandInfo>> environmentCobrandInfos = new HashMap<>();
  protected Map<Environment, Set<CobrandInfo>> tempEnvironmentCobrandInfos = new HashMap<>();

  protected Map<DataCenter, Set<CobrandInfo>> datacenterCobrandInfos = new HashMap<>();

  protected Map<Environment, Map<Long, CobrandInfo>> environmentCobrandInfoMap = new HashMap<>();
  protected Map<Environment, Map<Long, CobrandInfo>> tempEnvironmentCobrandInfoMap = new HashMap<>();

  protected Map<DataCenter, Map<Long, CobrandInfo>> datacenterCobrandInfoMap = new HashMap<>();

  protected Map<Environment, Map<Long, String>> environmentCobrandIdNamesMap = new HashMap<>();
  protected Map<Environment, Map<Long, String>> tempEnvironmentCobrandIdNamesMap = new HashMap<>();

  protected Map<DataCenter, Map<Long, String>> dataCenterCobrandIdNamesMap = new HashMap<>();

  protected final Map<String, Set<CobrandInfo>> dbCobrandInfos = new HashMap<>();

  protected final Map<String, Map<Long, CobrandInfo>> dbCobrandInfoMap = new HashMap<>();

  protected final Map<String, Map<Long, String>> dbCobrandIdNamesMap = new HashMap<>();

  protected Map<Environment, Set<String>> environmentDbNamesMap = new HashMap<>();
  protected Map<Environment, Set<String>> tempEnvironmentDbNamesMap = new HashMap<>();

  protected Map<DataCenter, Set<String>> dataCenterDbNamesMap = new HashMap<>();

  protected Map<Long, CobrandInfo> cobrandInfoMap = new HashMap<>();

  protected Map<Long, String> cobrandIdNames = new HashMap<>();

  protected Map<Environment, Map<Long, String>> envOauthCobrandInfoMap = new HashMap<>();
  protected Map<Environment, Map<Long, String>> tempEnvOauthCobrandInfoMap = new HashMap<>();

  protected Map<Environment, Set<Long>> cobDuplicateEnvironment = new HashMap<>();
  protected Map<Environment, Set<Long>> tempCobDuplicateEnvironment = new HashMap<>();

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * @return
   */
  protected abstract boolean isIgnoreInActive();

  /**
   * @return
   */
  protected boolean displayIgnoringCobrandDetails() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#getCobrand(java.lang.
   * Long)
   */
  @Override
  public CobrandInfo getCobrand(final Long cobrandId) {
    return this.cobrandInfoMap.get(cobrandId);
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#getCobrands(com.
   * yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment)
   */
  @Override
  public Set<CobrandInfo> getCobrands(final Environment environment) {
    return this.environmentCobrandInfos.get(environment);
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#getCobrands(com.
   * yodlee.tools.common.dbcobrandinfo.model.lookup.DataCenter)
   */
  @Override
  public Set<CobrandInfo> getCobrands(DataCenter dataCenter) {
    return this.datacenterCobrandInfos.get(dataCenter);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#getDBName(java.lang.
   * Long)
   */
  @Override
  public String getDBName(final Long cobrandId) {
    return this.getCobrand(cobrandId).getDbName();
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#getCobrandIdNames(com
   * .yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment)
   */
  @Override
  public Map<Long, String> getCobrandIdNames(final Environment environment) {
    return this.environmentCobrandIdNamesMap.get(environment);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#getCobrandIdNames(com
   * .yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter)
   */
  @Override
  public Map<Long, String> getCobrandIdNames(final DataCenter dataCenter) {
    return this.dataCenterCobrandIdNamesMap.get(dataCenter);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#getCobrand(com.yodlee
   * .iae.commons.dbcobrandinfo.model.lookup.Environment, java.lang.Long)
   */
  @Override
  public CobrandInfo getCobrand(final Environment environment, final Long cobrandId) {
    return this.environmentCobrandInfoMap.get(environment).get(cobrandId);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#getCobrand(com.yodlee
   * .iae.commons.dbcobrandinfo.model.lookup.DataCenter, java.lang.Long)
   */
  @Override
  public CobrandInfo getCobrand(DataCenter dataCenter, Long cobrandId) {
    return this.datacenterCobrandInfoMap.get(dataCenter).get(cobrandId);
  }

  /*
   * (non-Javadoc)
   * @see com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache#
   * getEnvironmentCobrandInfos()
   */
  @Override
  public Map<Environment, Set<CobrandInfo>> getEnvironmentCobrandInfos() {
    return this.environmentCobrandInfos;
  }

  /**
   * @return
   */
  public Set<CobrandInfo> getCobrands() {
    return new HashSet<>(this.cobrandInfoMap.values());
  }

  /**
   * @param env
   */
  public void initializeRef(EnvironmentInfo environmentInfo) {
    this.intializeRef(environmentInfo.getId());

  }

  /**
   * @param id
   */
  public void intializeRef(int id) {
    logger.info("Initilizing cobrand cache for environment: {}", Environment.from(id).getName());
    final Environment e = Environment.from(id);
    /*this.environmentCobrandInfos.put(e, new HashSet<>());
    this.environmentCobrandInfoMap.put(e, new HashMap<>());
    this.environmentCobrandIdNamesMap.put(e, new HashMap<>());
    this.environmentDbNamesMap.put(e, new HashSet<>());
    this.envOauthCobrandInfoMap.put(e, new HashMap<>());
    this.cobDuplicateEnvironment.put(e, new HashSet<>());*/
    
    //adding new temp maps, so main maps are not altered while recaching
    this.tempEnvironmentCobrandInfos.put(e, new HashSet<>());
    this.tempEnvironmentCobrandInfoMap.put(e, new HashMap<>());
    this.tempEnvironmentCobrandIdNamesMap.put(e, new HashMap<>());
    this.tempEnvironmentDbNamesMap.put(e, new HashSet<>());
    this.tempEnvOauthCobrandInfoMap.put(e, new HashMap<>());
    this.tempCobDuplicateEnvironment.put(e, new HashSet<>());
  }

  /**
   * @param dataCenter
   */
  protected void initializeDatacenter(DataCenter dataCenter) {
    if (null != dataCenter) {
      logger.info("Datacenter details: {}, {}", dataCenter.getId(), dataCenter.name());
      if (!this.datacenterCobrandInfos.containsKey(dataCenter)) {
        this.datacenterCobrandInfos.put(dataCenter, new HashSet<>());
      }
      if (!this.datacenterCobrandInfoMap.containsKey(dataCenter)) {
        this.datacenterCobrandInfoMap.put(dataCenter, new HashMap<>());
      }
      if (!this.dataCenterCobrandIdNamesMap.containsKey(dataCenter)) {
        this.dataCenterCobrandIdNamesMap.put(dataCenter, new HashMap<>());
      }
      if (!this.dataCenterDbNamesMap.containsKey(dataCenter)) {
        this.dataCenterDbNamesMap.put(dataCenter, new HashSet<>());
      }     
    }
  }

  protected void initializeCobrandCache(Object... args) {
    final CobrandDetailsFetcher cache = this.applicationContext.getBean(CobrandDetailsFetcher.class, args);
    cache.initialize();
    this.doMap(cache);
  }
  
  /**
   * 
   */
  protected void refreshMaps() {
	  logger.info("Refreshing data from temp maps to main Maps start");
	  logger.info("=================================================");  
      this.cobDuplicateEnvironment = this.tempCobDuplicateEnvironment;     
      this.environmentCobrandInfos = this.tempEnvironmentCobrandInfos;    
      this.environmentCobrandIdNamesMap = this.tempEnvironmentCobrandIdNamesMap;    
      this.environmentCobrandInfoMap = this.tempEnvironmentCobrandInfoMap;     
      this.envOauthCobrandInfoMap = this.tempEnvOauthCobrandInfoMap;    
      this.environmentDbNamesMap = this.tempEnvironmentDbNamesMap;     
      logger.info("=================================================");
      logger.info("Refreshing data from temp maps to main Maps done");
  }

  /**
   * @param cache
   */
  protected void doMap(final CobrandDetailsFetcher cache) {
    cache.getCobrandInfos().forEach(c -> {

      final CobrandStatus cobrandStatus = c.getCobrandStatus();
      if ((cobrandStatus != CobrandStatus.ACTIVE) && this.isIgnoreInActive()) {
        return;
      }
      logger.debug("Adding cobrand cache for {} of environment {} in DC {}", c.getCobrandId(), c.getEnvironment(),
          c.getDataCenter());
      logger.trace("Populating cobrand master cache");
      this.populateMasterDetailCache(c);

      logger.trace("Populating cobrand environment cache");
      this.populateEnvironmentCobrandCache(c);

      logger.trace("Populating cobrand data center cache");
      this.populateDatacenterCobrandCache(c);

      logger.trace("Populating cobrand db cache");
      this.populateDbCobrandCache(c);

    });
  }

  protected void populateDbCobrandCache(final CobrandInfo c) {
    if (c.getDbName() != null) {
      if (!this.dbCobrandIdNamesMap.containsKey(c.getDbName())) {
        this.dbCobrandIdNamesMap.put(c.getDbName(), new HashMap<>());       
        this.dbCobrandInfoMap.put(c.getDbName(), new HashMap<>());      
        this.dbCobrandInfos.put(c.getDbName(), new HashSet<>());
      }
      this.dbCobrandIdNamesMap.get(c.getDbName()).put(c.getCobrandId(), c.getCobrandDisplayName());      
      this.dbCobrandInfoMap.get(c.getDbName()).put(c.getCobrandId(), c);    
      this.dbCobrandInfos.get(c.getDbName()).add(c);     
    }   
  }

  protected void populateDatacenterCobrandCache(final CobrandInfo c) {
    if (null != c.getDataCenter()) {
      if (this.dataCenterCobrandIdNamesMap.get(c.getDataCenter()).containsKey(c.getCobrandId())) {
        // TODO: At environment level - duplicate cobrand_id across
        // multiple oltp use-case to be handled later, skipping now.
      } else {
        this.datacenterCobrandInfos.get(c.getDataCenter()).add(c);
        this.datacenterCobrandInfoMap.get(c.getDataCenter()).put(c.getCobrandId(), c);
        this.dataCenterCobrandIdNamesMap.get(c.getDataCenter()).put(c.getCobrandId(), c.getCobrandDisplayName());
      }
      if (!this.dataCenterDbNamesMap.get(c.getDataCenter()).contains(c.getDbName())) {
        this.dataCenterDbNamesMap.get(c.getDataCenter()).add(c.getDbName());
      }
    }
  }

  protected void populateEnvironmentCobrandCache(final CobrandInfo c) {
	 /*if (this.environmentCobrandIdNamesMap.get(c.getEnvironment()).containsKey(c.getCobrandId())) {
      // TODO: At environment level - duplicate cobrand_id across
      // multiple oltp use-case to be handled later, skipping now.
      if (this.displayIgnoringCobrandDetails()) {
        logger.warn("Same cobrand {} cannot be active in multiple DBs of same environment {}", c.getCobrandId(),
            c.getEnvironment().getName());
      }
      this.cobDuplicateEnvironment.get(c.getEnvironment()).add(c.getCobrandId());
    } else {
      this.environmentCobrandInfos.get(c.getEnvironment()).add(c);
      this.environmentCobrandIdNamesMap.get(c.getEnvironment()).put(c.getCobrandId(), c.getCobrandDisplayName());
      this.environmentCobrandInfoMap.get(c.getEnvironment()).put(c.getCobrandId(), c);

      if (c.isOauthEnabled()) {
        this.envOauthCobrandInfoMap.get(c.getEnvironment()).put(c.getCobrandId(), c.getCobrandDisplayName());
      }

      if (!this.environmentDbNamesMap.get(c.getEnvironment()).contains(c.getDbName())) {
        this.environmentDbNamesMap.get(c.getEnvironment()).add(c.getDbName());
      }
    }*/
	  
	  //Using temp maps instead of main maps
	  if (this.tempEnvironmentCobrandIdNamesMap.get(c.getEnvironment()).containsKey(c.getCobrandId())) {
      // TODO: At environment level - duplicate cobrand_id across
      // multiple oltp use-case to be handled later, skipping now.
      if (this.displayIgnoringCobrandDetails()) {
        logger.warn("Same cobrand {} cannot be active in multiple DBs of same environment {}", c.getCobrandId(),
            c.getEnvironment().getName());
      }
      this.tempCobDuplicateEnvironment.get(c.getEnvironment()).add(c.getCobrandId());
    } else {
      this.tempEnvironmentCobrandInfos.get(c.getEnvironment()).add(c);
      this.tempEnvironmentCobrandIdNamesMap.get(c.getEnvironment()).put(c.getCobrandId(), c.getCobrandDisplayName());    
      this.tempEnvironmentCobrandInfoMap.get(c.getEnvironment()).put(c.getCobrandId(), c);

      if (c.isOauthEnabled()) {
        this.tempEnvOauthCobrandInfoMap.get(c.getEnvironment()).put(c.getCobrandId(), c.getCobrandDisplayName());
      }

      if (!this.tempEnvironmentDbNamesMap.get(c.getEnvironment()).contains(c.getDbName())) {
        this.tempEnvironmentDbNamesMap.get(c.getEnvironment()).add(c.getDbName());
      }
    }
  }

  protected void populateMasterDetailCache(final CobrandInfo c) {
    /*
     * master detail cache
     */
    if (this.cobrandIdNames.containsKey(c.getCobrandId())) {
      // TODO: Duplicate cobrand_id across multiple oltp use-case to be
      // handled later, skipping now
      logger.debug(
          "\t Ignoring cobrand-id: {} from caching - Duplicate entry found..Cobrand will be added to envionment cob cache",
          c.getCobrandId());
    } else {
      logger.trace("\t Adding cobrand-id : {}", c.getCobrandId());
      this.cobrandIdNames.put(c.getCobrandId(), c.getCobrandDisplayName());
      this.cobrandInfoMap.put(c.getCobrandId(), c);
    }
  }

  /**
   *
   */
  public void destroy() {
    this.clear();

    this.cobrandIdNames = null;
    this.cobrandInfoMap = null;
    this.environmentCobrandInfos = null;
    this.environmentCobrandInfoMap = null;
    this.environmentCobrandIdNamesMap = null;
    this.environmentDbNamesMap = null;
    this.dataCenterCobrandIdNamesMap = null;
    this.dataCenterDbNamesMap = null;
    this.datacenterCobrandInfoMap = null;
    this.datacenterCobrandInfos = null;
    this.envOauthCobrandInfoMap = null;
    
    //adding temp maps
    this.tempEnvironmentCobrandInfos = null;
    this.tempEnvironmentCobrandIdNamesMap = null;
    this.tempEnvironmentCobrandInfoMap = null;   
    this.tempEnvOauthCobrandInfoMap = null;
    this.tempEnvironmentDbNamesMap = null;

  }

  /**
   *
   */
  protected void clear() {
    this.cobrandIdNames.clear();
    this.cobrandInfoMap.clear();
    this.environmentCobrandInfos.clear();
    this.environmentCobrandInfoMap.clear();
    this.environmentCobrandIdNamesMap.clear();
    this.environmentDbNamesMap.clear();
    this.dataCenterCobrandIdNamesMap.clear();
    this.dataCenterDbNamesMap.clear();
    this.datacenterCobrandInfoMap.clear();
    this.datacenterCobrandInfos.clear();
    this.datacenterCobrandInfoMap.clear();
    this.envOauthCobrandInfoMap.clear();
    
    //adding temp maps
    this.tempEnvironmentCobrandInfos.clear();
    this.tempEnvironmentCobrandIdNamesMap.clear();
    this.tempEnvironmentCobrandInfoMap.clear(); 
    this.tempEnvOauthCobrandInfoMap.clear();
    this.tempEnvironmentDbNamesMap.clear();
  }

  @Override
  public Set<String> getDBNames(final DataCenter dataCenter) {
    return this.dataCenterDbNamesMap.get(dataCenter);
  }

  @Override
  public Set<String> getDBNames(final Environment environment) {
    return this.environmentDbNamesMap.get(environment);
  }

  @Override
  public Map<Long, String> getOathCobrandIdNames(final Environment environment) {
    return this.envOauthCobrandInfoMap.get(environment);
  }

  @Override
  public Set<Long> getAllCobrandIds(final Environment environment) {
    final Set<Long> cobrandIds = new LinkedHashSet<>();
    this.envOauthCobrandInfoMap.get(environment).entrySet().parallelStream().forEach(k -> {
      cobrandIds.add(k.getKey());
    });
    return cobrandIds;
  }

  @Override
  public Optional<Map<Long, String>> getCobrandIdNames(final String dbName) {
    return Optional.ofNullable(this.dbCobrandIdNamesMap.get(dbName));
  }

  @Override
  public Optional<Set<Long>> getCobrandIds(final Environment environment) {
    return Optional.ofNullable(this.environmentCobrandIdNamesMap.get(environment).keySet());
  }

  @Override
  public Optional<CobrandInfo> getCobrand(final Long cobrandId, final String dbName) {
    return Optional.ofNullable(this.dbCobrandInfoMap.get(dbName).get(cobrandId));
  }

  @Override
  public Optional<Set<CobrandInfo>> getCobrands(final String dbName) {
    return Optional.ofNullable(this.dbCobrandInfos.get(dbName));
  }

  @Override
  public boolean isDuplicateCobrand(final Environment env, final Long cobrandId) {
    if (this.cobDuplicateEnvironment.containsKey(env) && this.cobDuplicateEnvironment.get(env).contains(cobrandId)) {
      return true;
    }
    return false;
  }
}
