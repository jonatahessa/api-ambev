package com.jonata.api.consumers;

import com.jonata.api.model.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpConsumer {
    
    public ResponseEntity<Product> getRequisition(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);

        return response;
    }
}