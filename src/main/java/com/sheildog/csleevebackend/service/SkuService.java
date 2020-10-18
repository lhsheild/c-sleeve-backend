package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Sku;
import com.sheildog.csleevebackend.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuService {
    @Autowired
    private SkuRepository skuRepository;

    public List<Sku> getSkuListByIds(List<Long> ids){
        return this.skuRepository.findAllByIdIn(ids);
    }
}
