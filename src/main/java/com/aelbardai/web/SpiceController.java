package com.aelbardai.web;

import com.aelbardai.service.SpiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * Created by dev on 5/23/17.
 */
@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value= "/spices")
public class SpiceController {

    private final SpiceService spiceService;

    @GetMapping(value={"", "/", "/list"})
    public ModelAndView viewSpices(){
        log.info("from view Spices method");
        return new ModelAndView("spices/view" , "spices", spiceService.getAllSpices());
    }

    @GetMapping("/add")
    public String addSpiceView(){
        return "add" ;
    }

    @PostMapping("/add")
    public ModelAndView addSpiceSubmit(){
        return new ModelAndView("index" , "spices", spiceService.getAllSpices());
    }
}
