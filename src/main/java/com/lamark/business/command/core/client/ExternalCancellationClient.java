package com.lamark.business.command.core.client;

import com.lamark.shared.dto.ExternalCancellationDto;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@RegisterRestClient(configKey = "tiaxa-unSubscribe")
public interface ExternalCancellationClient {

    @POST
    @Path("/unSubscribeProduct")
    @Produces(MediaType.APPLICATION_JSON)
    HashMap<String, Integer> unsubscribeProduct(@HeaderParam("Authorization") String barer, ExternalCancellationDto externalCancellationDto);


}
