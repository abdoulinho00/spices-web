package com.aelbardai.repository;

import com.aelbardai.domain.Spice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dev on 5/23/17.
 */
@Repository
public interface SpiceRepository extends CrudRepository<Spice, Long> {
}
