package com.lamark.business.command.core.command;


import com.lamark.architecture.corems.util.MathUtil;
import com.lamark.business.command.core.client.AccountAssetClient;
import com.lamark.business.command.core.client.UserCoreClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.AccountAssetDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Optional;

@Singleton
public class CreateAccountAssetCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(CreateAccountAssetCommand.class);
    @Inject
    @RestClient
    UserCoreClient userCoreClient;

    @Inject
    DataProcess dataProcess;

    @Inject
    @RestClient
    AccountAssetClient accountAssetClient;

    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");

        Optional.ofNullable(dataProcess.isMakeBilling())
                .filter(d -> d)
                .map(s -> createAccountAsset())
                .orElseGet(() -> {
                    return null;
                });


    }

    public Long createAccountAsset() {
        AccountAssetDto accountAsset = new AccountAssetDto();
        accountAsset.setAccountId(dataProcess.getAccountId());
        accountAsset.setUserId(dataProcess.getUserId());
        accountAsset.setContentPackageId(dataProcess.getContentPackage());
        accountAsset.setRemainingPayments(BigDecimal.ZERO.intValue());
        logger.info("dataProcess.getSite() " + dataProcess.getSite());
        logger.info("dataProcess.isAmountExact() " + dataProcess.isAmountExact());
        if (dataProcess.isAmountExact())
            accountAsset.setPrice(new Double(dataProcess.getAmount()));
        else
            accountAsset.setPrice(MathUtil.getAmount(dataProcess.getAmount()));

        accountAsset.setSubscriptionDuration(dataProcess.getSubscriptionDuration());
        accountAsset.setSite(dataProcess.getSite());
        accountAsset.setPurchaseDate(dataProcess.getPurchaseDate());
        accountAsset.setExpireDate(dataProcess.getExpireDate());
        Long accountAssetId = accountAssetClient.save(accountAsset);
        dataProcess.setAccountAsset(accountAssetId);

        return accountAssetId;

    }

}
