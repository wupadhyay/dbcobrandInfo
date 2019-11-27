/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.datasource;

import com.yodlee.iae.commons.dbcobrandinfo.model.lookup.Environment;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since May 10, 2016
 */
public class DBContext {

  private static ThreadLocal<ContextInfo> context = InheritableThreadLocal.withInitial(ContextInfo::new);

  /**
   * @return
   */
  public static ContextInfo get() {
    return context.get();
  }

  /**
   * @param context
   *          the context to set
   */
  public static void set(ContextInfo contextInfo) {
    context.set(contextInfo);
  }

  /**
   * @param dbName
   */
  public static void setDBName(final String dbName) {
    DBContext.get().setDbName(dbName);
  }

  /**
   * @param cobrandId
   */
  public static void setCobrandId(Environment environment, Long cobrandId) {
    DBContext.get().setEnvironment(environment).setCobrandId(cobrandId);
  }
}
