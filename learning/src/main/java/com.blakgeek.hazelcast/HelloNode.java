package com.blakgeek.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * User: Carlos Lawton
 * Date: 3/16/14
 * Time: 4:55 PM
 */
public class HelloNode {

    public static void main(String[] args) {

        Config config = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        instance.getTopic("words").publish(new com.blakgeek.hazelcast.Word(instance.getName()));
    }
}
