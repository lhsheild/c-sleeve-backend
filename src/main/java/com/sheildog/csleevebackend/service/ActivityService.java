package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.Activity;
import com.sheildog.csleevebackend.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public Activity getByName(String name){
        return activityRepository.findByName(name);
    }
}
