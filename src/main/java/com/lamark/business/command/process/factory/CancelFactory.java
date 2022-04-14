package com.lamark.business.command.process.factory;

import javax.inject.Singleton;

import com.lamark.business.command.process.*;
import com.lamark.shared.dto.*;
@Singleton
public class CancelFactory {

    public static Cancelation getCancel(EventTypeDto eventType){
        switch (eventType){
            case CANCEL_BY_APP : return  new CancelByApp();
            case CANCEL : return  new CancelRegular();
            case CANCEL_BY_AUTO: return  new CancelByAuto();
            case CANCEL_BY_CRM: return  new CancelByCrm();
            case ROLLBACK: return new CancelByRollback();
            default : return null ;
        }
    }
}
