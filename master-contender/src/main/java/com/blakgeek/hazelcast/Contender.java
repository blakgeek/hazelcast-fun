package com.blakgeek.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.*;

/**
 * User: Carlos Lawton
 * Date: 3/19/14
 * Time: 10:02 AM
 * This is a simple example of testing failover
 * This works based on two guarantees
 * 1. The membership events are always delivered in order.
 * 2. The list of members in the event is always in a consistent order.
 */
public class Contender implements MembershipListener {

    private final HazelcastInstance hz;
    private final String arena;
    private String currentMaster = "The Wind";
    private int quorum;
    private String uuid;

    public Contender(String arena, int quorum) {

        this.arena = arena;
        this.quorum = quorum;
        Config config = new Config();
        config.getGroupConfig().setName(arena);
        hz = Hazelcast.newHazelcastInstance();
        uuid = hz.getCluster().getLocalMember().getUuid();

        Cluster cluster = hz.getCluster();
        cluster.addMembershipListener(this);

        System.out.println(String.format("Hello my name is %s and i am ready to battle in the %s arena.", cluster.getLocalMember().getUuid(), arena));
        // don't attempt to do anything until have the required number of members
        int contenderCnt = cluster.getMembers().size();
        if(contenderCnt >= quorum) {
            currentMaster = cluster.getMembers().toArray(new Member[]{})[0].getUuid();
            System.out.println(String.format("I see the master is %s.", currentMaster));
        } else {
            System.out.println(String.format("There are only %d contender(s) that is not enough for honorable battle.", contenderCnt));
        }
    }

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {

        Member member = membershipEvent.getMember();
        Member[] members = membershipEvent.getMembers().toArray(new Member[]{});
        System.out.println(String.format("%s has submitted to battle in the %s arena.", member.getUuid(), arena));
        System.out.println(String.format("That brings the total to %d", members.length));
        if (members.length >= quorum) {

            // Master selection is simple just choose the first entry in the array of members
            Member newMaster = members[0];
            if (uuid.equals(newMaster.getUuid())) {
                System.out.println("I'm the master!");
            } else if (newMaster.getUuid().equals(currentMaster)) {
                System.out.println(String.format("%s is still the master", currentMaster));
            } else {
                System.out.println(String.format("%s has taken over from %s as the master", newMaster.getUuid(), currentMaster));
            }
            currentMaster = newMaster.getUuid();
        }
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        Member member = membershipEvent.getMember();
        Member[] members = membershipEvent.getMembers().toArray(new Member[]{});
        System.out.println(String.format("%s has been defeated.", member.getUuid()));
        System.out.println(String.format("Leaving only %d contender(s).", members.length));
        if (members.length >= quorum) {

            Member newMaster = members[0];
            if (uuid.equals(newMaster.getUuid())) {
                System.out.println("I'm the master!");
            } else if (newMaster.getUuid().equals(currentMaster)) {
                System.out.println(String.format("%s is still the master", currentMaster));
            } else {
                System.out.println(String.format("%s has taken over from %s as the master", newMaster.getUuid(), currentMaster));
            }
            currentMaster = newMaster.getUuid();
        } else {
            System.out.println("The old master is no more.  And alas there are not enough contenders for a fair battle.");
            currentMaster = null;
        }
    }

    @Override
    public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {

    }

    public static void main(String[] args) {

        if(args.length == 2) {
            new Contender(args[0], Integer.parseInt(args[1]));
        } else {
            System.err.println("java -jar contender.jar <arena name> <minimum contenders>");
            System.err.println("          arena name - determines which cluster the member will connect to");
            System.err.println("          minimum contenders - minimum nodes required to choose a master");
        }
    }
}
