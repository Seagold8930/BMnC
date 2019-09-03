package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpaceUsernameTest {
    private Validate validate;
    private String[] myInput= { "D .Mota 001", " John.Doe001", "J ohn.Doe001", "Jo hn.Doe001",
            "Joh n.Doe001", "John .Doe001", "John. Doe001", "John.D oe001", "John.Do e001",
            "John.Doe 001", "John.Doe0 01", "John.Doe00 1", "John.Doe001 ", "“ J o h n . D o e 0 0 1 ”" };


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
    public void input_validator_space_input() {
        for ( String s : myInput ) {
            Assert.assertEquals( 3, validate.validateUsername( s ) );
        }
    }
}
