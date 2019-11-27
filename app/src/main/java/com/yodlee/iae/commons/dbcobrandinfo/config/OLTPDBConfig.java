/**
 *
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.yodlee.iae.commons.dbcobrandinfo.cache.DBInfoCache;
import com.yodlee.iae.commons.dbcobrandinfo.entities.adt.DBDescriptor;
import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 11, 2016
 */
@Configuration
public class OLTPDBConfig {

  private static Logger logger = LoggerFactory.getLogger(OLTPDBConfig.class);

  @Autowired
  private DBInfoCache dbInfoCache;

  @Value("${tools.dbcobrandinfo.default.db.name}")
  private String defaultDB;

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
  public Map<String, DataSource> createOLTPDatasources(@Qualifier("adtDataSource") final DataSource dataSource) {
    final List<Integer> environmentIds = this.dbInfoCache.getEnvironmentIds();
    logger.info("configured environment ids: {}", environmentIds);

    environmentIds.forEach(id -> {
      final Environment e = Environment.from(id);
      this.dbInfoCache.getEnvMap().put(e, new LinkedHashSet<>());
    });

    final String sql = "select * from deploy_db_descriptor where (is_oltp = 1 or is_firemem_oltp = 1) and is_sr = 0 and is_deleted = 0 and fdt_environment_id in ("
        + environmentIds.toString().replaceAll("\\[|\\]", "") + ")";
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    // OLTP's
    this.dbInfoCache.getDbDescriptors().addAll((jdbcTemplate.query(sql,
        (RowMapper<DBDescriptor>) (r, n) -> DBDescriptor.build().setDescriptorId(r.getLong("deploy_db_descriptor_id"))
            .setEnvironmentId(r.getInt("fdt_environment_id")).setDataCenterId(r.getInt("data_centre_id"))
            .setDriverName(r.getString("jdbc_driver_name")).setDriverType(r.getString("jdbc_driver_type"))
            .setHostname(r.getString("host_address")).setPort(r.getInt("port_number"))
            .setName(r.getString("database_sid")).setUsername(r.getString("encrypted_username"))
            .setPassword(r.getString("encrypted_password")).setOltp(NBC.convertToEntityAttribute(r.getInt("is_oltp")))
            .setSr(NBC.convertToEntityAttribute(r.getInt("is_sr")))
            .setDeleted(NBC.convertToEntityAttribute(r.getInt("is_deleted")))
            .setFiremem(NBC.convertToEntityAttribute(r.getInt("is_firemem_oltp"))))));

    this.dbInfoCache.getDbDescriptors().forEach(db -> {
      final Environment e = Environment.from(db.getEnvironmentId());
      this.dbInfoCache.getDbDescriptorMap().put(db.getName(), db);
      this.dbInfoCache.getEnvMap().get(e).add(db.getName());
      this.dbInfoCache.createDataSource(db);
    });

    this.dbInfoCache.post();

    return this.dbInfoCache.getDatasourceMap();
  }
}
