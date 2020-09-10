package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Banner;
import com.sheildog.csleevebackend.model.Spu;

import java.util.List;

/**
 * @author a7818
 */
public interface SpuService {
    Spu getSpu(Long id);
    List<Spu> getLatestPagingSpu();
}
