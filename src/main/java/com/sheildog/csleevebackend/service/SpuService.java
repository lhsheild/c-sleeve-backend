package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Banner;
import com.sheildog.csleevebackend.model.Spu;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author a7818
 */
public interface SpuService {
    Spu getSpu(Long id);
    Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size);
}
