package com.HaikalArif.project_management_api.RestTemplateTest;

import com.HaikalArif.project_management_api.Enum.Role;
import com.HaikalArif.project_management_api.Exception.NotFoundException;
import com.HaikalArif.project_management_api.Model.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RestController
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    String url = "http://localhost:8080/api/user";

    // Get using RestTemplate
    @GetMapping("/api/get/all-users_v1")
    public  User getRestTemplate() {
        long id = 2;
        System.out.println("Method 1: Using getForObject()");

        String getUrl = url + "/{id}";
        // Method 1: Using getForObject()
        User user = restTemplate.getForObject(getUrl, User.class, id);
        System.out.println("User: " + user);

//        System.out.println("Method 2: Using getForEntity()");
//        String url = "http://localhost:8080/api/user/{id}";
//
//        // Method 2: Using getForEntity()
//        ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class, id);
//        User user1 = responseEntity.getBody();
//        System.out.println("User: " + user1);
//
//        System.out.println("Method 3: Using exchange()");
//        // Method 3: Using exchange()
//        ResponseEntity<User> responseEntity2 = restTemplate.exchange(url, HttpMethod.GET, null, User.class, id);
//        User user2 = responseEntity2.getBody();
//        System.out.println("User: " + user2);
//
//        System.out.println("Method 4: Using execute()");
//        // Method 4: Using execute()
//        RequestCallback requestCallback = request -> {
//            // You can customize the request if needed
//        };
//        ResponseExtractor<ResponseEntity<User>> responseExtractor = restTemplate.responseEntityExtractor(User.class);
//
//        ResponseEntity<User> responseEntity3 = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor, id);
//        User user3 = responseEntity3.getBody();
//        System.out.println("User: " + user3);

        return user;
    }
//
    // Post using RestTemplate
    @PostMapping("/api/post/all-users_v1")
    public User postRestTemplate() {
        // Using postForObject()
        User newUser = new User(0,"Abdul", "abdul@gmail.com", "abdul", Role.ADMIN);
        User createdUser = restTemplate.postForObject(url, newUser, User.class);
        System.out.println("User: " + createdUser);

        // User log Slf4j to store the input into Log instead of local --> easy to see again (not disappear or delete)
        log.info("Try: " + createdUser);

        // Using postForLocation()
//        URI location = restTemplate.postForLocation(url, newUser, User.class);
//        System.out.println("Location: " + location);

        // Using postForEntity()
//        ResponseEntity<User> responseEntity = restTemplate.postForEntity(url, newUser, User.class);
//        System.out.println("HTTP Status: " + responseEntity.getStatusCode());
//        System.out.println("Response Body: " + responseEntity.getBody());
//
//        HttpHeaders headers = responseEntity.getHeaders();
//        System.out.println("Response Headers: " + headers);

        throw new NotFoundException("POST SUCCESSFUL!");
    }
//
    // Put using Rest Template
    @PutMapping("/api/all-users_v1")
    public User putRestTemplate() {
        String putUrl = url + "/{id}";

        User user = new User(0, "Kassim", "kassim@gmail.com", "kassim", Role.ADMIN);
        restTemplate.put(putUrl, user, Map.of("id", "18"));

        throw new NotFoundException("PUT SUCCESSFUL!");
    }

    // Delete using Rest Template
    @DeleteMapping("/api/all-users_v1")
    public HttpStatus deleteRestTemplate() {
        String deleteUrl = url + "/{id}";

        restTemplate.delete(deleteUrl, Map.of("id", "18"));
        throw new NotFoundException("Delete successful");
    }

}

