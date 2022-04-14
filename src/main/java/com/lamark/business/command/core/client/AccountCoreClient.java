package com.lamark.business.command.core.client;

import com.lamark.architecture.corems.exception.BaseException;
import com.lamark.shared.dto.AccountRequestDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.lang.reflect.InvocationTargetException;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core/account/")
public interface AccountCoreClient {

    @POST
    @Path("/save")
    Long save(AccountRequestDto accountRequest)  ;
}
