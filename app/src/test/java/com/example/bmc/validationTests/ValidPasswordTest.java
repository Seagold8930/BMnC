package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidPasswordTest {
    private Validate validate;
    private String[] myInput = { "D.Mota001", "PassPattern0", "passPattern!0", "some_PaSs9&^%",
            "!#SK.DoE0", "jane.sMith23", "Joe.Bloggs992", "joe.bl0Ggs992", "Logan.M1", "log@N.m1" };

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
    public void input_validator_valid_input() {
        for ( String s : myInput ) {
            Assert.assertEquals( 0, validate.validatePassword( s ) );
        }
    }
}
