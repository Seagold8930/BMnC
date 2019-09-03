package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvalidUsernameTest {
    private Validate validate;
    private String[] myInput = { "D///.Mota///001", "JohnDoe001", "JohnDoe", "John.Doe",
            "JohnDoe.001", "!#Sk.Doe0", "!#Sk.Doe", "John_Doe001", "john-doe001", "123456789" };


    @Before
    public void setUp() {
        validate = new Validate();
    }

    @After
    public void tearDown() {
        validate = null;
        myInput = null;
    }

    @Test
    public void input_validator_invalid_input() {
        for ( String s : myInput ) {
            Assert.assertEquals( 1, validate.validateUsername( s ) );
        }
    }
}
