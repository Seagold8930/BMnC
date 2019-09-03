package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShortPasswordTest {
    private Validate validate;
    private String[] myInput = { "D", "short", "123456", "AbCDeF", "!$Whsa0", "_.-=", "5#$97", "0" };

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
    public void input_validator_short_input() {
        for ( String s : myInput ) {
            Assert.assertEquals( 2, validate.validatePassword( s ) );
        }
    }
}
