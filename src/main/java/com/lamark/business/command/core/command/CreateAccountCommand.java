package com.lamark.business.command.core.command;


import com.lamark.business.command.core.client.AccountCoreClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.AccountRequestDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreateAccountCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(CreateAccountCommand.class);
    Integer DEFAULT_STATUS_ID = 1;

    @Inject
    @RestClient
    AccountCoreClient accountCoreClient;

    @Inject
    DataProcess dataProcess;

    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");
        AccountRequestDto accountRequest = new AccountRequestDto();
        accountRequest.setMsisdn(dataProcess.getMsisdn());
        accountRequest.setSite(dataProcess.getSite());
        accountRequest.setStatusId(DEFAULT_STATUS_ID);
        Long accountId = accountCoreClient.save(accountRequest);

        dataProcess.setAccountId(accountId);
    }
}
