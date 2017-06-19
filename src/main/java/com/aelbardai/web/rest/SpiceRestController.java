package com.aelbardai.web.rest;

import com.aelbardai.spices.domain.Spice;
import com.aelbardai.spices.service.SpiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/spices")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpiceRestController {

    private final SpiceService spiceService;

    @GetMapping
    public List<Spice> getAllSpices(){
        return spiceService.getAllSpices();
    }

    @PostMapping("/create")
    public Spice createSpice(Spice spice){
        return spiceService.save(spice);
    }

    @GetMapping("/{id}")
    public Spice getSpice(@PathVariable("id") Long id){
        if(id != null){
            return spiceService.getSpiceById(id);
        }
        else{
            return null;
        }

    }
}
