package com.lamark.business.command.core.command;



import com.lamark.architecture.corems.util.MathUtil;
import com.lamark.business.command.core.client.ChargeRecordClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.ChargeRecordDto;
import com.lamark.shared.dto.ChargeStatusDto;
import com.lamark.shared.dto.CollectedTypeDto;
import com.lamark.shared.dto.PaymentSystemDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CreateChargeRecordCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(CreateChargeRecordCommand.class);

    @Inject
    DataProcess dataProcess;

    @Inject
    @RestClient
    ChargeRecordClient chargeRecordClient;


    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");

        Optional.ofNullable(dataProcess.isMakeBilling())
                .filter(d -> d)
                .map(s -> createChargeRecord())
                .orElseGet(() -> {
                    return null;
                });
    }


    public ChargeRecordDto createChargeRecord() {
        ChargeRecordDto chargeRecord = new ChargeRecordDto();
        chargeRecord.setUserId(dataProcess.getUserId());
        chargeRecord.setPriceRecordId(Integer.parseInt(dataProcess.getPriceCode()));
        chargeRecord.setAccountAsset(dataProcess.getAccountAsset());
        chargeRecord.setKeyword(dataProcess.getKeyword());
        if (dataProcess.isAmountExact())
            chargeRecord.setAmount(new Double(dataProcess.getAmount()));
        else
            chargeRecord.setAmount(MathUtil.getAmount(dataProcess.getAmount()));
        chargeRecord.setChargeStatus(ChargeStatusDto.PAID);
        chargeRecord.setPaymentSystem(PaymentSystemDto.PREPAID);
        chargeRecord.setCollectedType(CollectedTypeDto.YES);
        chargeRecord.setSite(dataProcess.getSite());
        chargeRecord.setBillingDate(dataProcess.getBillingDate());
        Long chargeRecordId = chargeRecordClient.save(chargeRecord);
        dataProcess.setChargeRecord(chargeRecordId);


        if (dataProcess.getSubscriptionResource() != null) {
            dataProcess.getSubscriptionResource().setChargeRecord(chargeRecordId);
        }

        return chargeRecord;
    }
}
