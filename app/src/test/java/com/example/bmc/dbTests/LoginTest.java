package com.example.bmc.dbTests;

import com.example.bmc.auxiliary.UserCredentials;
import com.example.bmc.db.DB_Handler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
    private DB_Handler handler;
    private UserCredentials userCredentials;

    @Before
    public void setUp() {
        handler = new DB_Handler();
        userCredentials = new UserCredentials( "D.Mota001", "D.Mota001" );
    }

    @After
    public void tearDown() {
        handler = null;
        userCredentials = null;
    }

    @Test
    public void login_test() {
        Assert.assertTrue( handler.login( userCredentials.getUsername(), userCredentials.getPassword() ) );
    }
}
