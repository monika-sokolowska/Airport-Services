package com.example.application.infrastructure;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;



public class PilotClient {
    public void post() {
        HttpResponse<String> response;
        try {
            Unirest.setTimeouts(0, 0);
            response = Unirest.post("http://localhost:8080/pilot/landed/123")
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);

    }
}
