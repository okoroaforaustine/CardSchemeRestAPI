/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.service;

import com.mint.cardscheme.dto.Payload;
import com.mint.cardscheme.entity.CardScheme;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author austine.okoroafor
 */
@Service
public class KafkaProducerService {


    private static final String TOPIC = "com.ng.vela.even.card_verified";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    
    public void sendMessage(Payload message) {
        System.out.println(String.format("#### -> Producing message -> %s", message));
        kafkaTemplate.send(TOPIC, message.toString());
    }

}
