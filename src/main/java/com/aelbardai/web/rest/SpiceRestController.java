package com.aelbardai.web.rest;

import com.aelbardai.domain.Spice;
import com.aelbardai.service.SpiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dev on 5/23/17.
 */
@RestController
@RequestMapping("/api/spices")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpiceRestController {

    private final SpiceService spiceService;

    @GetMapping
    public List<Spice> getAllSpices(){
        return spiceService.getAllSpices();
    }
}
