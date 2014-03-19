package com.blakgeek.hazelcast;

/**
 * User: Carlos Lawton
 * Date: 3/16/14
 * Time: 5:13 PM
 */
public class ListenerNode implements MessageListener<Word> {

    public static void main(String[] args) {

        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        ITopic<Word> topic = instance.getTopic("words");
        topic.addMessageListener(new ListenerNode());
    }

    @Override
    public void onMessage(Message<Word> message) {

        System.out.println(message.getMessageObject().getValue());
    }
}
