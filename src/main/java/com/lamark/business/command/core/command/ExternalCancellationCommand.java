package com.lamark.business.command.core.command;

import com.lamark.architecture.corems.exception.ExceptionHelper;
import com.lamark.business.command.core.client.ExternalCancellationClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.ExternalCancellationDto;
import com.lamark.shared.dto.UserID;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Singleton
public class ExternalCancellationCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(ExternalCancellationCommand.class);

    @Inject
    DataProcess dataProcess;

    @Inject
    @RestClient
    ExternalCancellationClient externalCancellationClient;

    @Override
    public void execute() {

        if ((dataProcess.getSite().equals(172) || dataProcess.getSite().equals(171)) && dataProcess.isExternalCancellation()) {
            ExternalCancellationDto dto = new ExternalCancellationDto();
            dto.setAppId(dataProcess.getAppId());
            dto.setChannelId(1);
            dto.setPartnerID(dataProcess.getPartnerId());
            HashMap<String, String> subInfo = new HashMap<>();
            subInfo.put("productID", dataProcess.getExternalProductId());
            dto.setSubInfo(subInfo);
            dto.setTransID(UUID.randomUUID().toString());
            dto.setUserID(UserID.builder().ID(dataProcess.getMsisdn()).userType(0).build());
            dto.setVersion("5.0");
            logger.info("[STAR EXTERNAL CANCELLATION TIAXA] {}", dto);
            String header = "Basic " + new String(Base64.getEncoder().encode(("Lamark_2021" + ":" + "L4m4rk_2021*").getBytes()));
            HashMap<String, Integer> result = externalCancellationClient.unsubscribeProduct(header, dto);
            logger.info("[END EXTERNAL CANCELLATION TIAXA] {}", result);
            if (!result.get("resultCode").equals(0))
                throw ExceptionHelper.buildRuntimeException(
                        "Error en la cancelacion externa resulcode {0}", result.get("resultCode"));

        }

    }
}
