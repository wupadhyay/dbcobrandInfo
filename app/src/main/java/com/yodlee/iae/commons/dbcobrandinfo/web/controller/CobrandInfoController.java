/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.web.controller;

import static com.yodlee.iae.commons.dbcobrandinfo.web.ServiceRoutes.COBRAND;
import static org.springframework.http.HttpStatus.OK;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandCache;
import com.yodlee.iae.commons.dbcobrandinfo.cache.DBInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.DataCenter;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;
import com.yodlee.iae.commons.dbcobrandinfo.model.response.CobrandInfo;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 15-May-2016
 */
@RestController
@RequestMapping(value = { COBRAND })
public class CobrandInfoController {

	@Autowired
	private CobrandCache cobrandCache;

	@Autowired
	private DBInfoCache dbInfoCache;

	/**
	 * @param lookup
	 * @param cobrandId
	 * @return
	 */
	@GetMapping("/{lookup}")
	public ResponseEntity<Set<CobrandInfo>> getCobrandInfos(@PathVariable String lookup) {
		final Environment environment = Environment.from(lookup);
		if (environment != null) {
			return this.getEnvironmentCobrands(environment);
		}

		final DataCenter dataCenter = DataCenter.fromName(lookup);
		if (dataCenter != null) {
			return this.getDatacenterCobrands(dataCenter);
		}

		if (this.dbInfoCache.getOltpDBInfoMap().containsKey(lookup)) {
			return this.getDBCobrands(lookup);
		}

		return null;
	}

	/**
	 * @param userEnvironment
	 * @param cobrandId
	 * @return
	 */
	@GetMapping("/{lookup}/{cobrandId}")
	public ResponseEntity<CobrandInfo> getCobrandInfo(@PathVariable String lookup, @PathVariable Long cobrandId) {

		final Environment environment = Environment.from(lookup);
		if (environment != null) {
			return this.getEnvironmentCobrand(environment, cobrandId);
		}

		final DataCenter dataCenter = DataCenter.fromName(lookup);
		if (dataCenter != null) {
			return this.getDatacenterCobrand(dataCenter, cobrandId);
		}

		if (this.dbInfoCache.getOltpDBInfoMap().containsKey(lookup)) {
			return this.getDBCobrand(lookup, cobrandId);
		}

		return null;
	}

	/**
	 * @param dataCenter
	 * @return
	 */
	private ResponseEntity<Set<CobrandInfo>> getDatacenterCobrands(DataCenter dataCenter) {
		final Set<CobrandInfo> cobrandInfo = this.cobrandCache.getCobrands(dataCenter);
		return new ResponseEntity<>(cobrandInfo, OK);
	}

	/**
	 * @param environment
	 * @return
	 */
	private ResponseEntity<Set<CobrandInfo>> getEnvironmentCobrands(Environment environment) {
		final Set<CobrandInfo> cobrandInfo = this.cobrandCache.getCobrands(environment);
		return new ResponseEntity<>(cobrandInfo, OK);
	}

	/**
	 * @param dbName
	 * @return
	 */
	private ResponseEntity<Set<CobrandInfo>> getDBCobrands(String dbName) {
		Optional<Set<CobrandInfo>> cobrands = this.cobrandCache.getCobrands(dbName);
		if (cobrands.isPresent()) {
			final Set<CobrandInfo> cobrandInfo = cobrands.get();
			return new ResponseEntity<>(cobrandInfo, OK);
		} else {
			return new ResponseEntity<>(new HashSet<CobrandInfo>(), OK);
		}
	}

	/**
	 * @param dataCenter
	 * @return
	 */
	private ResponseEntity<CobrandInfo> getDBCobrand(String dbName, Long cobrandId) {
		final CobrandInfo cobrandInfo = this.cobrandCache.getCobrand(cobrandId, dbName).get();
		return new ResponseEntity<>(cobrandInfo, OK);
	}

	/**
	 * @param dataCenter
	 * @return
	 */
	private ResponseEntity<CobrandInfo> getDatacenterCobrand(DataCenter dataCenter, Long cobrandId) {
		final CobrandInfo cobrandInfo = this.cobrandCache.getCobrand(dataCenter, cobrandId);
		return new ResponseEntity<>(cobrandInfo, OK);
	}

	/**
	 * @param environment
	 * @param cobrandId
	 * @return
	 */
	private ResponseEntity<CobrandInfo> getEnvironmentCobrand(Environment environment, Long cobrandId) {
		final CobrandInfo cobrandInfo = this.cobrandCache.getCobrand(environment, cobrandId);
		return new ResponseEntity<>(cobrandInfo, OK);
	}
}
