package com.lamark.business.command.process.factory;

import com.lamark.business.command.process.Suspend;
import com.lamark.business.command.process.SuspendByRetry;
import com.lamark.business.command.process.SuspendByRetry2;
import com.lamark.business.command.process.SuspendRegular;
import com.lamark.shared.dto.EventTypeDto;

import javax.inject.Singleton;

@Singleton
public class SuspendFactory {

    public static Suspend getSuspend(EventTypeDto eventType){
        switch (eventType){
            case SUSPEND: return new SuspendRegular();
            case SUSPEND_BY_RETRY: return new SuspendByRetry();
            case SUSPEND_BY_RETRY2: return new SuspendByRetry2();
            default : return null ;
        }
    }
}
