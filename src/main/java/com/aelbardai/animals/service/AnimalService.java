package com.aelbardai.animals.service;


import com.aelbardai.animals.domain.Animal;

import java.util.List;

public interface AnimalService {

    List<Animal> getAllAnimals();
    Animal getLastAnimal();
    Animal getRandomAnimal();
    Animal save(Animal animal);

}
