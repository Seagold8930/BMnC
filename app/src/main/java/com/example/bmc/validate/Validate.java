package com.example.bmc.validate;

public class Validate {

    public int validateUsername(String input) {
        if( input.isEmpty() )
            return 4;
        else if( input.contains( " " ) )
            return 3;
        else if ( input.length() < 3 )
            return 2;
        else if ( ! input.matches( "^(?=.*/d)(?=.*[A-Z])(?!.*[a-zA-Z0-9.])(.{3,})$" ) )
            return 1;
        return -1;
    }

    public int validatePassword(String input) {
        return -1;
    }
}
