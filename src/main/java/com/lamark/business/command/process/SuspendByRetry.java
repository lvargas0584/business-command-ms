package com.lamark.business.command.process;

import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.SSLStateDto;

public class SuspendByRetry extends Suspend {

    public void process(DataProcess dataProcess)   {
        dataProcess.setStateSubscription(SSLStateDto.SUSPENDED_BY_RETRY.getCode());
    }
}
