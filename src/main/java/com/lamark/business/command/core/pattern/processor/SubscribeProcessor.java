package com.lamark.business.command.core.pattern.processor;

import com.lamark.business.command.core.command.*;
import com.lamark.business.command.core.pattern.processor.core.AbstractCommandProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SubscribeProcessor extends AbstractCommandProcessor {


    @Inject
    SubscribeProcessor(CreateAccountCommand createAccountCommand,
                       GetProductBySiteCommand getProductBySiteCommand,
                       CreateVoucherCommand createVoucherCommand,
                       CreateUserCommand createUserCommand,
                       CreateUserRolCommand createUserRolCommand,
                       CreateAccountAssetCommand createAccountAssetCommand,
                       CreateChargeRecordCommand createChargeRecordCommand,
                       CreateAccountCreditsCommand createAccountCreditsCommand,
                       CreateUserContentCreditsCommand createUserContentCreditsCommand,
                       CreateSubscriptionStateLogCommand createSubscriptionStateLogCommand
    ) {

            super(createAccountCommand,
                    getProductBySiteCommand,
                    createVoucherCommand,
                    createUserCommand,
                    createUserRolCommand,
                    createAccountAssetCommand,
                    createChargeRecordCommand,
                    createAccountCreditsCommand,
                    createUserContentCreditsCommand,
                    createSubscriptionStateLogCommand);
    }
}
