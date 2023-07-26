package com.example.TaskPP316;

import com.example.TaskPP316.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class Main {
    private String url = "http://94.198.50.185:7081/api/users";
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    static String result = "";

    public Main() {
        String sessionId = getAllUsers();
        headers.set("cookie", sessionId);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.createUser();
        main.updateUser();
        main.deleteUser(3);
        if (result.length() != 18) {
            System.out.println("Error");
        } else {
            System.out.println("Code: " + result);
        }
    }

    public String getAllUsers() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return String.join(";", Objects.requireNonNull(responseEntity.getHeaders().get("set-cookie")));
    }

    public void createUser() {
        User user = new User("James", "Brown", (byte) 54);
        user.setId(3L);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String request = restTemplate.postForEntity(url, entity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }

    public void updateUser() {
        User user = new User("Thomas", "Shelby", (byte) 85);
        user.setId(3L);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String request = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }

    public void deleteUser(@PathVariable long id) {
        HttpEntity<User> entity = new HttpEntity<>(headers);
        String request = restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }

}