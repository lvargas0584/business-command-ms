package com.lamark.business.command.core.command;



import com.lamark.business.command.core.client.DataCoreClient;
import com.lamark.business.command.core.client.UserCoreClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.AccountDto;
import com.lamark.shared.dto.UserDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreateUserCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(CreateUserCommand.class);

    @Inject
    @RestClient
    UserCoreClient userCoreClient;

    @Inject
    @RestClient
    DataCoreClient dataCoreClient;

    @Inject
    DataProcess dataProcess;

    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");
        UserDto user = null;
        try { //AJGA - Añadido para manejar las resuscripciones
            user = dataCoreClient.getUser(dataProcess.getMsisdn(), dataProcess.getSite());
        } catch (Exception e) {
            //logger.info("Exception User : " , e);
        }
        Long userId = null;
        if (user != null) { //AJGA - Añadido para manejar las resuscripciones
            user.setKeyword(dataProcess.getKeyword());
            user.setChannel(dataProcess.getChannel());
            userCoreClient.updateUser(user);
            userId = user.getUserId();
            dataProcess.setUserId(userId);
            dataProcess.setNewUser(false);
        } else {
            user = new UserDto();
            AccountDto account = new AccountDto();
            account.setAccountId(dataProcess.getAccountId());
            if (dataProcess.getSite().equals(167))
                user.setProviderUniqueId("92" + dataProcess.getMsisdn());
            else
                user.setProviderUniqueId(dataProcess.getMsisdn());
            user.setMsisdn(dataProcess.getMsisdn());
            user.setAccount(account);
            user.setSiteId(dataProcess.getSite());
            user.setLastSync(dataProcess.getSubscriptionDate());
            user.setChannel(dataProcess.getChannel());
            user.setKeyword(dataProcess.getKeyword());
            user.setProductId(dataProcess.getProductId());
            user.setUserName(dataProcess.getMsisdn());
            user.setProductId(dataProcess.getProductId());
            user.setKeywordDescription(dataProcess.getAdProvider());
            if (dataProcess.getSite().equals(150))
                user.setKeywordDescription("APIGATE_WEB");
            if (dataProcess.getSite().equals(169))
                user.setComment(dataProcess.getCountryLetter());
            userId = userCoreClient.saveUser(user);
            dataProcess.setUserId(userId);
            dataProcess.setNewUser(true);

        }
    }
}
