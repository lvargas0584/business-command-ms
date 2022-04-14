package com.lamark.business.command.core.command;

import com.lamark.architecture.corems.exception.BaseException;

import com.lamark.business.command.core.client.DataCoreClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.SubscriptionResourceDto;
import com.lamark.shared.dto.UserDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetUSerCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(GetUSerCommand.class);

    @Inject
    DataProcess dataProcess;

    @Inject
    @RestClient
    DataCoreClient dataCoreClient;

    @Override
    public void execute() {

        logger.info("execute command: [" + this.getClass().getName() + "]");

        try {
            UserDto user = dataCoreClient.getUser(dataProcess.getMsisdn(), dataProcess.getSite());
            dataProcess.setUserId(user.getUserId());
            dataProcess.setUser(user);
            dataProcess.setAccountId(user.getAccount().getAccountId());
            dataProcess.setKeyword(user.getKeyword());
            SubscriptionResourceDto subscriptionResourceDto = new SubscriptionResourceDto();
            subscriptionResourceDto.setUserId(user.getUserId());
            dataProcess.setSubscriptionResource(subscriptionResourceDto);


        } catch (BaseException e) {
            e.printStackTrace();
            //throw new BaseException(new BaseExceptionDto(5,""));
        }
    }
}
