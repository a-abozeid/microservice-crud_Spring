package com.SlicK.rest_4crud.Controller;

import com.SlicK.rest_4crud.Model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/rest/{id}")
    public User getPerson(@PathVariable int id, @RequestHeader("Authorization") String  token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        HashMap<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("id", id);
        ResponseEntity<User> responseEntity = new RestTemplate()
                .exchange("http://localhost:8000/persons/{id}", HttpMethod.GET, entity, User.class, uriVariables);

        User user = responseEntity.getBody();
        return new User(user.getId(), user.getName(), user.getEmail());
    }
}
