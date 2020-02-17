/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author austine.okoroafor
 */
@Entity
@Data
@DynamicUpdate
public class CardScheme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cardnum;
    private String scheme;
    private String transaction_type;
    private String brand;
    private String Country;
    private String bank_name;
    private long card_numberof_calls;

}
