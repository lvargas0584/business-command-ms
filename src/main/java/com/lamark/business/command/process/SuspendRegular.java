package com.lamark.business.command.process;

import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.SSLStateDto;

public class SuspendRegular extends Suspend {

    public void process(DataProcess dataProcess)   {
        dataProcess.setStateSubscription(SSLStateDto.SUSPENDED.getCode());
    }
}
