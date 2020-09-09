package com.sheildog.csleevebackend.repository;

import com.sheildog.csleevebackend.model.Spu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author a7818
 */
public interface SpuRepository extends JpaRepository<Spu, Long> {
    Spu findOneById(Long id);
}
