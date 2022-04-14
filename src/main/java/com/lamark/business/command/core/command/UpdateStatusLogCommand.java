package com.lamark.business.command.core.command;

import com.lamark.architecture.corems.exception.ExceptionHelper;
import com.lamark.business.command.core.client.DataCoreClientActualizado;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.business.command.process.factory.CancelFactory;
import com.lamark.business.command.process.factory.SuspendFactory;
import com.lamark.shared.dto.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

@Singleton
public class UpdateStatusLogCommand implements Command {

    @Inject
    @RestClient
    DataCoreClientActualizado dataCoreClientActualizado;

    @Inject
    DataProcess dataProcess;

    private final Logger logger = LoggerFactory.getLogger(UpdateStatusLogCommand.class);

    @Override
    public void execute() {
        if (!dataProcess.isExternalCancellation()) {
            executeUpdateStatus();
        }
    }

    private void executeUpdateStatus() {
        logger.info("execute command: [" + this.getClass().getName() + "]");

        try {
            UserDto user = dataProcess.getUser();

            SubscriptionDto subscriptionUpdate = new SubscriptionDto();
            subscriptionUpdate.setSite(dataProcess.getSite());
            subscriptionUpdate.setUserId(dataProcess.getUserId());
            subscriptionUpdate.setEventType(dataProcess.getEventType());
            subscriptionUpdate.setSubscriptionDuration(dataProcess.getSubscriptionDuration());
            subscriptionUpdate.setLastModifiedDate(dataProcess.getLastModifiedDate());
            logger.info("dataProcess.getEventType() : " + dataProcess.getEventType());

            if (EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.RENEW)) {

                SubscriptionDataDto requestRenewal = dataProcess.getSubscriptionData();
                SubscriptionDto ssl = user.getSubscription();
                if (
                        !SSLStateDto.CANCEL.getCode().equals(ssl.getState()) &&
                                !SSLStateDto.CANCEL_BY_LEAVE_CARRIER.getCode().equals(ssl.getState()) &&
                                !SSLStateDto.CANCEL_BY_APP.getCode().equals(ssl.getState()) &&
                                !SSLStateDto.CANCEL_BY_AUTO.getCode().equals(ssl.getState()) &&
                                !SSLStateDto.CANCEL_BY_CRM.getCode().equals(ssl.getState()) &&
                                !SSLStateDto.CANCEL_BY_BLOCK.getCode().equals(ssl.getState())
                ) {
                    subscriptionUpdate.setState(SSLStateDto.SUSPENDED.getCode());
                    subscriptionUpdate.setNextModifiedDate(dataProcess.getNextModifiedDate());
                    Long chargeRecord = dataProcess.getChargeRecord();
                    logger.info("chargeRecord : " + chargeRecord);
                    if (chargeRecord != null) {
                        if (!chargeRecord.equals(0L)) {
                            subscriptionUpdate.setState(SSLStateDto.ACTIVE.getCode());
                        }
                    }
                    if (dataProcess.getSite().equals(167)) {
                        if (dataProcess.getSource() != null && dataProcess.getSource().equals("status_change")) {
                            logger.info("entra en nueva seccion");
                            subscriptionUpdate.setState(SSLStateDto.ACTIVE.getCode());
                        }
                    }
                } else {
                    logger.error("Suscripcion cancelada no puede ser renovada - userId: {}   - msisdn :  {} , siteId:  {} ",
                            user.getUserId().toString(),
                            requestRenewal.getMsisdn(),
                            requestRenewal.getSiteId().toString());

                    throw ExceptionHelper.buildRuntimeException(
                            "Suscripcion cancelada no puede ser renovada - userId: {0}   - msisdn :  {1} , siteId:  {2} ",
                            user.getUserId().toString(),
                            requestRenewal.getMsisdn(),
                            requestRenewal.getSiteId().toString());
                }

            }


            if (EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.SUSPEND) ||
                    EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.SUSPEND_BY_RETRY) ||
                    EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.SUSPEND_BY_RETRY2)) {
                SuspendFactory.getSuspend(EventTypeDto.valueOf(dataProcess.getEventType())).process(dataProcess);
                subscriptionUpdate.setState(dataProcess.getStateSubscription());
                Optional.ofNullable(dataProcess.getSuspendDate())
                        .map((x) -> {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                subscriptionUpdate.setLastModifiedDate(format.parse(dataProcess.getSuspendDate()));
                            } catch (Exception e) {
                                logger.error("Exception : ", e);
                                throw ExceptionHelper.buildRuntimeException("Error in parsing last modified date");
                            }
                            return null;
                        });
                Optional.ofNullable(dataProcess.getStrNextModifiedDate())
                        .map((x) -> {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                subscriptionUpdate.setNextModifiedDate(format.parse(dataProcess.getStrNextModifiedDate()));
                            } catch (Exception e) {
                                logger.error("Exception : ", e);
                                throw ExceptionHelper.buildRuntimeException("Error in parsing next modified date");
                            }
                            return null;
                        });
            }

            if (EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.CANCEL) ||
                    EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.CANCEL_BY_APP) ||
                    EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.CANCEL_BY_AUTO) ||
                    EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.CANCEL_BY_BLOCK) ||
                    EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.CANCEL_BY_CRM) ||
                    EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.CANCEL_BY_LEAVE_CARRIER) ||
                    EventTypeDto.valueOf(dataProcess.getEventType()).equals(EventTypeDto.ROLLBACK)
            ) {
                CancelFactory.getCancel(EventTypeDto.valueOf(dataProcess.getEventType())).process(dataProcess);
                subscriptionUpdate.setState(dataProcess.getStateSubscription());

                LocalDateTime cancellationDate = Optional.ofNullable(dataProcess.getCancellationDate()).orElse(null);
                subscriptionUpdate.setLastModifiedDate(cancellationDate != null ? Date.from(cancellationDate.toInstant(ZoneOffset.UTC)) : null);
            }
            logger.info("before updateSubscription");
            SubscriptionDto dto = dataCoreClientActualizado.updateSubscription(subscriptionUpdate);
            logger.info("PRINT " + dto.getMsisdn());
            logger.info("After updateSubscription");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception : ", e);
        }
    }

}
