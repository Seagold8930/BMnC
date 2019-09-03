package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShortUsernameTest {
    private Validate validate;
    private String[] myInput = { "D", "123", "AbC", "def", "GHI", ".", "._", "0" };

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
            Assert.assertEquals( 2, validate.validateUsername( s ) );
        }
    }
}
