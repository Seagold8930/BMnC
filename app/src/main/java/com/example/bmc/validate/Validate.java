//**************************************************************************************************
// Copyright <2019> <DAN MOTA>
//
// Permission to use, copy, modify, and/or distribute this software for any purpose with or without
// fee is hereby granted, provided that the above copyright notice and this permission notice appear
// in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS
// SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
// AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
// NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
// OF THIS SOFTWARE.
//**************************************************************************************************
package com.example.bmc.validate;

public class Validate {

    /**
     * This method validates a username inputted by the user. The username possesses the following
     * rules: Must not be empty (Error code 4), must not contain whitespaces (Error code 3),
     * must not be shorter than 8 characters (Error code 2), must match the pattern:
     * alpha characters (upper case or lower case) followed by a dot, followed by alpha characters
     * upper case or lower case) followed by digits. i.e. Name.Surname000
     * returns error code 1 if pattern does not match, and error code 0 if it matches
     * @param input
     * @return
     */
    public int validateUsername(String input) {
        if( input.isEmpty() )
            return 4;
        else if( input.contains( " " ) )
            return 3;
        else if ( input.length() < 8 )
            return 2;
        else if ( ! input.matches( "[a-zA-Z]+[.][a-zA-Z]+[0-9]+" ) )
            return 1;
        else if ( input.matches( "[a-zA-Z]+[.][a-zA-Z]+[0-9]+" ) )
            return 0;

        return -1;
    }

    /**
     * This method validates a password inputted by the user. The password possesses the following
     * rules: Must not be empty (Error code 4), must not contain whitespaces (Error code 3),
     * must not be shorter than 8 characters (Error code 2), must match the pattern:
     * at least one upper case letter, at least one lower case letter, at least one digit
     * returns error code 1 if pattern does not match, and error code 0 if it matches
     * @param input
     * @return
     */
    public int validatePassword(String input) {
        if ( input.isEmpty() )
            return 4;
        else if ( input.contains( " " ) )
            return 3;
        else if ( input.length() < 8 )
            return 2;
        else if ( ! input.matches( "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$" ) )
            return 1;
        else if ( input.matches( "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$" ) )
            return 0;

        return -1;
    }
}
