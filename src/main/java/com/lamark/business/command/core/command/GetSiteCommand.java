package com.lamark.business.command.core.command;

import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetSiteCommand implements Command {

    @Inject
    DataProcess dataProcess;

    @Override
    public void execute() {
        System.out.println("execute GetSiteCommand");
    }
}
