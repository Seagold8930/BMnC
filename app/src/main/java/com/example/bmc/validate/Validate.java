package com.example.bmc.validate;

public class Validate {

    public int validateUsername(String input) {
        if( input.isEmpty() )
            return 4;
        return -1;
    }

    public int validatePassword(String input) {
        return -1;
    }
}
