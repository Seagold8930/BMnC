package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpacePasswordTest {
    private Validate validate;
    private String[] myInput = { "D .Mota 001", " John.Doe001", "John .Doe001", "John. Doe001",
            "John.Doe001 ", " J o h n . D o e 0 0 1 ", "My Password 100", " 1 23 456 7",
            "Pass word pass ", "AJLhdaU9 8q3HDi!&(^" };


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
            Assert.assertEquals( 3, validate.validatePassword( s ) );
        }
    }
}
