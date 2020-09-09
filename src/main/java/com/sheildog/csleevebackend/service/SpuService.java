package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Spu;
import com.sheildog.csleevebackend.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author a7818
 */
@Service
public class SpuService {
    @Autowired
    private SpuRepository spuRepository;

    public Spu getSpu(Long id){
        return this.spuRepository.findOneById(id);
    }
}
