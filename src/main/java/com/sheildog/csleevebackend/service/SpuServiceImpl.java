package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Spu;
import com.sheildog.csleevebackend.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Spu getSpu(Long id) {
        return this.spuRepository.findOneById(id);
    }

    @Override
    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size) {
        Pageable page = PageRequest.of(pageNum, size, Sort.by("createTime").descending());
        return this.spuRepository.findAll(page);
    }
}
