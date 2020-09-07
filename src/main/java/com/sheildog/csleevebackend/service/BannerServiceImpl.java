package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Banner;
import com.sheildog.csleevebackend.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author a7818
 */
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner getByName(String name){
        return bannerRepository.findOneByName(name);
    }
}
