package com.lamark.business.command.core.client;

import com.lamark.architecture.corems.exception.BaseException;
import com.lamark.shared.dto.IntegrationDto;
import com.lamark.shared.dto.LamarkConfigDto;
import com.lamark.shared.dto.SiteDto;
import com.lamark.shared.dto.UserDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core")
public interface DataCoreClient {

    @GET
    @Path("/user/{msisdn}/{siteId}")
    @Produces(MediaType.APPLICATION_JSON)
    UserDto getUser(@PathParam("msisdn") String msisdn, @PathParam("siteId") Integer siteId) throws BaseException;

    @GET
    @Path("/config/{parentName}")
    @Produces("application/json")
    List<LamarkConfigDto> getConfig(@PathParam("parentName") String parentName);


    @POST
    @Path("/integrationMsisdn/save")
    public void  saveInCorrelationTable(IntegrationDto integrationDto)   ;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/site/getSite/{siteId}")
    public SiteDto getSiteById(@PathParam("siteId") Integer siteId);

}
