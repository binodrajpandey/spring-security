/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldenhandshake.demo;

import com.goldenhandshake.demo.entities.User;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author binod
 */
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    
}
