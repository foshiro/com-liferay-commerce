/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.openapi.admin.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class BasicEndToEndTest extends PortalContextProvider {

	public static final String DEFAULT_SITE_NAME = "Global";

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDefaultSiteExistence() {
		StringBundler webSiteResourceURLSB = new StringBundler(3);

		webSiteResourceURLSB.append(getRootEndpointURL());
		webSiteResourceURLSB.append(_API_VERSION);
		webSiteResourceURLSB.append("webSite");

		String siteName = RestAssured.given(
		).request(
		).auth(
		).preemptive(
		).basic(
			USER, PASSWORD
		).accept(
			ContentType.JSON
		).when(
		).get(
			webSiteResourceURLSB.toString()
		).then(
		).statusCode(
			Response.Status.OK.getStatusCode()
		).extract(
		).jsonPath(
		).param(
			"globalSiteName", DEFAULT_SITE_NAME
		).get(
			"items.find { item -> item.name == globalSiteName }.name"
		);

		Assert.assertEquals(DEFAULT_SITE_NAME, siteName);
	}

	@Test
	public void testPortalURL() throws IOException {
		String portalURL = getPortalURL();

		Assert.assertNotNull(portalURL, portalURL);
	}

	private static final String _API_VERSION = "v1.0/";

}