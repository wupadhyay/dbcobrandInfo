/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.cache;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 18-May-2016
 */
public interface CobrandCache {

  String getDBName(Long cobrandId);

  CobrandInfo getCobrand(Long cobrandId);

  Optional<CobrandInfo> getCobrand(Long cobrandId, String dbName);

  Optional<Map<Long, String>> getCobrandIdNames(String dbName);

  Optional<Set<CobrandInfo>> getCobrands(String dbName);

  CobrandInfo getCobrand(Environment environment, Long cobrandId);

  CobrandInfo getCobrand(DataCenter dataCenter, Long cobrandId);

  Set<CobrandInfo> getCobrands(Environment environment);

  Set<CobrandInfo> getCobrands(DataCenter dataCenter);

  Map<Environment, Set<CobrandInfo>> getEnvironmentCobrandInfos();

  Set<String> getDBNames(Environment environment);

  Set<String> getDBNames(DataCenter environment);

  Optional<Set<Long>> getCobrandIds(Environment environment);

  Map<Long, String> getCobrandIdNames(Environment environment);

  Map<Long, String> getOathCobrandIdNames(Environment environment);

  boolean isDuplicateCobrand(Environment env, Long cobrandId);

  Set<Long> getAllCobrandIds(Environment environment);

  Map<Long, String> getCobrandIdNames(DataCenter dataCenter);

}
