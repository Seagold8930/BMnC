package com.example.bmc.db;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.ComplianceImage;
import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB_Handler {
    private Connection conn = null;
    private PreparedStatement statement;
    private ResultSet set;
    private List<Building> buildings = new ArrayList<>();

    public DB_Handler() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionString = "jdbc:sqlserver://bmcs.database.windows.net:1433;database=Building Management and Compliance;user=bmcs_admin;password=Weltec2019;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            String username = "bmcs_admin";
            String password = "Weltec2019";
            conn = DriverManager.getConnection( connectionString );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    public boolean login(String username, String password ) {
        String db_user = null;
        String db_pass = null;

        try {
            statement = conn.prepareStatement( "SELECT * FROM [dbo].[User] WHERE userID = ?" );
            statement.setString( 1, username );
            set = statement.executeQuery();

            while ( set.next() ) {
                db_user = set.getString( "userID" );
                db_pass = set.getString( "password" );
            }

            if ( db_user != null && db_pass != null ) {
                if ( db_user.equalsIgnoreCase( username ) && db_pass.equals( password ) ) {
                    //TODO return true
                }
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            try {
                set.close();
                statement.close();
                conn.close();
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updatePassword( String newPassword, User user ) {

        try {
            conn.setAutoCommit( false );
            statement = conn.prepareStatement( "UPDATE [dbo].[User] SET password = ? WHERE " +
                    "lower( userID ) = ?" );
            statement.setString( 1, newPassword );
            statement.setString( 2, user.getUsername().toLowerCase() );

            statement.execute();
            conn.commit();

            //TODO return true
        } catch ( SQLException e ) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch ( SQLException e1 ) {
                e1.printStackTrace();
            }
        } finally {
            try {
                statement.close();
                conn.setAutoCommit( true );
                conn.close();
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public List<Building> getBuildingName(User user) {
        List<String> buildingIDs = new ArrayList<>();

        try {
            statement = conn.prepareStatement( "SELECT * FROM [dbo].[User_Building] WHERE " +
                    "lower( userID ) = ?" );
            statement.setString( 1, user.getUsername().toLowerCase() );

            set = statement.executeQuery();

            while ( set.next() ) {
                buildingIDs.add( set.getString( "buildingID" ) );
            }

            statement = null;
            set = null;

            for ( String id : buildingIDs ) {
                statement = conn.prepareStatement( "SELECT * FROM [dbo].[Building_Header] " +
                        "WHERE buildingID = ?" );
                statement.setString( 1, id );
                set = statement.executeQuery();

                while ( set.next() ) {
                    addBuildingToList( new Building( set.getString( "buildingID" ),
                            set.getString( "buildingName" ),
                            set.getString( "address" ),
                            set.getString( "location" ),
                            set.getInt( "yearBuilt" ) ) );
                }

                statement = null;
                set = null;
            }

            //TODO return List building
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void addBuildingToList(Building building) {
        buildings.add( building );
    }

    public boolean uploadComplianceInspection( ComplianceInspection complianceInspection, User user ) {
        Date date = new java.sql.Date( new java.util.Date().getTime() );

        try {
            conn.setAutoCommit( false );
            statement = conn.prepareStatement( "INSERT INTO [dbo].[Compliance_Inspection] " +
                    "( buildingID, inspectionDate, finding, description, inspectionStatus, image," +
                    " createdBy, creationDate ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )" );
            statement.setString( 1, complianceInspection.getBuildingID() );
            statement.setDate( 2, date );
            statement.setString( 3, complianceInspection.getFinding() );
            statement.setString( 4, complianceInspection.getDescription() );
            statement.setString( 5, complianceInspection.getStatus() );
            //TODO fix exception com.microsoft.sqlserver.jdbc.SQLServerException: String or binary data would be truncated.
            statement.setBytes( 6, getBlob( complianceInspection.getImage().getImageFile() ) );
            statement.setString( 7, user.getUsername() );
            statement.setDate( 8, date );

            statement.execute();
            conn.commit();
            //TODO return true
        } catch ( SQLException e ) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch ( SQLException e1 ) {
                e1.printStackTrace();
            }
        } finally {
            try {
                statement.close();
                conn.setAutoCommit( true );
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private byte[] getBlob( File imageFile ) {
        byte[] fileContent = new byte[ ( int ) imageFile.length() ];
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream( imageFile );
            inputStream.read(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileContent;
    }

    public static void main( String[] args ) {
        DB_Handler handler = new DB_Handler();
        User user = new User( "Dan", "Dan.Mota001" );
        handler.getBuildingName( user );
    }
}
