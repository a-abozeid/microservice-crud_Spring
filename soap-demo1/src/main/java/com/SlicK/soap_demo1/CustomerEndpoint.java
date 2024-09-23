package com.SlicK.soap_demo1;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.slick.service1.GetCustomerResponse;
import com.slick.service1.GetCustomerRequest;

@Endpoint
public class CustomerEndpoint {

    private static final String NAMESPACE_URI = "http://slick.com/service1";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetCustomerRequest")
    @ResponsePayload
    public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
        GetCustomerResponse response = new GetCustomerResponse();
        response.setName("test");
        response.setEmail("test@example.com");
        response.setPhone("12345678910");
        return response;
    }
}