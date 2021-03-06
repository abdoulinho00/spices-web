package com.aelbardai.spices.service;

import com.aelbardai.spices.domain.Spice;

import java.util.List;

public interface SpiceService {

    Spice save(Spice spice);
    Spice getSpiceById(Long id);
    List<Spice> getAllSpices();
    Spice getLastSpice();
    Spice getRandomSpice();
}
