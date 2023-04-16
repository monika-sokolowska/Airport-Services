package com.example.application.server.service;

import java.util.LinkedList;
import java.util.Queue;

public abstract class MessageQueue<T> {
    protected final Queue<T> messageQueue;

    public MessageQueue() {
        messageQueue = new LinkedList<>();
    }

    public T getMessage() {
        if(messageQueue.isEmpty()) {
            return null;
        }

        return messageQueue.poll();
    }
}
