package com.aelbardai.service;

import com.aelbardai.domain.Spice;
import com.aelbardai.repository.SpiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dev on 5/23/17.
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpiceServiceImpl implements SpiceService{

    private final SpiceRepository spiceRepository;

    public Spice save(Spice spice){
        if(spice != null){
            return spiceRepository.save(spice);
        }
        return null;
    }

    public Spice getSpiceById(Long id){
        return spiceRepository.findOne(id);
    }

    public List<Spice> getAllSpices(){
        return (List<Spice>) spiceRepository.findAll();
    }
}
