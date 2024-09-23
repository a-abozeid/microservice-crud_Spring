package com.slick.soap_demo2;

import com.slick.service1.GetCustomerRequest;
import com.slick.service1.GetCustomerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class CustomerClient extends WebServiceGatewaySupport {

    @Value("${soap.server.url}")
    private String soapServerUrl;

    public GetCustomerResponse getCustomer(String customerId) {
        GetCustomerRequest request = new GetCustomerRequest();
        request.setCustomerId(customerId);

        GetCustomerResponse response = (GetCustomerResponse) getWebServiceTemplate()
                .marshalSendAndReceive(soapServerUrl, request,
                        new SoapActionCallback("http://slick.com/service1/GetCustomerRequest"));

        return response;
    }
}
