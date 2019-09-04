package com.example.bmc.auxiliary;

public class Building {
    private String buildingID;
    private String buildingName;
    private String buildingAddress;
    private String buildingLocation;
    private int buildingYearBuilt;

    public Building(String buildingID, String buildingName, String buildingAddress, String buildingLocation, int buildingYearBuilt) {
        this.buildingID = buildingID;
        this.buildingName = buildingName;
        this.buildingAddress = buildingAddress;
        this.buildingLocation = buildingLocation;
        this.buildingYearBuilt = buildingYearBuilt;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getBuildingLocation() {
        return buildingLocation;
    }

    public void setBuildingLocation(String buildingLocation) {
        this.buildingLocation = buildingLocation;
    }

    public int getBuildingYearBuilt() {
        return buildingYearBuilt;
    }

    public void setBuildingYearBuilt(int buildingYearBuilt) {
        this.buildingYearBuilt = buildingYearBuilt;
    }
}
