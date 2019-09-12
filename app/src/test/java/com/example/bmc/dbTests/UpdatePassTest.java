package com.example.bmc.dbTests;

import com.example.bmc.auxiliary.User;
import com.example.bmc.auxiliary.UserCredentials;
import com.example.bmc.db.DB_Handler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdatePassTest {
    private DB_Handler handler;
    private User user;

    @Before
    public void setUp() {
        handler = new DB_Handler();
        user = new User( "John Doe", "John.Doe001" );
    }

    @After
    public void tearDown() {
        handler = null;
        user = null;
    }

    @Test
    public void update_password_test() {
        Assert.assertTrue( handler.updatePassword("NewPassword1000", user ) );

        //reverting to default
        handler = new DB_Handler();
        Assert.assertTrue( handler.updatePassword( "John.Doe001", user ) );
    }
}
