package com.justice;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.justice.services.GetProperties;
import com.justice.services.WebSocketListeners;


public class ExampleClient {
    private static final GetProperties properties = new GetProperties();

    public static void main(String[] args) throws URISyntaxException, IOException {
        WebSocketListeners client = new WebSocketListeners(new URI(properties.getProperty("WSS_URL")));
        client.connect();
    }
}
