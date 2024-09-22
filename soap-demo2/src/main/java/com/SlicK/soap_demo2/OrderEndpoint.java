package com.SlicK.soap_demo2;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigDecimal;
import java.time.LocalDate;

@Endpoint
public class OrderEndpoint {

    private static final String NAMESPACE_URI = "http://slick.com/service2";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrderRequest")
    @ResponsePayload
    public com.SlicK.service2.GetOrderResponse getOrder(@RequestPayload com.slick.service2.GetOrderRequest request) {
        com.SlicK.service2.GetOrderResponse response = new com.SlicK.service2.GetOrderResponse();
        response.setOrderDate(LocalDate.now());
        response.setTotalAmount(BigDecimal.valueOf(99.99));
        response.setStatus("Shipped");
        return response;
    }
}