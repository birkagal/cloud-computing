package com.birkagal.management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserTests {

    private int port;
    private String url;
    private RestTemplate restTemplate;

    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    @PostConstruct
    public void init() {
        this.url = "http://localhost:" + port + "/users";
        this.restTemplate = new RestTemplate();
    }

    @Test
    void contextLoads() {
    }

}
