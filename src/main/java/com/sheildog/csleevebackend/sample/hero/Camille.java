package com.sheildog.csleevebackend.sample.hero;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author a7818
 */
@Component
@Primary
public class Camille implements ISkill {
    @Override
    public void q() {
        System.out.println("Camille q");
    }

    @Override
    public void w() {
        System.out.println("Camille w");
    }

    @Override
    public void e() {
        System.out.println("Camille e");
    }

    @Override
    public void r() {
        System.out.println("Camille r");
    }
}
