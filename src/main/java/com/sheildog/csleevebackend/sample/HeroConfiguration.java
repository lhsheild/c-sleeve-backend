package com.sheildog.csleevebackend.sample;

import com.sheildog.csleevebackend.sample.hero.Camille;
import com.sheildog.csleevebackend.sample.hero.ISkill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author a7818
 */
@Configuration
public class HeroConfiguration {
    @Bean
    public ISkill camille() {
        return new Camille();
    }
}
