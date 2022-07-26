package org.example;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.awt.image.RescaleOp;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

@Component
public class Communication {
    private final String URL = "http://94.198.50.185:7081/api/users";

    @Autowired
    private RestTemplate restTemplate;

    public List<String> getCookie() {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(URL, List.class);
        List<String> cookie = responseEntity.getHeaders().get("Set-Cookie");
        return cookie;
    }

    public HttpHeaders setHttpHeaders(List<String> cookies) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        return httpHeaders;
    }

    public String saveUser (User user, List<String> cookies) {
        HttpHeaders headers = setHttpHeaders(cookies);
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, requestBody, String.class);
        return responseEntity.getBody();
    }

    public String deleteUser (int id, List<String> cookies) {
        HttpHeaders headers = setHttpHeaders(cookies);
        HttpEntity<User> requestBody = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL+"/"+id, HttpMethod.DELETE, requestBody, String.class);
        return responseEntity.getBody();
    }

    public String editUser (User user, List<String> cookies) {
        HttpHeaders headers = setHttpHeaders(cookies);
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestBody, String.class);
        return responseEntity.getBody();
    }
}
