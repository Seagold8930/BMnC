package com.example.bmc.dbTests;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.User;
import com.example.bmc.db.DB_Handler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetBuildingTest {
    private DB_Handler handler;
    private User user;
    private List<Building> buildings;
    private String[] buildingNames;

    @Before
    public void setUp() {
        handler = new DB_Handler();
        user = new User( "John Doe", "John.Doe001" );
        buildings = new ArrayList<>();
        buildingNames = new String[]{ "BM&C Test Building 1 Name", "BM&C Test Building 3 Name",
                "BM&C Test Building 5 Name", "BM&C Test Building 7 Name", "BM&C Test Building 9 Name" };
    }

    @After
    public void tearDown() {
        handler = null;
    }

    @Test
    public void get_building_test() {
        buildings = handler.getBuildingName( user );
        Assert.assertNotNull( buildings );
    }

    @Test
    public void check_building_value() {
        int i = 0;

        for ( Building b : buildings ) {
            Assert.assertEquals( buildingNames[ i ], b.getBuildingName() );
            i++;
        }
    }
}
