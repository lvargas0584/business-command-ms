package com.lamark.business.command.core.client;

import com.lamark.shared.dto.UserDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core/user")
public interface UserCoreClient {

    @POST
    Long saveUser(UserDto userDto);

    @PUT
    Long updateUser(UserDto userDto);

    @POST
    @Path("/role")
    Response saveUserRole(UserDto userDto);
}
