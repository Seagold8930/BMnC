package com.example.bmc.auxiliary;

public class ComplianceInspection {
    private int buildingID;
    private String date;
    private String finding;
    private String description;
    private String status;
    private ComplianceImage image;

    public ComplianceInspection() {
        this.buildingID = -1;
        this.date = null;
        this.finding = null;
        this.description = null;
        this.status = null;
        this.image = null;
    }

    public ComplianceInspection( ComplianceImage image ) {
        this.buildingID = -1;
        this.date = null;
        this.finding = null;
        this.description = null;
        this.status = null;
        this.image = image;
    }

    public ComplianceInspection( int buildingID, String date, String finding, String description, String status, ComplianceImage image ) {
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

    public void setBuildingID( int buildingID ) {
        this.buildingID = buildingID;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public String getFinding() {
        return finding;
    }

    public void setFinding( String finding ) {
        this.finding = finding;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public ComplianceImage getImage() {
        return image;
    }

    public void setImage( ComplianceImage image ) {
        this.image = image;
    }

    public boolean addComplianceInspectionData( int buildingID, String date, String finding, String description, String status ) {
        try {
            setBuildingID( buildingID );
            setDate( date );
            setFinding( finding );
            setDescription( description );
            setStatus( status );
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }
}
