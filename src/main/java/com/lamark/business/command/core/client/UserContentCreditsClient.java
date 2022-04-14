package com.lamark.business.command.core.client;

import com.lamark.shared.dto.UserContentCreditsDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core/userContentCredits/")
public interface UserContentCreditsClient {

    @POST
    @Path("/save")
    public Long save(UserContentCreditsDto userContentCredits);
}
