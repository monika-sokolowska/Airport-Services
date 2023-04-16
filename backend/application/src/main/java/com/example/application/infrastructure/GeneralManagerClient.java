package com.example.application.infrastructure;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class GeneralManagerClient {

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    public String get() {
        HttpResponse<String> response;
        try {
            Unirest.setTimeouts(0, 0);
            response = Unirest.get("http://localhost:8080/generalmanager/getmessage?flightNumber=123").asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.getBody());
        return response.getBody();

    }
    public void post(String message, String time, String flightNumber) {
        HttpResponse<String> response;
        String messageToStandManager = ("{\"flightNumber\":" + flightNumber + ", \"message\":\"" + message + "\", \"minutes\":" + time +"}");
        try {
          response = Unirest.post("http://localhost:8080/generalmanager/finished")
                    .header("Content-Type", "application/json")
                    .body(messageToStandManager)
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        System.out.println(messageToStandManager);

    }



}
