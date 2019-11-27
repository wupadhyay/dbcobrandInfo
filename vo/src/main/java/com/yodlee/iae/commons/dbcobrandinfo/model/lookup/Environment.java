/**
 * Copyright (c) 2016 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 */

package com.yodlee.iae.commons.dbcobrandinfo.model.lookup;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Mar 18, 2016
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Environment {
  DEV(1, "Dev"),

  QE(2, "QE"),

  STAGE(3, "Stage"),

  PERF(4, "PERF"),

  PROD(5, "Prod"),

  ALPHA(6, "Alpha");

  private final int id;

  private final String name;

  /**
   *
   */
  private Environment(final int id, final String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * @return the id
   */
  public int getId() {
    return this.id;
  }

  /**
   * @return the name
   */
  @JsonValue
  public String getName() {
    return this.name;
  }

  /**
   * @param id
   * @return
   */
  public static Environment from(final int id) {
    for (final Environment env : Environment.values()) {
      if (env.getId() == id) {
        return env;
      }
    }
    return null;
  }

  /**
   * @param id
   * @return
   */
  @JsonCreator
  public static Environment from(final String name) {
    for (final Environment env : Environment.values()) {
      if (env.getName().equalsIgnoreCase(name)) {
        return env;
      }
    }
    return null;
  }

  /**
   * @return the Map
   */
  public static Map<Integer, String> toMap() {
    final Map<Integer, String> environments = new LinkedHashMap<>();
    for (final Environment env : Environment.values()) {
      environments.put(env.getId(), env.getName());
    }
    return environments;
  }
}
