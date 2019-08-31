package com.example.bmc.validate;

public class Validate {

    public int validateUsername(String input) {
        if( input.isEmpty() )
            return 4;
        else if( input.contains( " " ) )
            return 3;
        else if ( input.length() < 3 )
            return 2;
        else if ( ! input.matches( "[a-zA-Z]+[.][a-zA-Z]+[0-9]+" ) )
            return 1;
        else if ( input.matches( "[a-zA-Z]+[.][a-zA-Z]+[0-9]+" ) )
            return 0;

        return -1;
    }

    public int validatePassword(String input) {
        if ( input.isEmpty() )
            return 4;

        return -1;
    }
}
