package com.aelbardai.web;

import com.aelbardai.animals.service.AnimalService;
import com.aelbardai.spices.service.SpiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {

    private final AnimalService animalService;
    private final SpiceService spiceService;
    @GetMapping({"/", "/home"})
    public ModelAndView homePage(){

        ModelAndView model = new ModelAndView("index", "animal" , animalService.getRandomAnimal());
        model.addObject("spice"  ,spiceService.getRandomSpice());
        return model;
    }


    @RequestMapping("/api")
    public ModelAndView swagger(){
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

}
