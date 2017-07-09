package com.aelbardai.web;

import com.aelbardai.animals.domain.Animal;
import com.aelbardai.animals.service.AnimalService;
import com.aelbardai.files.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value= "/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final FileService fileService;

    @GetMapping(value={"/list"})
    public ModelAndView viewAllAnimals(){
        return new ModelAndView("animals/list" , "animals", animalService.getAllAnimals());
    }

    @GetMapping(value={"" , "/"})
    public ModelAndView viewAnimals(){
        ModelAndView model =  new ModelAndView("animals/view" , "lastAnimal", animalService.getLastAnimal());
        model.addObject("randomAnimal" , animalService.getRandomAnimal());
        return model;
    }

    @GetMapping("/{id}")
    public ModelAndView viewAnimalDetails(@PathVariable("id") Long id){
        Animal animal = animalService.getAnimalById(id);
        return new ModelAndView("animals/details" ,"animal" , animal);
    }

    @GetMapping("/add")
    public ModelAndView addanimalView(){
        ModelAndView modelAndView =new ModelAndView("animals/add" , "animal", new Animal());
        modelAndView.addObject("images" , fileService.getImages());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addanimalSubmit(@Valid Animal animal){
        animalService.save(animal);
        return new ModelAndView("redirect:/animals");
    }
}
