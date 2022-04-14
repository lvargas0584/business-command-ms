package com.lamark.business.command.core.pattern.processor;

import com.lamark.business.command.core.command.*;
import com.lamark.business.command.core.pattern.processor.core.AbstractCommandProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RenewProcessor extends AbstractCommandProcessor {

    @Inject
    RenewProcessor(GetUSerCommand getUSerCommand,
                   CreateAccountAssetCommand createAccountAssetCommand,
                   CreateChargeRecordCommand createChargeRecordCommand,
                   CreateAccountCreditsCommand createAccountCreditsCommand,
                   CreateUserContentCreditsCommand createUserContentCreditsCommand,
                   UpdateStatusLogCommand updateStatusLogCommand ) {
        super(  getUSerCommand,
                createAccountAssetCommand,
                createChargeRecordCommand,
                createAccountCreditsCommand,
                createUserContentCreditsCommand,
                updateStatusLogCommand );
    }


}
