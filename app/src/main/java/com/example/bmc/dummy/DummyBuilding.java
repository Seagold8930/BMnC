package com.example.bmc.dummy;

import java.util.ArrayList;
import java.util.List;

public class DummyBuilding {
    private static int[] buildingID = { 001, 002, 003, 004, 005, 006, 007, 010, 011, 012 };
    private static String[] buildingName = {"Building 1 Name", "Building 2 Name", "Building 3 Name", "Building 4 Name", "Building 5 Name", "Building 6 Name", "Building 7 Name", "Building 8 Name", "Building 9 Name", "Building 10 Name"};
    private static String[] buildingAddress = { "Building 1 Address", "Building 2 Address", "Building 3 Address", "Building 4 Address", "Building 5 Address", "Building 6 Address", "Building 7 Address", "Building 8 Address", "Building 9 Address", "Building 10 Address" };
    private static String[] buildingLocation = { "Building 1 Location", "Building 2 Location", "Building 3 Location", "Building 4 Location", "Building 5 Location", "Building 6 Location", "Building 7 Location", "Building 8 Location", "Building 9 Location", "Building 10 Location" };
    private static int[] buildingYearBuilt = { 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000 };

    public static List<Building> getDummyData() {
        List<Building> buildingList = new ArrayList<>();

        for( int i = 0; i < buildingID.length; i++ ) {
            buildingList.add( new Building( buildingID[i], buildingName[i], buildingAddress[i], buildingLocation[i], buildingYearBuilt[i] ) );
        }

        return buildingList;
    }
}
