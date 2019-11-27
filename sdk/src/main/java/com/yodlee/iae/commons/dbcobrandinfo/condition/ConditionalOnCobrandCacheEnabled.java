/**
 *
 */
package com.yodlee.iae.commons.dbcobrandinfo.condition;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since 25-May-2016
 */
@Retention(RUNTIME)
@Target({ TYPE })
@ConditionalOnProperty(value = "tools.dbcobrandinfo.cobrandcache.enabled", matchIfMissing = true)
public @interface ConditionalOnCobrandCacheEnabled {
}
