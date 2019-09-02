package com.example.bmc.db;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;

import java.util.List;

public class DB_Handler {

    public boolean login( String username, String password ) {
        //TODO implement user validation logic
        return false;
    }

    public boolean updatePassword( String newPassword, User user ) {
        //TODO implement update db logic
        return false;
    }

    public List<Building> getBuildingName(User user) {
        //TODO implement db logic
        return null;
    }

    public boolean uploadComplianceInspection(ComplianceInspection complianceInspection) {
        //TODO implement db logic
        return false;
    }
}
