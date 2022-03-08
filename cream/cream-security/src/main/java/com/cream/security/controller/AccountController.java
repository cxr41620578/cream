/**
 * 
 */
package com.cream.security.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String login(HttpServletRequest request) {
        Device device = DeviceUtils.getCurrentDevice(request);
        System.out.println("device.isMobile:"+device.isMobile());
        System.out.println("device.isTablet:"+device.isTablet());
        System.out.println("device.isNormal:"+device.isNormal());
 
        System.out.println("device.Platform:"+device.getDevicePlatform());
        return "login";
    }
    
    @GetMapping("/regist")
    public String regist() {
        return "regist";
    }

    @ResponseBody
    @RequestMapping("/login/error")
    public String loginError(HttpServletRequest request) {
        return request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION).toString();
    }
}
