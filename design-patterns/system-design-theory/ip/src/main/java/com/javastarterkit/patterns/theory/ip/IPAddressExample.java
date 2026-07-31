package com.javastarterkit.patterns.theory.ip;

/**
 * System Design Theory: IP (Internet Protocol)
 * 
 * Demonstrates IPv4 and IPv6 address representation and basic operations.
 */
public class IPAddressExample {
    private final String address;
    private final int version;

    public IPAddressExample(String address) {
        this.address = address;
        this.version = address.contains(":") ? 6 : 4;
    }

    public int getVersion() {
        return version;
    }

    public String getAddress() {
        return address;
    }

    public boolean isPrivate() {
        if (version == 4) {
            String[] octets = address.split("\\.");
            if (octets.length == 4) {
                int first = Integer.parseInt(octets[0]);
                int second = Integer.parseInt(octets[1]);
                return first == 10 ||
                       (first == 172 && second >= 16 && second <= 31) ||
                       (first == 192 && second == 168);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        IPAddressExample ipv4 = new IPAddressExample("192.168.1.1");
        System.out.println("Address: " + ipv4.getAddress() + 
                         ", Version: IPv" + ipv4.getVersion() + 
                         ", Private: " + ipv4.isPrivate());

        IPAddressExample ipv6 = new IPAddressExample("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
        System.out.println("Address: " + ipv6.getAddress() + 
                         ", Version: IPv" + ipv6.getVersion() + 
                         ", Private: " + ipv6.isPrivate());
    }
}