package com.lamark.business.command.api;

import com.lamark.architecture.corems.api.BaseController;
import com.lamark.architecture.corems.exception.BaseException;

import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.business.command.core.service.AccountService;
import com.lamark.shared.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/business/v1/command")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommandController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(CommandController.class);

    @Inject
    AccountService accountService;

    @Inject
    DataProcess dataProcess;

    @PUT
    @Path("/subscribe")
    public Response subscribe(SubscriptionDto subscriptionDto) throws BaseException {

        List<Integer> integers = new ArrayList<>();
        this.logger.info("[PUT][START] /subscribe/{} ", subscriptionDto);
        this.logger.info("[PUT][START] /subscribe/{}  ", subscriptionDto.getLastModifiedDate());

        dataProcess.setSite(subscriptionDto.getSite());
        dataProcess.setMsisdn(subscriptionDto.getMsisdn());
        dataProcess.setKeyword(subscriptionDto.getKeyword());
        dataProcess.setContentPackage(subscriptionDto.getContentPackage());
        dataProcess.setPriceCode(subscriptionDto.getPriceCode());
        dataProcess.setAmount(subscriptionDto.getAmount());
        dataProcess.setChannel(subscriptionDto.getChannel());
        dataProcess.setMakeBilling(subscriptionDto.isMakeBilling());
        dataProcess.setSubscriptionDuration(subscriptionDto.getSubscriptionDuration());
        dataProcess.setCountryLetter(subscriptionDto.getCountryLetter());
        dataProcess.setLastModifiedDate(subscriptionDto.getLastModifiedDate());
        dataProcess.setSubscriptionDate(subscriptionDto.getSubscriptionDate());
        dataProcess.setBillingDate(subscriptionDto.getBillingDate());
        dataProcess.setTrial(subscriptionDto.isTrial());
        dataProcess.setPurchaseDate(subscriptionDto.getPurchaseDate());
        dataProcess.setBillingDate(subscriptionDto.getBillingDate());
        dataProcess.setAccountCreditsDate(subscriptionDto.getAccountCreditsDate());
        dataProcess.setExpireDate(subscriptionDto.getExpireDate());
        dataProcess.setNextModifiedDate(subscriptionDto.getNextModifiedDate());
        dataProcess.setAmountExact(subscriptionDto.isAmountExact());
        dataProcess.setContentId(subscriptionDto.getContentId());
        dataProcess.setCredits(subscriptionDto.getCredits());
        dataProcess.setGroupId(subscriptionDto.getGroupId());
        dataProcess.setOsType(subscriptionDto.getOsType());
        dataProcess.setPhoneModel(subscriptionDto.getPhoneModel());

        logger.info("dataProcess.getAdProvider " + dataProcess.getAdProvider());
        dataProcess.setAdProvider(subscriptionDto.getAdProvider());
        dataProcess.setGenerateVoucherCode(subscriptionDto.isGenerateVoucherCode());
        accountService.subscribe();
        ResponseSubscribeDto responseSubscribe = new ResponseSubscribeDto();
        responseSubscribe.setUserId(dataProcess.getUserId());
        responseSubscribe.setNewUser(dataProcess.isNewUser());
        responseSubscribe.setMsisdn(dataProcess.getMsisdn());

        return buildSuccessWrapperResponse(responseSubscribe);
    }

    @PUT
    @Path("/renew")
    public Response renew(SubscriptionDataDto data) throws BaseException {
        this.logger.info("[PUT][START] /renew/{} ", data);
        dataProcess.setMsisdn(data.getMsisdn());
        dataProcess.setSite(data.getSiteId());
        dataProcess.setSubscriptionData(data);
        dataProcess.setEventType(EventTypeDto.RENEW.name());
        dataProcess.setSubscriptionDuration(data.getSubscriptionDuration());
        dataProcess.setContentPackage(data.getContentPackageId());
        dataProcess.setPriceCode(data.getPriceCodeId().toString());
        dataProcess.setAmount(data.getAmount());
        dataProcess.setMakeBilling(data.isMakeBilling());
        dataProcess.setPurchaseDate(data.getPurchaseDate());
        dataProcess.setBillingDate(data.getBillingDate());
        dataProcess.setAccountCreditsDate(data.getAccountCreditsDate());
        dataProcess.setLastModifiedDate(data.getLastModifiedDate());
        dataProcess.setSource(data.getSource());
        dataProcess.setNextModifiedDate(data.getNextModifiedDate());
        dataProcess.setExpireDate(data.getExpireDate());
        dataProcess.setAmountExact(data.isAmountExact());
        accountService.renew();

        SubscriptionResourceDto response = dataProcess.getSubscriptionResource();

        this.logger.info("[PUT][END] /renew", data);
        return buildSuccessWrapperResponse(response);
    }

    @PUT
    @Path("/cancel/{msisdn}/{site}/{cancelationType}")
    public Response cancel(@PathParam("msisdn") String msisdn, @PathParam("site") Integer site,
                           @PathParam("cancelationType") String eventType) throws BaseException {
        this.logger.info("[PUT][START] /cancel/msisdn : {}, site: {} , cancelationType: {} ", msisdn, site, eventType);
        dataProcess.setSite(site);
        dataProcess.setMsisdn(msisdn);
        dataProcess.setEventType(eventType);
        accountService.cancel();
        this.logger.info("[PUT][END] /cancel/msisdn : {}, site: {} , cancelationType: {} ", msisdn, site, eventType);
        return buildSuccessWrapperResponse();
    }

    @PUT
    @Path("/cancel")
    public Response cancel(CancellationDto cancellationDto) throws BaseException {
        dataProcess.setSite(cancellationDto.getSiteId());
        dataProcess.setMsisdn(cancellationDto.getMsisdn());
        dataProcess.setEventType(cancellationDto.getEventType().toString());
        dataProcess.setCancellationDate(cancellationDto.getCancellationDate());
        dataProcess.setExternalCancellation(cancellationDto.isExternalCancellation());
        dataProcess.setAppId(cancellationDto.getAppId());
        dataProcess.setPartnerId(cancellationDto.getPartnerId());
        dataProcess.setExternalProductId(cancellationDto.getProductId());
        accountService.cancel();
        return buildSuccessWrapperResponse();
    }



    @PUT
    @Path("/suspend")
    @Consumes("application/json")
    public Response suspend( SuspendDto suspendDto )
    throws BaseException{
        logger.info("INit suspend " + suspendDto );
        dataProcess.setSite(suspendDto.getSite());
        dataProcess.setMsisdn(suspendDto.getMsisdn());
        logger.info("dataProcess.getEventType()  " + dataProcess.getEventType());
        dataProcess.setEventType(suspendDto.getEventType());
        logger.info("dataProcess.getEventType()  " + dataProcess.getEventType());
        dataProcess.setSuspendDate(suspendDto.getSuspendDate());
        dataProcess.setStrNextModifiedDate(suspendDto.getStrNextModifiedDate());
        accountService.suspend();
        return buildSuccessWrapperResponse();
    }



}
