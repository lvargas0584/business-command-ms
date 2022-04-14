package com.lamark.business.command.process;

import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.SSLStateDto;

public class CancelByAuto extends Cancelation{
    public void process(DataProcess dataProcess)   {
        dataProcess.setStateSubscription(SSLStateDto.CANCEL_BY_AUTO.getCode());
    }
}
