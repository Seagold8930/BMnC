package com.example.bmc.auxiliary;

import java.io.File;

public class ComplianceInspection {
    private int buildingID;
    private String date;
    private String finding;
    private String description;
    private String status;
    private File image;

    public ComplianceInspection(int buildingID, String date, String finding, String description, String status, File image) {
        this.buildingID = buildingID;
        this.date = date;
        this.finding = finding;
        this.description = description;
        this.status = status;
        this.image = image;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFinding() {
        return finding;
    }

    public void setFinding(String finding) {
        this.finding = finding;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
