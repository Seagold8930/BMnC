package com.example.bmc.validationTests;

import com.example.bmc.validate.Validate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidUsernameTest {
    private Validate validate;
    private String[] myInput = { "D.Mota001", "d.mota001", "John.Doe001", "john.doe001", "Jane.Smith23",
            "jane.smith23", "Joe.Bloggs992", "joe.bloggs992", "Logan.M1", "logan.m1" };

    @Before
    public void setUp(){
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
            Assert.assertEquals(0, validate.validateUsername( s ) );
        }
    }
}
