package com.lamark.business.command.core.service.impl;


import com.lamark.architecture.corems.exception.BaseException;

import com.lamark.business.command.api.CommandController;
import com.lamark.business.command.core.pattern.factory.CommandFactory;
import com.lamark.business.command.core.service.AccountService;
import com.lamark.shared.dto.CommandTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Inject
    CommandFactory commandFactory;

    @Override
    public void subscribe() throws BaseException {
        commandFactory.getCommand(CommandTypeDto.SUBSCRIBE).process();
    }

    @Override
    public void renew() throws BaseException {
        commandFactory.getCommand(CommandTypeDto.RENEW).process();
    }

    @Override
    public void cancel() throws BaseException {
        commandFactory.getCommand(CommandTypeDto.CANCEL).process();
    }

    @Override
    public void suspend() throws BaseException {
        commandFactory.getCommand(CommandTypeDto.SUSPEND).process();
    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void sendPixel() {

    }
}
