package com.SlicK.soap_demo2;

import com.SlicK.service2.GetOrderRequest;
import com.SlicK.service2.GetOrderResponse;
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
    public GetOrderResponse getOrder(@RequestPayload GetOrderRequest request) {
        GetOrderResponse response = new GetOrderResponse();
        response.setOrderDate(LocalDate.now());
        response.setTotalAmount(BigDecimal.valueOf(99.99));
        response.setStatus("Shipped");
        return response;
    }
}