package com.utcluj.bogdan;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Base64;

public class UserTest {

    @Before
    public void init() {

    }

    @After
    public void destroy() {

    }

    @Test
    public void changeInfoFail() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/resource/user/3");
        String token = "test2@testus.com:testpass";
        request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes()));

        // our current user has the ID 1, the target user has the ID 3
        HttpResponse response = HttpClientBuilder.create().build().execute( request );
        ObjectMapper mapper = new ObjectMapper();
        UserDTO dto = mapper.readValue(response.getEntity().getContent(), UserDTO.class);

        UserDTO result = new UserDTO(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getRole(),
                dto.getGender(),
                dto.getActive(),
                dto.getEnableEmails()
        );

        request = new HttpPut("http://localhost:8080/resource/user");
        request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes()));
        ((HttpPut) request).setEntity(new StringEntity(mapper.writeValueAsString(result)));
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        response = HttpClientBuilder.create().build().execute( request );

        // we are not logged in, so we should get a 403 (forbidden)
        Assert.assertEquals(403, response.getStatusLine().getStatusCode());
    }

    @Test
    public void changeInfoSuccess() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/resource/user/1");
        String token = "test2@testus.com:testpass";
        request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes()));

        // our current user has the ID 1, the target user has the ID 3
        HttpResponse response = HttpClientBuilder.create().build().execute( request );
        ObjectMapper mapper = new ObjectMapper();
        UserDTO dto = mapper.readValue(response.getEntity().getContent(), UserDTO.class);

        UserDTO result = new UserDTO(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getRole(),
                dto.getGender(),
                dto.getActive(),
                dto.getEnableEmails()
        );

        request = new HttpPut("http://localhost:8080/resource/user");
        request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes()));
        ((HttpPut) request).setEntity(new StringEntity(mapper.writeValueAsString(result)));
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        response = HttpClientBuilder.create().build().execute( request );

        // we are not logged in, so we should get a 403 (forbidden)
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    }
}
