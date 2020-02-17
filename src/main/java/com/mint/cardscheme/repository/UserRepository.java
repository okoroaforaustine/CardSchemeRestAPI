/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.repository;

import com.mint.cardscheme.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author austine.okoroafor
 */
public interface UserRepository extends CrudRepository<AppUser, Long>{
     
  AppUser  findByUsername(String  username);
    
}
