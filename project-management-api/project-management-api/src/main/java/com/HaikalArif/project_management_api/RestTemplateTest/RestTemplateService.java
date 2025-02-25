package com.HaikalArif.project_management_api.RestTemplateTest;

import com.HaikalArif.project_management_api.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService {
    @Autowired
    private RestTemplate restTemplate;

}
