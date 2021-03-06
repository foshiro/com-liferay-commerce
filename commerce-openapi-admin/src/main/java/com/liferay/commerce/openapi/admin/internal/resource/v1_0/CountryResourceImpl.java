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

package com.liferay.commerce.openapi.admin.internal.resource.v1_0;

import com.liferay.commerce.openapi.admin.internal.resource.util.v1_0.CountryHelper;
import com.liferay.commerce.openapi.admin.model.v1_0.CountryDTO;
import com.liferay.commerce.openapi.admin.resource.v1_0.CountryResource;
import com.liferay.commerce.openapi.core.context.Pagination;
import com.liferay.commerce.openapi.core.model.CollectionDTO;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.kernel.model.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=CommerceOpenApiAdmin.Rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=v1.0"
	},
	scope = ServiceScope.PROTOTYPE, service = CountryResource.class
)
public class CountryResourceImpl implements CountryResource {

	@Override
	@RequiresScope("CommerceOpenApiAdmin.write")
	public Response deleteCountry(String id) throws Exception {
		_countryHelper.deleteCountry(id);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	@RequiresScope("CommerceOpenApiAdmin.read")
	public CollectionDTO<CountryDTO> getCountries(
			Long groupId, Pagination pagination)
		throws Exception {

		return _countryHelper.getCountryDTOs(groupId, pagination);
	}

	@Override
	@RequiresScope("CommerceOpenApiAdmin.read")
	public CountryDTO getCountry(String id) throws Exception {
		return _countryHelper.getCountryDTO(id);
	}

	@Override
	@RequiresScope("CommerceOpenApiAdmin.write")
	public Response updateCountry(
			Long groupId, String id, CountryDTO countryDTO)
		throws Exception {

		_countryHelper.updateCountry(groupId, id, countryDTO, _user);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	@RequiresScope("CommerceOpenApiAdmin.write")
	public CountryDTO upsertCountry(Long groupId, CountryDTO countryDTO)
		throws Exception {

		return _countryHelper.upsertCountry(groupId, countryDTO, _user);
	}

	@Reference
	private CountryHelper _countryHelper;

	@Context
	private User _user;

}