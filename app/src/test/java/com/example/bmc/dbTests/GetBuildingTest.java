package com.example.bmc.dbTests;

import com.example.bmc.auxiliary.User;
import com.example.bmc.db.DB_Handler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetBuildingTest {
    private DB_Handler handler;
    private User user;

    @Before
    public void setUp() {
        handler = new DB_Handler();
        user = new User( "Dan", "D.Mota001" );
    }

    @After
    public void tearDown() {
        handler = null;
    }

    @Test
    public void get_building_test() {
        Assert.assertNotNull( handler.getBuildingName( user ) );
    }
}
