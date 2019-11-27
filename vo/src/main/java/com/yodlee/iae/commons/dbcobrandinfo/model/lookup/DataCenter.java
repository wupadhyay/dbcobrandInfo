package com.yodlee.iae.commons.dbcobrandinfo.model.lookup;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Sundhara Raja Perumal .S (sperumal@yodlee.com)
 * @since Date: Jan 10, 2017
 */
public enum DataCenter {

  SC9(1, "SC9"),

  RHB(2, "Malaysia"),

  AUS(3, "Australia"),

  CAN(4, "Canada"),

  UK(5, "United Kingdom"),

  IDC(6, "India")

  ;

  private int id;

  private String name;

  /**
   * @param id
   * @param name
   */
  private DataCenter(final int id, final String name) {
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
  public String getName() {
    return this.name;
  }

  /**
   * @param id
   * @return
   */
  public static DataCenter from(final int id) {
    for (final DataCenter dc : DataCenter.values()) {
      if (dc.getId() == id) {
        return dc;
      }
    }
    return null;
  }

  /**
   * @param id
   * @return
   */
  @JsonCreator
  public static DataCenter fromName(final String name) {
    for (final DataCenter dc : DataCenter.values()) {
      if (dc.getName().equalsIgnoreCase(name)) {
        return dc;
      }
    }
    return null;
  }

  public static Map<Integer, String> toMap() {
    final Map<Integer, String> dataCenter = new HashMap<>();
    for (final DataCenter dc : DataCenter.values()) {
      dataCenter.put(dc.getId(), dc.getName());
    }
    return dataCenter;
  }
}
