/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.config;

import static com.yodlee.iae.framework.entities.converter.NumberToBooleanConverter.NBC;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.yodlee.iae.commons.dbcobrandinfo.cache.CobrandInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.util.CobrandUtil;
import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.DBDescriptor;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;



/**
 * @author Waivaw Upadhyay (wupadhyay@yodlee.com)
 * 
 */
@Configuration
public class SRDBConfig {
	private static Logger logger = LoggerFactory.getLogger(SRDBConfig.class);

	@Autowired
	private CobrandInfoCache cobrandInfoCache;

	/**
	 * @param oltpRepository
	 * @return
	 */
	@Autowired
	@Bean(name = "srdbDatasources")
	public Map<String, DataSource> createSRDBDatasources(@Qualifier("adtDataSource") final DataSource dataSource) {
		final List<Integer> environmentIds = this.cobrandInfoCache.getEnvironmentIds();
		logger.info("Inside SRDBConfig configured environment ids: {}", environmentIds);
		try {
		environmentIds.forEach(id -> {
			final Environment e = Environment.from(id);
			this.cobrandInfoCache.getEnvMap().put(e, new LinkedHashSet<>());
		});

		final String sql = "select * from deploy_db_descriptor where is_sr = 1 and is_deleted = 0 and data_centre_id=1 and fdt_environment_id in ("
				+ environmentIds.toString().replaceAll("\\[|\\]", "") + ")";
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		this.cobrandInfoCache.getSrdbDescriptors()
				.addAll((jdbcTemplate.query(sql, (RowMapper<DBDescriptor>) (r, n) -> DBDescriptor.build()
						.setDescriptorId(r.getLong("deploy_db_descriptor_id"))
						.setEnvironmentId(r.getInt("fdt_environment_id")).setDataCenterId(r.getInt("data_centre_id"))
						.setDriverName(r.getString("jdbc_driver_name")).setDriverType(r.getString("jdbc_driver_type"))
						.setHostname(r.getString("host_address")).setPort(r.getInt("port_number"))
						.setName(r.getString("database_sid")).setUsername(r.getString("encrypted_username"))
						.setPassword(r.getString("encrypted_password"))
						.setOltp(NBC.convertToEntityAttribute(r.getInt("is_oltp")))
						.setSr(NBC.convertToEntityAttribute(r.getInt("is_sr")))
						.setDeleted(NBC.convertToEntityAttribute(r.getInt("is_deleted")))
						.setFiremem(NBC.convertToEntityAttribute(r.getInt("is_firemem_oltp"))))));

		this.cobrandInfoCache.getSrdbDescriptors().forEach(db -> {
			final Environment e = Environment.from(db.getEnvironmentId());
			this.cobrandInfoCache.getSrdbDescriptorMap().put(db.getName(), db);
			this.cobrandInfoCache.getEnvMap().get(e).add(db.getName());
			this.cobrandInfoCache.createSRDBDataSource(db);
		});
		}catch(Exception e) {
			CobrandUtil.printException(logger, e);
		}
		logger.debug("SRDB Data Source Map-" + this.cobrandInfoCache.getSrdbDatasourceMap().toString());
		return this.cobrandInfoCache.getSrdbDatasourceMap();
	}

}
