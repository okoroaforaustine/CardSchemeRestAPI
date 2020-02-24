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
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletOutputStream;
import javax.websocket.Session;
import org.springframework.messaging.MessagingException;


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

    @RequestMapping(value = "/register", method = RequestMethod.POST,produces = "Application/json", consumes = "Application/json")
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

    @GetMapping("/export-users")
    public void exportCSV(HttpServletResponse response) throws Exception {
  String FILE_NAME="";
        //set file name and content type
         ServletOutputStream out = null;
        String filename = "users.csv";
       AppUser user=null;
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<AppUser> writer = new StatefulBeanToCsvBuilder<AppUser>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

      FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
           
     //attachPart( writer.write(userRepo.findByUsername(user.getUsername());
       
       
         
                
    }
    
    
    
    
}
