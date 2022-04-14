package com.lamark.business.command.core.command;

import com.lamark.architecture.corems.util.DateUtil;
import com.lamark.business.command.core.client.UserContentCreditsClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.UserContentCreditsDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Optional;

@Singleton
public class CreateUserContentCreditsCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(CreateUserContentCreditsCommand.class);

    @Inject
    DataProcess dataProcess;

    @Inject
    @RestClient
    UserContentCreditsClient userContentCreditsClient;

    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");

        Optional.of(dataProcess.isMakeBilling())
                .filter(d -> d)
                .map(s -> createUserContentCredits());
    }

    public UserContentCreditsDto createUserContentCredits() {
        UserContentCreditsDto ucc = new UserContentCreditsDto();
        if (dataProcess.getSite().equals(126)) {
            ucc.setContentID(1);
            ucc.setCredits(599);
            ucc.setGroupID(1);
            ucc.setMsisdn(dataProcess.getMsisdn());
            ucc.setOsType("unknown");
            ucc.setPhoneModel("unknown");
            ucc.setValidUntil(DateUtil.getDate("2050-01-01 00:00:00"));
            ucc.setUserID(dataProcess.getUserId());
            userContentCreditsClient.save(ucc);
            return ucc;
        } else {
            if (Arrays.asList(51, 103, 105, 106, 139, 140, 141).contains(dataProcess.getSite())) {//KANTOO
                ucc.setContentID(dataProcess.getContentId());
                ucc.setCredits(dataProcess.getCredits());
                ucc.setGroupID(dataProcess.getGroupId());
                ucc.setMsisdn(dataProcess.getMsisdn());
                ucc.setOsType(dataProcess.getOsType());
                ucc.setPhoneModel(dataProcess.getPhoneModel());
                ucc.setValidUntil(dataProcess.getNextModifiedDate());
                ucc.setUserID(dataProcess.getUserId());
                userContentCreditsClient.save(ucc);
            }
        }
        return null;
    }
}
