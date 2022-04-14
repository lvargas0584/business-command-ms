package com.lamark.business.command.core.command;

import com.lamark.architecture.corems.exception.ExceptionHelper;

import com.lamark.business.command.core.client.ProductCoreClient;
import com.lamark.business.command.core.pattern.command.Command;
import com.lamark.business.command.core.pattern.dto.DataProcess;
import com.lamark.shared.dto.ProductDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class GetProductBySiteCommand implements Command {

    private final Logger logger = LoggerFactory.getLogger(GetProductBySiteCommand.class);

    @Inject
    @RestClient
    ProductCoreClient productCoreClient;

    @Inject
    DataProcess dataProcess;

    @Override
    public void execute() {
        logger.info("execute command: [" + this.getClass().getName() + "]");
        Optional.ofNullable(dataProcess.getSite()).orElseThrow(() -> ExceptionHelper.buildRuntimeException("Site : {0} not found", dataProcess.getSite()));
        List<ProductDto> productsForSite = productCoreClient.getProductsForSite(dataProcess.getSite());
        Integer productId = productsForSite.stream().findFirst().map(s -> s.getProductID()).orElseThrow(() -> ExceptionHelper.buildRuntimeException("Products not found for site : {0}", dataProcess.getSite()));
        dataProcess.setProductId(productId);
    }
}
