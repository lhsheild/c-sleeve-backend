package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Banner;
import com.sheildog.csleevebackend.model.Spu;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author a7818
 */
public interface SpuService {
    Spu getSpu(Long id);
    Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size);
    Page<Spu> getByCategory(Long cid, Boolean isRoot, Integer pageNum, Integer size);
}
