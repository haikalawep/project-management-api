package com.HaikalArif.project_management_api.RestTemplateTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configs {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
