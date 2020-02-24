/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.util;

import com.mint.cardscheme.dto.Response;
import com.mint.cardscheme.dto.Error;
import com.mint.cardscheme.dto.Payload;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import com.mint.cardscheme.paginationdto.Payloads;
import com.mint.cardscheme.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author austine.okoroafor
 */
@Component
public class AppUtil {
       @Autowired
    KafkaProducerService producer;
    public ResponseEntity<Response> returnErrorResponse(List<Error> errors, HttpStatus code) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Response response = new Response();
        response.setStatus(ConstantsUtil.ERROR);
        response.setErrors(errors);
        return new ResponseEntity<Response>(response, httpHeaders, code);
    }

    public ResponseEntity<Response> returnErrorResponse(String error, HttpStatus code) {
        List<Error> errors = new ArrayList<Error>();
        errors.add(new Error(error, 4));
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Response response = new Response();
        response.setStatus(ConstantsUtil.ERROR);
        response.setErrors(errors);
        return new ResponseEntity<Response>(response, httpHeaders, code);
    }

    public ResponseEntity<Response> returnPostValidationErrors(Errors errors) {
        //log.debug("Returning post validation errors...");
        List<FieldError> fields = errors.getFieldErrors();
        List<Error> errorList = new ArrayList<Error>();
        Iterator<FieldError> eIt = fields.iterator();
        while (eIt.hasNext()) {
            FieldError fe = eIt.next();
            errorList.add(getFieldRequiredError(fe.getField()));
        }
        return returnErrorResponse(errorList, HttpStatus.BAD_REQUEST);
    }

    public Error getFieldRequiredError(String field) {
        return new Error(ConstantsUtil.FIELD_REQUIRED_MESSAGE.replace("{}", field), 4, field);
    }

    public ResponseEntity<Payload> returnSuccessResponse(Object responseObj, String message) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Payload payload = new Payload();
        payload.setSuccess(ConstantsUtil.SUCCESS);

        payload.setPayload(responseObj);
         producer.sendMessage(payload);
        return new ResponseEntity<Payload>(payload, httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<Payloads> returnSuccessResponse(Object responseObj, int start, int limit, int size) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Payloads payload = new Payloads();
        payload.setSuccess(ConstantsUtil.SUCCESS);
        payload.setStart(start);
        payload.setLimit(limit);
        payload.setSize(size);
        payload.setPayload(responseObj);
        return new ResponseEntity<Payloads>(payload, httpHeaders, HttpStatus.OK);
    }

}
