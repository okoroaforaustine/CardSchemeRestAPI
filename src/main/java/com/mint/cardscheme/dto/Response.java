/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author austine.okoroafor
 */
@Data
public class Response {

    String status;

    Integer code;

    String message;

    Object response;

    List<Error> errors;

}
