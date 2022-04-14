package com.lamark.business.command.core.pattern.processor;

import com.lamark.business.command.core.command.GetUSerCommand;
import com.lamark.business.command.core.command.UpdateStatusLogCommand;
import com.lamark.business.command.core.pattern.processor.core.AbstractCommandProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SuspendProcessor extends AbstractCommandProcessor {

    @Inject
    SuspendProcessor(GetUSerCommand getUSerCommand, UpdateStatusLogCommand UpdateStatusLogCommand) {
        super(getUSerCommand,UpdateStatusLogCommand);
    }
}
