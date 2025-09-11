package com.albertomorini.drivintext;

import java.util.stream.Stream;

public class Contact {
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber.replaceAll(" ","");
    }

    // parseToString returns Stream<String>
    public Stream<String> parseToString() {
        return Stream.of(name+" ("+phoneNumber+")");
    }
}
