package com.lamark.business.command.core.command;



import com.lamark.business.command.core.client.UserCoreClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.UserDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreateUserRolCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(CreateUserRolCommand.class);

    @Inject
    @RestClient
    UserCoreClient userCoreClient;


    @Inject
    DataProcess dataProcess;

    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");
        if (dataProcess.getUserId() == null) {//AJGA  : Añadido debido a que en el event en resuscripciones tampoco se vuelve a insertar esta tabla
            UserDto userDto = new UserDto();
            userDto.setUserId(dataProcess.getUserId());
            userDto.setLastSync(dataProcess.getSubscriptionDate());
            userCoreClient.saveUserRole(userDto);
        }
    }
}
