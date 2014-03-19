package com.blakgeek.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.*;

/**
 * User: Carlos Lawton
 * Date: 3/19/14
 * Time: 10:02 AM
 */
public class Contender implements MembershipListener {

    private final HazelcastInstance hz;
    private String master;
    private int quorum;

    public Contender(int quorum) {

        this.quorum = quorum;
        Config config = new Config();
        config.getGroupConfig().setName("battlefield");

        hz = Hazelcast.newHazelcastInstance();
        Cluster cluster = hz.getCluster();
        cluster.addMembershipListener(this);
        System.out.println(String.format("Hello my name is %s", cluster.getLocalMember().getUuid()));
        if(cluster.getMembers().size() >= quorum) {
            master = cluster.getMembers().toArray(new Member[]{})[0].getUuid();
            System.out.println(String.format("And %s is the master.", master));;
        }
    }

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {

        Member member = membershipEvent.getMember();
        Member[] members = membershipEvent.getMembers().toArray(new Member[]{});
        System.out.println(String.format("%s has entered the arena.", member.getUuid()));
        System.out.println(String.format("That brings the total to %d", members.length));
        if (members.length >= quorum) {

            if (hz.getCluster().getLocalMember().getUuid().equals(members[0].getUuid())) {
                System.out.println("I'm the master!");
            } else if (members[0].getUuid().equals(master)) {
                System.out.println(String.format("%s is still the master", master));
            } else {
                System.out.println(String.format("%s has taken over from %s as the master", members[0].getUuid(), master));
            }
            master = members[0].getUuid();
        } else {
            System.out.println("Alas there are not enough contenders for fair battle.");
        }
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        Member member = membershipEvent.getMember();
        Member[] members = membershipEvent.getMembers().toArray(new Member[]{});
        System.out.println(String.format("%s has left the arena.", member.getUuid()));
        System.out.println(String.format("That brings the total to %d", members.length));
        if (members.length >= quorum) {

            if (hz.getCluster().getLocalMember().getUuid().equals(members[0].getUuid())) {
                System.out.println("I'm the master!");
            } else if (members[0].getUuid().equals(master)) {
                System.out.println(String.format("%s is still the master", master));
            } else {
                System.out.println(String.format("%s has taken over from %s as the master", members[0].getUuid(), master));
            }
            master = members[0].getUuid();
        } else {
            System.out.println("Alas there are not enough contenders for fair battle.");
        }
    }

    @Override
    public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {

    }

    public static void main(String[] args) {

        if(args.length == 2) {
            new Contender(args[0], args[1]);
        } else {
            usage();
        }
    }

    private static void usage() {
        System.out.println("");
    }
}
