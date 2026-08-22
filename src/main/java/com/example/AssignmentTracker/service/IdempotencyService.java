package com.example.AssignmentTracker.service;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IdempotencyService {

    private final Map<String ,Long> proceedRequests=
            new ConcurrentHashMap<>();

    public boolean isProceed(String key){
        return proceedRequests.containsKey(key);
    }
    public void put(String key,Long ProductId){
        proceedRequests.put(key, ProductId);
    }

    public Long get(String key){
        return proceedRequests.get(key);
    }



}
