/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.paginationdto;

import lombok.Data;

/**
 *
 * @author austine.okoroafor
 */
@Data
public class Payloads {

    String success;
    int start;
    int limit;
    int size;
    Object payload;
}
