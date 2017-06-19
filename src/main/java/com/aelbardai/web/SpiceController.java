package com.aelbardai.web;

import com.aelbardai.spices.domain.Spice;
import com.aelbardai.spices.service.SpiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value= "/spices")
public class SpiceController {

    private final SpiceService spiceService;

    @GetMapping(value={"/list"})
    public ModelAndView viewAllSpices(){
        return new ModelAndView("spices/list" , "spices", spiceService.getAllSpices());
    }

    @GetMapping(value={"" , "/"})
    public ModelAndView viewSpices(){
        ModelAndView model =  new ModelAndView("spices/view" , "lastSpice", spiceService.getLastSpice());
        model.addObject("randomSpice" , spiceService.getRandomSpice());
        return model;
    }

    @GetMapping("/add")
    public ModelAndView addSpiceView(){
        return new ModelAndView("spices/add" , "spice", new Spice());
    }

    @PostMapping("/add")
    public ModelAndView addSpiceSubmit(@Valid Spice spice){
        spiceService.save(spice);
        return new ModelAndView("redirect:/spices" , "spices", spiceService.getAllSpices());
    }
}
