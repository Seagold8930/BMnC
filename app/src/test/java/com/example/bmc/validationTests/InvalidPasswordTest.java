package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvalidPasswordTest {
    private Validate validate;
    private String myInput;

    @Before
    public void setUp() {
        validate = new Validate();
        myInput = "long-enough-but-wrong-pattern";
    }

    @After
    public void tearDown() {
        validate = null;
        myInput = null;
    }

    @Test
    public void input_validator_invalid_input() {
        Assert.assertEquals( 1, validate.validatePassword( myInput ) );
    }

}
