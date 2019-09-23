package com.example.bmc.dbTests;

import com.example.bmc.auxiliary.User;
import com.example.bmc.auxiliary.UserCredentials;
import com.example.bmc.db.DB_Handler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
    private DB_Handler handler;
    private UserCredentials userCredentials;
    private UserCredentials otherUserCredentials;

    @Before
    public void setUp() {
        handler = new DB_Handler();
        userCredentials = new UserCredentials( "John.Doe001", "John.Doe001" );
        otherUserCredentials = new UserCredentials( "Jane.Smith001", "MyPass1000" );
    }

    @After
    public void tearDown() {
        handler = null;
        userCredentials = null;
    }

    @Test
    public void login_test() {
        User returned = handler.login( userCredentials.getUsername(), userCredentials.getPassword() );
        Assert.assertNotNull( returned );
        Assert.assertEquals( userCredentials.getUsername(), returned.getUsername() );

        handler = new DB_Handler();
        returned = handler.login( otherUserCredentials.getUsername(), otherUserCredentials.getPassword() );
        Assert.assertNotNull( returned );
        Assert.assertEquals( otherUserCredentials.getUsername(), returned.getUsername() );
    }
}
