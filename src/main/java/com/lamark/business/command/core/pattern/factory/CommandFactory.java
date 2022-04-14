package com.lamark.business.command.core.pattern.factory;

import com.lamark.business.command.core.pattern.processor.CancelProcessor;
import com.lamark.business.command.core.pattern.processor.RenewProcessor;
import com.lamark.business.command.core.pattern.processor.SubscribeProcessor;
import com.lamark.business.command.core.pattern.processor.SuspendProcessor;
import com.lamark.business.command.core.pattern.processor.core.Processor;
import com.lamark.shared.dto.CommandTypeDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandFactory {
    @Inject
    CancelProcessor cancelExecutor;

    @Inject
    SubscribeProcessor subscribeExecutor;

    @Inject
    RenewProcessor renewProcessor;

    @Inject
    SuspendProcessor suspendExecutor;

    public Processor getCommand(CommandTypeDto commandType) {
        switch (commandType) {
            case SUBSCRIBE:
                return subscribeExecutor;
            case RENEW:
                return renewProcessor;
            case CANCEL:
                return cancelExecutor;
            case SUSPEND:
                return suspendExecutor;
            default:
                return null;
        }
    }
}
