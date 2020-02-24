/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mint.cardscheme.dto.Response;
import com.mint.cardscheme.entity.AppUser;
import com.mint.cardscheme.entity.CardScheme;
import com.mint.cardscheme.repository.CardSchemeRepository;
import com.mint.cardscheme.service.KafkaProducerService;
import com.mint.cardscheme.service.MintRestServiceClient;
import com.mint.cardscheme.util.AppUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author austine.okoroafor
 */
@RestController
@RequestMapping("/card-scheme")
public class CardSchemeController {

    @Autowired
    MintRestServiceClient mintRest;

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    CardSchemeRepository Repo;
    @Autowired
    AppUtil util;
    
       @Autowired
    KafkaProducerService producer;

    @GetMapping(value = "/verify/{card_number}", produces = "Application/json", consumes = "Application/json")
    public ResponseEntity<?> cardScheme(@PathVariable("card_number") String card_number) throws IOException {

        ResponseEntity resp = mintRest.connect(card_number);
        String countryName = "";
        String bankName = "";
        long count = 1;
        if (resp.getStatusCode() == HttpStatus.OK) {
            try {
                CardScheme schemeObj = new CardScheme();

                JsonNode root = mapper.readTree(resp.getBody().toString());
                String Scheme = root.path("scheme").asText();
                String trans_type = root.path("type").asText();
                String brand = root.path("brand").asText();
                JsonNode CountryNode = root.path("country");

                countryName = CountryNode.path("name").asText();

                JsonNode bankNode = root.path("bank");

                bankName = bankNode.path("name").asText();
                CardScheme card = Repo.findByCardnum(card_number);

                schemeObj.setScheme(Scheme);
                schemeObj.setTransaction_type(trans_type);
                schemeObj.setBrand(brand);
                schemeObj.setCountry(countryName);
                schemeObj.setCardnum(card_number);
                schemeObj.setBank_name(bankName);
                if (null == card) {
                    Repo.save(schemeObj);
                } else {
                    count++;
                    long cnt = card.getCard_numberof_calls() + count;

                    card.setCard_numberof_calls(cnt);
                    Repo.save(card);
                }

                Map data = new HashMap();
                data.put("scheme", schemeObj.getScheme());
                data.put("type", schemeObj.getTransaction_type());
                data.put("bank", schemeObj.getBank_name());
                
               // for(int i=0;i<data.size();i++){
                
                // producer.sendMessage(data);
                
              //  }
               
                 
    
                return util.returnSuccessResponse(data, "");

            } catch (JsonProcessingException ex) {
                Logger.getLogger(CardSchemeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/stats", produces = "Application/json", consumes = "Application/json")
    public ResponseEntity<?> findAll(@RequestParam("start") int start, @RequestParam("limit") int limit) throws JsonProcessingException {

        List<CardScheme> list = mintRest.getAllCards(start, limit, "cardnum");
        int size=list.size();
        Map data = new HashMap();
        for (int i = 0; i < list.size(); i++) {

            String cardnumber = list.get(i).getCardnum();
            long count = list.get(i).getCard_numberof_calls();

            data.put(cardnumber, count);
        }
        return util.returnSuccessResponse(data, start,limit,size);

    }

    
    
}
