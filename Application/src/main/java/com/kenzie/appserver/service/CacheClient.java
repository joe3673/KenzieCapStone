package com.kenzie.appserver.service;
import com.kenzie.appserver.repositories.model.EventRecord;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;
import javax.inject.Inject;

public class CacheClient {



    public CacheClient() {

    }

    public Jedis getPool(){
        String redisUrl = System.getenv("JEDIS_URL");
        if (redisUrl != null && redisUrl.length() > 0) {
            // Connect to AWS
            System.out.println("Providing redis " + redisUrl);
            return new Jedis(redisUrl, 6379, 20000);
        } else if ("true".equals(System.getenv("AWS_SAM_LOCAL"))) {
            // Connect to local Docker redis
            JedisPool pool = new JedisPool(new JedisPoolConfig(), "redis-stack", 6379, 20000);
            try {
                return pool.getResource();
            } catch (Exception e) {
                throw new IllegalStateException("Could not connect to the local redis container in docker.  " +
                        "Make sure that it is running and that you have configured the SAM CLI - Docker Network " +
                        "property to contain kenzie-local inside of your run configuration.");
            }
        } else {
            // Run Locally
            System.out.println("Providing local redis");
            return new JedisPool(new JedisPoolConfig(), "localhost", 6379, 20000).getResource();
        }
    }


    public void setValue(String key, int seconds, String value) {
        // Check for non-null key
        // Set the value in the cache
        if(checkNonNullKey(key)){
            Jedis cache = getPool();
            cache.setex(key, seconds, value);
            cache.close();
        }
    }

    public Optional<String> getValue(String key) {
        // Check for non-null key
        // Retrieves the Optional values from the cache
        if(checkNonNullKey(key)){
            Jedis cache = getPool();
            Optional<String> value = Optional.of(cache.get(key));
            cache.close();
            return value;
        }
        else{
            return Optional.empty();
        }

    }

    public void invalidate(String key) {
        // Check for non-null key
        // Delete the key
        if(checkNonNullKey(key)){
            Jedis cache = getPool();
            cache.del(key);
            cache.close();
        }

    }

    private boolean checkNonNullKey(String key) {
        // Ensure the key isn't null
        // What should you do if the key *is* null?
        if(key == null){
            return false;
        }
        return true;
    }

}
