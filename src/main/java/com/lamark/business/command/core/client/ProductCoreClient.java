package com.lamark.business.command.core.client;

import com.lamark.shared.dto.ProductDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core")
public interface ProductCoreClient {

    @GET
    @Path("/products/getProductsForSite")
    List<ProductDto> getProductsForSite(@QueryParam( "siteId") Integer siteId);
}
