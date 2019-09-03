package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvalidPasswordTest {
    private Validate validate;
    private String[] myInput = { "d///.mota///001", "johndoe001", "johnndoe", "john.doe",
            "johndoe.001", "!#SK.DOE0", "!#SK.DOE", "JOHN_DOE001", "john-doe001", "123456789" };

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
            Assert.assertEquals( 1, validate.validatePassword( s ) );
        }
    }

}
