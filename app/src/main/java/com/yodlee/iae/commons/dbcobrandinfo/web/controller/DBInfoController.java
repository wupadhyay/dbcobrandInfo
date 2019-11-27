/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.web.controller;

import static com.yodlee.iae.commons.dbcobrandinfo.web.ServiceRoutes.*;
import static org.springframework.http.HttpStatus.OK;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yodlee.iae.commons.dbcobrandinfo.cache.DBInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.DBInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 15-May-2016
 */
@RestController
@RequestMapping(DATABASE)
public class DBInfoController {

  @Autowired
  private DBInfoCache dbInfoCache;

  /**
   * @param dbname
   */
  @GetMapping(OLTP)
  public ResponseEntity<Set<DBInfo>> getOLTPDBInfos() {
    final Set<DBInfo> dbInfos = new HashSet<>(this.dbInfoCache.getOltpDBInfoMap().values());
    return new ResponseEntity<>(dbInfos, OK);
  }

  /**
   * @param dbname
   */
  @GetMapping(OLTP + "/{lookup}")
  public ResponseEntity<Object> getOLTPDBInfo(@PathVariable String lookup) {
    final Environment environment = Environment.from(lookup);
    if (environment != null) {
      return new ResponseEntity<>(this.dbInfoCache.getEnvironmentDBInfos().get(environment), OK);
    }
    return new ResponseEntity<>(this.dbInfoCache.getOltpDBInfoMap().get(lookup), OK);
  }

  /**
   * @param dbname
   */
  @GetMapping(FIREMEM)
  public ResponseEntity<Set<DBInfo>> getFirememDBInfos() {
    final Set<DBInfo> dbInfos = new HashSet<>(this.dbInfoCache.getFirememOLTPDBInfoMap().values());
    return new ResponseEntity<>(dbInfos, OK);
  }

  /**
   * @param dbname
   */
  @GetMapping(FIREMEM + "/{lookup}")
  public ResponseEntity<DBInfo> getFirememDBInfo(@PathVariable String lookup) {
    final Environment environment = Environment.from(lookup);
    if (environment != null) {
      return new ResponseEntity<>(this.dbInfoCache.getFirememEnvironmentDBInfos(environment), OK);
    }
    return new ResponseEntity<>(this.dbInfoCache.getFirememOLTPDBInfoMap().get(lookup), OK);
  }
}
