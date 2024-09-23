package com.slick.soap_demo2;

import com.slick.service1.GetCustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerClient customerClient;

    @GetMapping("/get-customer/{id}")
    public GetCustomerResponse getCustomer(@PathVariable String id) {
        return customerClient.getCustomer(id);
    }
}
