package com.lamark.business.command.core.pattern.dto;

import com.lamark.shared.dto.*;
import com.lamark.shared.enums.EventType;
import lombok.Data;

import javax.enterprise.context.RequestScoped;
import java.time.LocalDateTime;
import java.util.Date;

@RequestScoped
@Data
public class DataProcess {
    private Integer site;
    private String msisdn;
    private Long userId;
    private String cancellationType;
    private UserDto user;
    private SubscriptionDataDto subscriptionData;
    private SubscriptionResourceDto subscriptionResource;
    private String eventType;
    private Integer subscriptionDuration;
    private Integer stateSubscription;
    private SubscriptionDto subscription;
    private Long chargeRecord;
    private boolean newUser;
    private boolean newSuscribed;
    private Integer productId;
    private String channel;
    private Long accountId;
    private String keyword;
    private Integer contentPackage;
    private Date lastModifiedDate;
    private Date nextModifiedDate;
    private Date subscriptionDate;
    private String priceCode;
    private String amount;
    private SSLStateDto sslState;
    private boolean makeBilling;
    private Long accountAsset;
    private String countryLetter;
    private Date billingDate;
    private Date purchaseDate;
    private LocalDateTime cancellationDate;
    private Date accountCreditsDate;
    private boolean isTrial;
    private String source;
    private Date expireDate;
    private boolean amountExact;
    private String adProvider;
    private String suspendDate;
    //External cancellation
    private boolean externalCancellation;
    private String appId;
    private String partnerId;
    private boolean generateVoucherCode;
    private String strNextModifiedDate;
    private String externalProductId;
    //Content credit
    private Integer contentId;
    private Integer credits;
    private Integer groupId;
    private String osType;
    private String osVersion;
    private String phoneModel;


}
