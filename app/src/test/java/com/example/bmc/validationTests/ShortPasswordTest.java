package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShortPasswordTest {
    private Validate validate;
    private String myInput;

    @Before
    public void setUp() {
        validate = new Validate();
        myInput = "short";
    }

    @After
    public void tearDown() {
        validate = null;
        myInput = null;
    }

    @Test
    public void input_validator_short_input() {
        Assert.assertEquals( 2, validate.validatePassword( myInput ) );
    }
}
