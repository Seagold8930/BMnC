package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidUsernameTest {
    private Validate validate;
    private String myInput;

    @Before
    public void setUp(){
        validate = new Validate();
        myInput = "D.Mota001";
    }

    @After
    public void tearDown() {
        validate = null;
        myInput = null;
    }

    @Test
    public void input_validator_valid_input() {
        Assert.assertEquals(0, validate.validateUsername( myInput ) );
    }
}
