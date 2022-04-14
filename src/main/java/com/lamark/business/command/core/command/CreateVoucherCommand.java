package com.lamark.business.command.core.command;

import com.lamark.architecture.corems.exception.ExceptionHelper;

import com.lamark.business.command.core.client.DataCoreClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.IntegrationDto;
import com.lamark.shared.dto.SiteDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CreateVoucherCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(CreateVoucherCommand.class);

    @Inject
    @RestClient
    DataCoreClient dataCoreClient;

    @Inject
    DataProcess dataProcess;

    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");
        logger.info("dataProcess.isGenerateVoucherCode() " + dataProcess.isGenerateVoucherCode());
        Optional.ofNullable(dataProcess.isGenerateVoucherCode())
                .filter(d -> d)
                .map(s -> generateVoucherCode())
                .orElseGet(() -> {
                    return null;
                });
    }

    private String generateVoucherCode() {
        logger.info("INit generateVoucherCode");
        Integer siteID = dataProcess.getSite();
        SiteDto siteDto = dataCoreClient.getSiteById(siteID);
        if (siteID.equals(149)) {
            String countryCode = siteDto.getInternationalPrefix();
            String lengthVoucherCode = dataCoreClient.getConfig("LM_CONF_VOUCHER_CODE")
                    .stream()
                    .filter((x) -> x.getValue1().equals(siteID.toString()))
                    .filter((x) -> x.getValue2().equals("length"))
                    .findFirst().map((x) -> x.getValue3())
                    .orElseThrow(() -> ExceptionHelper.buildRuntimeException("No existe configuracion de voucher code LM_CONF_VOUCHER_CODE para el site " + siteID));
            logger.info("lengthVoucherCode  " + lengthVoucherCode);
            String voucherCode = countryCode.concat(RandomStringUtils.randomNumeric(Integer.valueOf(lengthVoucherCode)));
            logger.info("voucherCOde  " + voucherCode);
            IntegrationDto integrationDto = new IntegrationDto();
            integrationDto.setMsisdn(voucherCode);
            integrationDto.setKeyName("X-Source-Addr");
            integrationDto.setKeyValue(dataProcess.getMsisdn());
            integrationDto.setSiteId(dataProcess.getSite());
            dataProcess.setMsisdn(voucherCode);
            dataCoreClient.saveInCorrelationTable(integrationDto);
        }
        return "";
    }
}
