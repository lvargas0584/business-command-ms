package com.lamark.business.command.core.service;

import com.lamark.architecture.corems.exception.BaseException;

public interface AccountService {
    public  void subscribe() throws BaseException;

    public  void cancel() throws BaseException;

    public  void suspend() throws BaseException;

    public  void renew() throws BaseException;

    public  void sendMessage();

    public  void sendPixel();
}
