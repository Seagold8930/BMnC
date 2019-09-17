package com.example.bmc.auxiliary;

public class ComplianceInspection {
    private int buildingID;
    private String inspectionDate;
    private String finding;
    private String description;
    private String inspectionStatus;
    private String image;
    private String createdBy;
    private String creationDate;
    private String modifiedBy;
    private String getModifiedDate;
    private String status;

    public ComplianceInspection() {
    }

    public ComplianceInspection(int buildingID, String inspectionDate, String finding,
                                String description, String inspectionStatus, String image,
                                String createdBy, String creationDate, String modifiedBy,
                                String getModifiedDate, String status) {
        this.buildingID = buildingID;
        this.inspectionDate = inspectionDate;
        this.finding = finding;
        this.description = description;
        this.inspectionStatus = inspectionStatus;
        this.image = image;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifiedBy = modifiedBy;
        this.getModifiedDate = getModifiedDate;
        this.status = status;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
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

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getGetModifiedDate() {
        return getModifiedDate;
    }

    public void setGetModifiedDate(String getModifiedDate) {
        this.getModifiedDate = getModifiedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
