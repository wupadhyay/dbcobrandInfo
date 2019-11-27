/*
 * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 */
package com.yodlee.iae.commons.dbcobrandinfo.dbfetcher.bean;

/**
 * @author wupadhyay
 *
 */

public class QueryConstant {

	public static final String COBRAND_SPECIFIC_SUM_INFO_SITE_QUERY = "SELECT distinct c.cobrand_id, "
			+ "  c.name AS cobrandName, " + "  si.sum_info_id, " + "  sidc.is_ready AS status, " + "  si.site_id, "
			+ "  si.class_name AS className, " + "  t.tag, " + "  t.tag_id            AS tagId, "
			+ "  s.primary_locale_id AS sitePrimaryLocale " + "FROM cobrand c, " + "  sum_info si, "
			+ "  sum_info_def_cat sidc, " + "  tag t, " + "  site s, " + "  def_cat dc, " + "  def_tab dt "
			+ "WHERE c.cobrand_status_id = 1 " + "AND si.sum_info_id = sidc.sum_info_id "
			+ "AND si.tag_id = t.tag_id " + "AND s.site_id = si.site_id "
			+ "AND si.IS_READY =1 " + "AND s.IS_READY = 1 " + "AND sidc.is_ready =1 "
			+ "AND sidc.def_cat_id = dc.def_cat_id " + "AND dc.def_tab_id = dt.def_tab_id "
			+ "AND dt.cobrand_id  = c.cobrand_id " + "AND dc.IS_READY = 1 "
			+ "AND dt.IS_READY  =1";

	public static final String SITE_SUPPORTED_LOCALE_QUERY = "SELECT site_id as siteId, " + "  c.locale_id , "
			+ "  language_iso_code as language, " + "  country_name as country " + "FROM " + "  (SELECT sp.locale_id, "
			+ "    sl.language_id, " + "    sl.language_name, " + "    sl.language_iso_code, " + "    sc.country_id, "
			+ "    sc.country_name " + "  FROM locale sp " + "  INNER JOIN language sl "
			+ "  ON sp.language_id=sl.language_id " + "  LEFT OUTER JOIN country sc "
			+ "  ON sp.country_id = sc.country_id " + "  ) c, " + "  site_sptd_locale d "
			+ "WHERE c.locale_id = d.locale_id";

	public static final String SITE_SPECIFIC_DETAILS_QUERY = "SELECT site_id, "
			+ "  listagg(cobrand_id,',') within GROUP ( " + "ORDER BY cobrand_id) AS cobrands " + "FROM "
			+ "  ( SELECT DISTINCT dt.cobrand_id , " + "    si.site_id " + "  FROM def_tab dt, " + "    sum_info si "
			+ "  WHERE def_tab_id IN " + "    (SELECT def_tab_id " + "    FROM def_cat " + "    WHERE def_cat_id IN "
			+ "      (SELECT def_cat_id " + "      FROM sum_info_def_cat " + "      WHERE is_ready = 1 "
			+ "      AND sum_info_def_cat.sum_info_id = si.sum_info_id " + "      ) and is_ready=1 "
			+ "    ) and si.is_ready =1 and dt.is_ready=1 " + "  ) " + "GROUP BY site_id ";

	public static final String COBRAND_PRODUCT_CATALOG_QUERY = "SELECT cobrand_id as cobrandId, product_catalog_id as productCatalogId FROM cobrand_product";

	public static final String COBRAND_DISABLED_CONTAINER_QUERY = "SELECT cobrand_id," + "  NVL (disabled_containers,"
			+ "  (SELECT default_value" + "  FROM param_key "
			+ "  WHERE param_key_name = 'com.yodlee.core.containers.disabled'" + "  )) AS containers " + " FROM "
			+ "  (SELECT c.cobrand_id, " + " cp.param_value AS disabled_containers " + "  FROM cobrand c "
			+ "  LEFT OUTER JOIN cob_param cp " + "  ON (cp.cobrand_id   = c.cobrand_id "
			+ "  AND cp.param_key_id = 5955) " + ")";

	public static final String TAG_QUERY = "SELECT tag_id, tag FROM Tag where is_deprecated = 0";

	public static final String COBRAND_SUPPORTED_CONTAINER_QUERY = "SELECT p.product_catalog_id as productCatalogId,"
			+ "  ptm.tag as tag" + " FROM product p," + "  product_tag_map ptm "
			+ " WHERE p.product_id = ptm.product_id " + " AND p.product_catalog_id IS NOT NULL";

	public static final String SITE_METADATA_QUERY = "SELECT MC_KEY as MCKey,value as siteName FROM db_message_catalog where MC_KEY like ('%display_name.site.%')";

	public static final String YODLEE_COBRAND_QUERY = "select cobrand_id as cobrandId, name as cobrandName from cobrand where cobrand_id=10000004";
}
