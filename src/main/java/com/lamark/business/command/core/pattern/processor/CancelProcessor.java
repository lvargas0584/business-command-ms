package com.lamark.business.command.core.pattern.processor;

import com.lamark.business.command.core.command.ExternalCancellationCommand;
import com.lamark.business.command.core.command.UpdateStatusLogCommand;
import com.lamark.business.command.core.command.GetUSerCommand;
import com.lamark.business.command.core.pattern.processor.core.AbstractCommandProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CancelProcessor extends AbstractCommandProcessor {

    @Inject
    CancelProcessor(GetUSerCommand getUSerCommand, ExternalCancellationCommand externalCancellationCommand, UpdateStatusLogCommand updateStatusLogCommand) {
        super(getUSerCommand, externalCancellationCommand, updateStatusLogCommand);
    }
}
