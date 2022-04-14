package com.lamark.business.command.core.client;

import com.lamark.shared.dto.ContentPackageDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core/contentPackage")
public interface ContentPackageClient {

    @GET
    @Path("/findById")
    public ContentPackageDto findById(@QueryParam("id") Integer id);
}
