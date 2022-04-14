package com.lamark.business.command.core.pattern.processor.core;

import com.lamark.business.command.core.pattern.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommandProcessor implements Processor {

    public List<Command> commands = new ArrayList<>();

    public AbstractCommandProcessor(Command... command) {
        commands.addAll(Arrays.asList(command));
    }

    @Override
    public void process() {
        commands.forEach(Command::execute);
    }
}
