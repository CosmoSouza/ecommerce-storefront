package br.com.dio.storefront.service.impl;

import br.com.dio.storefront.dto.StockStatusMessage;
import br.com.dio.storefront.service.IProductChangeAvailabilityConsumer;
import br.com.dio.storefront.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductChangeAvailabilityConsumerImpl implements IProductChangeAvailabilityConsumer {

    private final IProductService service;

    @RabbitListener(queues = "${spring.rabbitmq.queue.product-change-availability}")
    @Override
    public void receive(StockStatusMessage message) {
        service.chanceActivated(message.id(), message.active());
    }
}
