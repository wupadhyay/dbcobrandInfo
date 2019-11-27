/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.web.controller;

import static com.yodlee.iae.commons.dbcobrandinfo.web.ServiceRoutes.ENVIRONMENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yodlee.iae.commons.dbcobrandinfo.cache.DBInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.EnvironmentInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 15-May-2016
 */
@RestController
public class EnvironmentInfoController {

  @Autowired
  private DBInfoCache dbInfoCache;

  /**
   * @param dbname
   */
  @GetMapping(ENVIRONMENT)
  public ResponseEntity<Set<EnvironmentInfo>> getEnvironmentInfos() {
    return new ResponseEntity<>(this.dbInfoCache.getEnvironmentInfos(), OK);
  }
}
