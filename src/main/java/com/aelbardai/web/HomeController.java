package com.aelbardai.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String homePage(){
        return "index";
    }


    @RequestMapping("/api")
    public ModelAndView swagger(){
        return new ModelAndView("redirect:/swagger-ui.html");
    }

}
