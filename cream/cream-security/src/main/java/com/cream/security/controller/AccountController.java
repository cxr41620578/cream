/**
 * 
 */
package com.cream.security.controller;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cream
 *
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/captcha")
    public void captcha(OutputStream out) throws IOException {
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/login/error")
    public String loginError() {
        return "error";
    }
}
