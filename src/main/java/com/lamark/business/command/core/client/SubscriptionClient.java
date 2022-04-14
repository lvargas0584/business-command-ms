package com.lamark.business.command.core.client;

import com.lamark.shared.dto.SubscriptionDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core/subscription")
public interface SubscriptionClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response save(SubscriptionDto dto );

    @PUT
    void update(SubscriptionDto dto);
}
