package com.kenzie.appserver.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kenzie.appserver.repositories.model.EventRecord;

import java.util.Optional;


public class CacheDAO{
    CacheClient cacheClient;



    Gson gson = new Gson();
    public CacheDAO(){
        cacheClient = new CacheClient();
    }

    private EventRecord fromJson(String json) {
        return gson.fromJson(json, new TypeToken<EventRecord>() { }.getType());
    }

    public void addRecord(EventRecord eventRecord){
        cacheClient.setValue(eventRecord.getEventID(), 10000, gson.toJson(eventRecord));
    }

    public EventRecord getRecord(String id){
        Optional<String> temp = cacheClient.getValue(id);
        if(temp.isPresent()){
            return fromJson(temp.get());
        }
        return null;
    }

    public void deleteRecord(String id){
        cacheClient.invalidate(id);
    }

}
