package com.lamark.business.command.core.client;

import com.lamark.shared.dto.ChargeRecordDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "data-core")
@Path("/data/v1/core/chargeRecord/")
public interface ChargeRecordClient {

    @POST
    @Path("/save")
    public Long save(ChargeRecordDto chargeRecord);
}
