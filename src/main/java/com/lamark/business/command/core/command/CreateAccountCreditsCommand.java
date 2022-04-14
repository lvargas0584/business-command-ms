package com.lamark.business.command.core.command;



import com.lamark.business.command.core.client.AccountCreditClient;
import com.lamark.business.command.core.client.ContentPackageClient;
import com.lamark.business.command.core.client.UserCoreClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.AccountCreditDto;
import com.lamark.shared.dto.ContentPackageDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CreateAccountCreditsCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(CreateAccountCreditsCommand.class);

    @Inject
    @RestClient
    UserCoreClient userCoreClient;

    @Inject
    DataProcess dataProcess;

    @Inject
    @RestClient
    ContentPackageClient contentPackageClient;

    @Inject
    @RestClient
    AccountCreditClient accountCreditClient;


    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");

        Optional.ofNullable(dataProcess.isMakeBilling())
                .filter(d -> d)
                .map(s -> createAccountCredit())
                .orElseGet(() -> {
                    return null;
                });
    }

    public AccountCreditDto createAccountCredit() {

        ContentPackageDto contentPackage = contentPackageClient.findById(dataProcess.getContentPackage());
        AccountCreditDto accountCredit = new AccountCreditDto();
        accountCredit.setAccountAssetId(dataProcess.getAccountAsset());
        accountCredit.setAccountId(dataProcess.getAccountId());
        accountCredit.setUserId(dataProcess.getUserId());
        accountCredit.setAvailableCredits(contentPackage.getTotalCredits());
        accountCredit.setInitialCredits(contentPackage.getTotalCredits());
        accountCredit.setSubscriptionDuration(dataProcess.getSubscriptionDuration());
        accountCredit.setSite(dataProcess.getSite());
        accountCredit.setCreateDate(dataProcess.getAccountCreditsDate());
        accountCredit.setExpireDate(dataProcess.getExpireDate());
        accountCreditClient.save(accountCredit);

        return accountCredit;
    }
}
