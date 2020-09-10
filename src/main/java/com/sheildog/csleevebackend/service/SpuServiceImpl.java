package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Spu;
import com.sheildog.csleevebackend.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author a7818
 */
@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private SpuRepository spuRepository;

    @Override
    public Spu getSpu(Long id){
        return this.spuRepository.findOneById(id);
    }

    @Override
    public List<Spu> getLatestPagingSpu(){
        return this.spuRepository.findAll();
    }
}
