/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.controller;

import com.mint.cardscheme.entity.AppUser;
import com.mint.cardscheme.repository.UserRepository;
import com.mint.cardscheme.util.AppUtil;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import com.mint.cardscheme.dto.Error;
import com.mint.cardscheme.dto.Response;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author austine.okoroafor
 */
@RestController

@RequestMapping("/User")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AppUtil appUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody AppUser user, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return appUtils.returnPostValidationErrors(errors);
        }

        List<Error> err = validate(user);
        if (!err.isEmpty()) {
            return appUtils.returnErrorResponse(err, HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return appUtils.returnSuccessResponse(user, "Your registration was Succesful");
    }

    public List<Error> validate(AppUser user) {
        List<Error> err = new ArrayList<Error>();
        AppUser usr = userRepo.findByUsername(user.getUsername());
        if (usr != null) {
            err.add(new Error("Username with the anme " + usr.getUsername() + "has already be taken", 4));
        }

        return err;
    }

}
