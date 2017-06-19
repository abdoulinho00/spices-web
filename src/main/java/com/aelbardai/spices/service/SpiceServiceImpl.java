package com.aelbardai.spices.service;

import com.aelbardai.spices.domain.Spice;
import com.aelbardai.spices.repository.SpiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Spice service class
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpiceServiceImpl implements SpiceService {

    private final SpiceRepository spiceRepository;

    @Override
    public Spice save(Spice spice) {
        if (spice != null) {
            return spiceRepository.save(spice);
        }
        return null;
    }

    @Override
    public Spice getSpiceById(Long id) {
        return spiceRepository.findOne(id);
    }

    @Override
    public Spice getLastSpice() {
        return spiceRepository.findFirstByOrderByIdDesc();
    }


    @Override
    public Spice getRandomSpice() {
        Long count = spiceRepository.count();
        Spice spice = null;
        if(count > 0) {
            while (spice == null) {
                long number = ThreadLocalRandom.current().nextLong(count) + 1;
                spice = spiceRepository.findOne(number);
            }
        }
        return spice;
    }

    @Override
    public List<Spice> getAllSpices() {
        return (List<Spice>) spiceRepository.findAll();
    }
}
