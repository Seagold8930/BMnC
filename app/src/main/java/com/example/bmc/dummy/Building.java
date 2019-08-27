package com.example.bmc.dummy;

import java.io.Serializable;

public class Building implements Serializable {
    private int buildingID;
    private String buildingName;
    private String buildingAddress;
    private String buildingLocation;
    private int buildingYearBuilt;

    public Building(int i, String s, String buildingAddress, String s1, int i1) {
        this.buildingID = i;
        this.buildingName = s;
        this.buildingAddress = buildingAddress;
        this.buildingLocation = s1;
        this.buildingYearBuilt = i1;
    }

    public String getName() {
        return this.buildingName;
    }

    public String getAddress() {
        return this.buildingAddress;
    }

    public String getLocation() {
        return this.buildingLocation;
    }

    public int getYearBuilt() {
        return this.buildingYearBuilt;
    }
}
