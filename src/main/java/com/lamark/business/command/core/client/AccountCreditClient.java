package com.lamark.business.command.core.client;

import com.lamark.shared.dto.AccountCreditDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core/accountCredit/")
public interface AccountCreditClient {

    @POST
    @Path("/save")
    public Long save(AccountCreditDto accountCredit);
}
