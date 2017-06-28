package com.aelbardai.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdministrationController {

    public ModelAndView mainMenu(){
        return new ModelAndView("admin/home");
    }
}
