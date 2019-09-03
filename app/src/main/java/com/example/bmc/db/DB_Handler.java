package com.example.bmc.db;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


public class DB_Handler {
    private Connection conn = null;

    public DB_Handler() {
        String connectionString = "jdbc:sqlserver://bmcs.database.windows.net:1433;database=Building Management and Compliance;user=bmcs_admin;password=Weltec2019;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        String username = "bmcs_admin";
        String password = "Weltec2019";

        try {
            conn = DriverManager.getConnection( connectionString );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password ) {
        //TODO implement user validation logic
        try {
            PreparedStatement statement = conn.prepareStatement( "SELECT * FROM [dbo].[User] WHERE userID = ?" );
            statement.setString( 1, username );
            statement.execute();

//            Statement statement = conn.createStatement();
//            statement.execute( "SELECT * FROM User WHERE user.userID = ?" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
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
