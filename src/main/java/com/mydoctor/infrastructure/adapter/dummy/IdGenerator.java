package com.mydoctor.infrastructure.adapter.dummy;

public class IdGenerator {

    public static long generate() {
        // Get the current timestamp in milliseconds
        long timestamp = System.currentTimeMillis();

        // Generate a unique ID by combining timestamp with a random number
        long id = (timestamp << 20) | (Math.round(Math.random() * 1048576));

        return id;
    }
}
