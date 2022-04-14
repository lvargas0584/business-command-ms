package com.lamark.business.command.core.pattern.command;

import com.lamark.architecture.corems.exception.BaseException;

@FunctionalInterface
public interface Command {

     public abstract void execute()  ;
}
