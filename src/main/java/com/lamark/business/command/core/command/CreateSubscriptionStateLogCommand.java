package com.lamark.business.command.core.command;



import com.lamark.business.command.core.client.SubscriptionClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.SSLStateDto;
import com.lamark.shared.dto.SubscriptionDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CreateSubscriptionStateLogCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(CreateSubscriptionStateLogCommand.class);

    @Singleton
    @RestClient
    SubscriptionClient subscriptionClient;

    @Inject
    DataProcess dataProcess;

    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");
        SSLStateDto sslState = null;
        logger.info("dataProcess.isTrial() " + dataProcess.isTrial());
        if (dataProcess.isTrial()) {
            sslState = SSLStateDto.ACTIVE;
        } else {
            sslState = Optional.ofNullable(dataProcess.getChargeRecord() != null)
                    .filter(p -> p)
                    .map(s -> SSLStateDto.ACTIVE)
                    .orElse(SSLStateDto.SUSPENDED);
        }
        dataProcess.setSslState(sslState);

        SubscriptionDto subscriptionStateLog = new SubscriptionDto();
        subscriptionStateLog.setUserId(dataProcess.getUserId());
        subscriptionStateLog.setContentPackage(dataProcess.getContentPackage());
        subscriptionStateLog.setLastModifiedDate(dataProcess.getLastModifiedDate());
        subscriptionStateLog.setInitialDate(dataProcess.getSubscriptionDate());
        subscriptionStateLog.setSubscriptionDate(dataProcess.getSubscriptionDate());
        subscriptionStateLog.setNextModifiedDate(dataProcess.getNextModifiedDate());
        subscriptionStateLog.setState(dataProcess.getSslState().getCode());
        subscriptionStateLog.setSubscriptionDuration(dataProcess.getSubscriptionDuration());
        subscriptionStateLog.setSite(dataProcess.getSite());
        logger.info("subscriptionStateLog: "+subscriptionStateLog.getLastModifiedDate());
        subscriptionClient.save(subscriptionStateLog);
    }
}
