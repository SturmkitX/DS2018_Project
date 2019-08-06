package com.utcluj.bogdan;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Base64;

public class AccessTest {

    @Before
    public void init() {

    }

    @After
    public void destroy() {

    }

    @Test
    public void testAccessFail() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/resource/channel");
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // we are not logged in, so we should get a 401 (unauthorized)
        Assert.assertEquals(401, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testAccessSuccess() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/resource/channel");
        String token = "test2@testus.com:testpass";
        request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes()));
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // we are logged in, so 200 should be returned
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testInsufficientRights() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/resource/email");
        String token = "test2@testus.com:testpass";
        request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(token.getBytes()));
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // we are not logged in, so we should get a 403 (forbidden)
        Assert.assertEquals(403, response.getStatusLine().getStatusCode());
    }
}
