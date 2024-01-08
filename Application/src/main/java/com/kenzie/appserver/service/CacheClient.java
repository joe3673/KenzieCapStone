package com.kenzie.appserver.service;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

public class CacheClient {

    private class Cache{

        ConcurrentHashMap<String, String> cacheMap;
        ConcurrentHashMap<String, LocalDateTime> expiryMap;

        int timeInCacheBySeconds;
        public Cache(){
            cacheMap = new ConcurrentHashMap<>();
            expiryMap = new ConcurrentHashMap<>();
            timeInCacheBySeconds = 1000;
        }

        public Cache(int seconds){
            cacheMap = new ConcurrentHashMap<>();
            expiryMap = new ConcurrentHashMap<>();
            timeInCacheBySeconds = seconds;
        }

        public void setValue(String key, String value){
            cacheMap.put(key, value);
            expiryMap.put(key, LocalDateTime.now());
        }

        public Optional<String> getValue(String key){
            return Optional.of(cacheMap.get(key));
        }

        public void invalidate(String key){
            cacheMap.remove(key);
            expiryMap.remove(key);
        }

        //Would be called periodically in a real application.
        public void cleanup(){
            Iterator<Map.Entry<String, LocalDateTime>> iterator = expiryMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, LocalDateTime> entry = iterator.next();
                if(LocalDateTime.now().isAfter(entry.getValue().plusSeconds(timeInCacheBySeconds))){
                    iterator.remove();
                    cacheMap.remove(entry.getKey());
                }
            }
        }

    }
    private final JedisPool pool;

    private Jedis jedi = new Jedis();

    Cache cache = new Cache();

    public CacheClient() {
        this.pool = new JedisPool(new JedisPoolConfig(), System.getenv("JEDIS_URL"), 6379, 20000);
    }




    private void checkForNullKey(String key){
        if(key == null){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method that sets a key-value pair in the cache.
     *
     * PARTICIPANTS: Implement this method.
     *
     * @param key     String used to identify an item in the cache
     * @param seconds The number of seconds during which the item is available
     * @param value   String representing the value set in the cache
     */
    public void setValue(String key, long seconds, String value){
        checkForNullKey(key);
        try {
            jedi.setex(key, seconds, value);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        cache.setValue(key, value);
    }

    /**
     * Method that retrieves a value from the cache.
     *
     * PARTICIPANTS: Replace return null with your implementation of this method.
     *
     * @param key String used to identify the item being retrieved
     * @return String representing the value stored in the cache or an empty Optional in the case of a cache miss.
     */
    public Optional<String> getValue(String key){
        checkForNullKey(key);
        try {
            String value = jedi.get(key);
            if (value == null) {
                return Optional.empty();
            }
            else {
                return Optional.of(value);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return cache.getValue(key);

    }

    /**
     * Method to invalidate an item in the cache. Checks whether the requested key exists before invalidating.
     *
     * PARTICIPANTS: Implement this method.
     *
     * @param key String representing the key to be deleted from the cache
     * @return true on invalidation, false if key does not exist in cache
     */
    public boolean invalidate(String key) {
        checkForNullKey(key);
        try {
            if (jedi.get(key) != null) {
                jedi.del(key);
                pool.close();
                return true;
            }
            return false;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        cache.invalidate(key);
        return true;
    }
}
