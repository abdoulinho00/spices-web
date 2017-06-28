package com.aelbardai.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.net.Authenticator;

@Controller
@Slf4j
@RequestMapping("/profile")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileController {


    @GetMapping("/myfiles")
    public void showProfileFiles(){
        log.info("trying to show content of folder");
    }

    @GetMapping({"/",""})
    public ModelAndView viewProfile(){

        return new ModelAndView("profile/view" , "user" , SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
