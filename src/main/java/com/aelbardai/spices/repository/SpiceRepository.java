package com.aelbardai.spices.repository;

import com.aelbardai.spices.domain.Spice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spice Repository class
 */
@Repository
public interface SpiceRepository extends CrudRepository<Spice, Long> {

    Spice findFirstByOrderByIdDesc();
}
