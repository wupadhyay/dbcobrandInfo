package com.yodlee.iae.commons.dbcobrandinfo.cache;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 10, 2017
 */
public class CustomBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

  public static final Set<String> TRUE_SET = new HashSet<>(Arrays.asList("y", "yes", "true", "1", "on"));

  /**
   * @param class1
   */
  public CustomBeanPropertyRowMapper(final Class<T> class1) {
    super(class1);
  }

  /*
   * (non-Javadoc)
   * @see
   * org.springframework.jdbc.core.BeanPropertyRowMapper#getColumnValue(java.sql
   * .ResultSet, int, java.beans.PropertyDescriptor)
   */
  @Override
  protected Object getColumnValue(final ResultSet rs, final int index, final PropertyDescriptor pd)
      throws SQLException {
    final Class<?> requiredType = pd.getPropertyType();

    if (boolean.class.equals(requiredType) || Boolean.class.equals(requiredType)) {
      final String value = rs.getString(index);
      if (StringUtils.isNotEmpty(value) && TRUE_SET.contains(value.toLowerCase())) {
        return true;
      } else {
        return false;
      }
    }

    return super.getColumnValue(rs, index, pd);
  }
}
