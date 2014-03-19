package com.blakgeek.hazelcast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * User: Carlos Lawton
 * Date: 3/17/14
 * Time: 5:10 AM
 */
public class JvmInfoNode {

    public static void main(String[] args) throws Exception {

        ObjectWriter jsonWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
        RuntimeMXBean runtimeMXB = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memoryMXB = ManagementFactory.getMemoryMXBean();
        OperatingSystemMXBean osMXB = ManagementFactory.getOperatingSystemMXBean();
        System.out.println(memoryMXB.getHeapMemoryUsage().getUsed());
        System.out.println(osMXB.getName());
        System.out.println(osMXB.getVersion());
        System.out.println(osMXB.getAvailableProcessors());
        System.out.println(osMXB.getArch());
        System.out.println(jsonWriter.writeValueAsString(runtimeMXB.getSystemProperties()));
    }
}
