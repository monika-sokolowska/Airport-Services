package com.example.application.infrastructure;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class StandManagerClient {

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    public String get() {
        HttpResponse<String> response;
        try {
            Unirest.setTimeouts(0, 0);
            response = Unirest.get("http://localhost:8080/standmanager/getmessage?flightNumber=123").asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.getBody());
        return response.getBody();

    }


}
