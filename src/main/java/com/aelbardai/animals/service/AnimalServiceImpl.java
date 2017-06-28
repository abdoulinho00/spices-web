package com.aelbardai.animals.service;


import com.aelbardai.animals.domain.Animal;
import com.aelbardai.animals.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnimalServiceImpl implements AnimalService{

    private final AnimalRepository animalRepository;
    @Override
    public List<Animal> getAllAnimals() {
        return (List<Animal>) animalRepository.findAll();
    }

    @Override
    public Animal getLastAnimal() {
        return animalRepository.findFirstByOrderByIdDesc();
    }

    @Override
    public Animal getRandomAnimal() {
        Long count = animalRepository.count();
        Animal animal = null;
        if(count > 0) {
            while (animal == null) {
                long number = ThreadLocalRandom.current().nextLong(count) + 1;
                animal = animalRepository.findOne(number);
            }
        }
        return animal;
    }

    @Override
    public Animal save(Animal animal) {
        if(animal != null) {
            return animalRepository.save(animal);
        }
        else {
            return null;
        }
    }

    @Override
    public Animal getAnimalById(Long id){
        return animalRepository.findOne(id);
    }
}
