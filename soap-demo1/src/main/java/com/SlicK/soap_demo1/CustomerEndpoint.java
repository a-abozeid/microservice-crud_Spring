package com.SlicK.soap_demo1;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CustomerEndpoint {

    private static final String NAMESPACE_URI = "http://slick.com/service1";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetCustomerRequest")
    @ResponsePayload
    public com.SlicK.service1.GetCustomerResponse getCustomer(@RequestPayload com.SlicK.service1.GetCustomerRequest request) {
        com.SlicK.service1.GetCustomerResponse response = new com.SlicK.service1.GetCustomerResponse();
        response.setName("test");
        response.setEmail("test@example.com");
        response.setPhone("12345678910");
        return response;
    }
}