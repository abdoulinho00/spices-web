package com.aelbardai.service;

import com.aelbardai.domain.Spice;

import java.util.List;

public interface SpiceService {

    Spice save(Spice spice);
    Spice getSpiceById(Long id);
    List<Spice> getAllSpices();
}
