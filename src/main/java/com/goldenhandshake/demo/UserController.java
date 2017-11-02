/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldenhandshake.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/user")
    public ResponseEntity<?> getFirstUser(){
       User user= userRepository.findAll().get(0);
       return  ResponseEntity.ok(user);
        
    }
    
}
