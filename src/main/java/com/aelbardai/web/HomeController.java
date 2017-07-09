package com.aelbardai.web;

import com.aelbardai.animals.service.AnimalService;
import com.aelbardai.spices.service.SpiceService;
import com.aelbardai.user.domain.UserCreateForm;
import com.aelbardai.user.service.UserCreateFormValidator;
import com.aelbardai.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {

    private final AnimalService animalService;
    private final SpiceService spiceService;
    private final UserService userService;
    private final UserCreateFormValidator userCreateFormValidator;

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

    @GetMapping({"/", "/home"})
    public ModelAndView homePage(){

        ModelAndView model = new ModelAndView("index", "animal" , animalService.getRandomAnimal());
        model.addObject("spice"  ,spiceService.getRandomSpice());
        return model;
    }
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("user/create", "form", new UserCreateForm());
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView handleUserCreateForm(@Valid @ModelAttribute("form") UserCreateForm form, BindingResult bindingResult) {
        log.info("trying to signup new user : {}" , form.getEmail());
        if (bindingResult.hasErrors()) {
            return new ModelAndView("user/create");
        }
        try {
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("email.exists", "Email already exists");
            return new ModelAndView("user/create");
        }
        return new ModelAndView("redirect:/home");
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
