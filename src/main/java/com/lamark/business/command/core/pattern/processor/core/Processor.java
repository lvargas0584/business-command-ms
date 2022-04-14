package com.lamark.business.command.core.pattern.processor.core;

import com.lamark.architecture.corems.exception.BaseException;

public interface Processor {
    void process() throws BaseException;
}
