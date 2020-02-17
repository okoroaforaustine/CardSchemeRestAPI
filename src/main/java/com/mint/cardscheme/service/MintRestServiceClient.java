/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.service;

import com.mint.cardscheme.entity.CardScheme;
import com.mint.cardscheme.repository.CardSchemeRepository;
import com.mint.cardscheme.util.PropertyUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author austine.okoroafor
 */
@Service
public class MintRestServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(MintRestServiceClient.class);
    private String baseUrl = PropertyUtil.msg("base_url");
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CardSchemeRepository repository;

    public ResponseEntity connect(String cardNumber) {

        String Url = baseUrl+"/" + cardNumber;

        ResponseEntity response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(cardNumber, headers);
        try {
            response = restTemplate.exchange(Url, HttpMethod.GET, entity, String.class);
            return response;
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }

    }

    public List<CardScheme> getAllCards(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<CardScheme> pagedResult = repository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<CardScheme>();
        }
    }

}
