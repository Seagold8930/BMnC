package com.example.bmc.db;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.ComplianceImage;
import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DB_Handler implements Serializable {
    private Connection conn = null;
    private PreparedStatement statement;
    private ResultSet set;
    private List<Building> buildings = new ArrayList<>();

    public DB_Handler() {

        try {
//            Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver" );

            Class.forName( "net.sourceforge.jtds.jdbc.Driver" );
            String username = "bmcs_admin";
            String password = "Weltec2019";
            String connectionString = "jdbc:jtds:sqlserver://bmcs.database.windows.net:1433/BMnC;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;";

//            String connectionString = "jdbc:sqlserver://bmcs.database.windows.net:1433;database=BMnC;sslProtocol=TLSv1.1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

            conn = DriverManager.getConnection( connectionString, username, password );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public User login( String username, String password ) {
        String db_user = null;
        String db_pass = null;
        String db_name_surname = null;

        try {
            statement = conn.prepareStatement( "SELECT * FROM [User] WHERE [userID] = ?" );
            statement.setString( 1, username );
            set = statement.executeQuery();

            while ( set.next() ) {
                db_user = set.getString( "userID" );
                db_pass = set.getString( "password" );
                db_name_surname = set.getString( "name" );
            }

            if ( db_user != null && db_pass != null ) {
                if ( db_user.equalsIgnoreCase( username ) && db_pass.equals( password ) ) {
                    return new User( db_name_surname, db_user );
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
        return null;
    }

    public boolean updatePassword( String newPassword, User user ) {

        try {
            conn.setAutoCommit( false );
            statement = conn.prepareStatement( "UPDATE [User] SET [password] = ? WHERE " +
                    "lower( [userID] ) = ?" );
            statement.setString( 1, newPassword );
            statement.setString( 2, user.getUsername().toLowerCase() );

            statement.execute();
            conn.commit();

            statement.close();
            conn.setAutoCommit( true );
            conn.close();

            return true;
        } catch ( SQLException e ) {
            e.printStackTrace();
            try {
                conn.rollback();
                conn.setAutoCommit( true );
            } catch ( SQLException e1 ) {
                e1.printStackTrace();
            }
        }

        return false;
    }

    public List<Building> getBuildingName( User user ) {
        List<String> buildingIDs = new ArrayList<>();

        try {
            statement = conn.prepareStatement( "SELECT * FROM [User_Building] WHERE " +
                    "lower( [userID] ) = ?" );
            statement.setString( 1, user.getUsername().toLowerCase() );

            set = statement.executeQuery();

            while ( set.next() ) {
                buildingIDs.add( set.getString( "buildingID" ) );
            }

            for ( String id : buildingIDs ) {
                statement = conn.prepareStatement( "SELECT * FROM [Building_Header] " +
                        "WHERE [buildingID] = ?" );
                statement.setString( 1, id );
                set = statement.executeQuery();

                while ( set.next() ) {
                    addBuildingToList( new Building( set.getInt( "buildingID" ),
                            set.getString( "buildingName" ),
                            set.getString( "address" ),
                            set.getString( "location" ),
                            set.getInt( "yearBuilt" ) ) );
                }
            }

            set.close();
            statement.close();
            conn.close();

            return buildings;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return null;
    }

    private void addBuildingToList( Building building ) {
        buildings.add( building );
    }

    public boolean uploadComplianceInspection( ComplianceInspection complianceInspection, User user ) {
        Date date = new java.sql.Date( new java.util.Date().getTime() );
        long timeNow = Calendar.getInstance().getTimeInMillis();
        Timestamp date_time = new java.sql.Timestamp( timeNow );


        try {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            complianceInspection.getImage().getImageFile().compress( Bitmap.CompressFormat.JPEG, 100, outputStream );
//            String encodedImage = Base64.encodeToString( outputStream.toByteArray(), Base64.DEFAULT );
//            InputStream stream = new FileInputStream( complianceInspection.getImage().getImageFile() );
            conn.setAutoCommit( false );
//            statement = null;
            Statement statement = conn.createStatement();
//            statement.execute( "INSERT INTO [Compliance_Inspection] " +
//                    "( [buildingID], [inspectionDate], [finding], [description], [inspectionStatus]," +
//                    " [image], [createdBy], [creationDate] ) VALUES ( " + complianceInspection.getBuildingID() + ", " +
//                    date + ", " + complianceInspection.getFinding() + ", " + complianceInspection.getDescription() + ", " +
//                    complianceInspection.getStatus() + ", " + encodedImage + ", " + user.getUsername() +
//                    ", " + date_time + " );" );
//            statement = conn.prepareStatement( "INSERT INTO [dbo].[Compliance_Inspection] " +
//                    "( buildingID, inspectionDate, finding, description, inspectionStatus, image," +
//                    " createdBy, creationDate ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )" );

//            if ( statement != null ) {
//                statement.setInt( 1, complianceInspection.getBuildingID() );
//                statement.setDate( 2, date );
//                statement.setString( 3, complianceInspection.getFinding() );
//                statement.setString( 4, complianceInspection.getDescription() );
//                statement.setString( 5, complianceInspection.getStatus() );
//                statement.setBinaryStream( 6,  stream, (int)complianceInspection.getImage().getImageFile().length() );
//                statement.setString( 7, user.getUsername() );
//                statement.setTimestamp( 8, date_time );
//
//                statement.execute();
                conn.commit();
//            }

//
//            statement = conn.prepareStatement( "UPDATE [Compliance_Inspection] SET [image] = ? " +
//                    "WHERE [buildingID] = ? AND [inspectionDate] = ? AND [creationDate] = ?" );
//            statement.setBinaryStream( 1,  stream, (int)complianceInspection.getImage().getImageFile().length() );
//            statement.setInt( 2, complianceInspection.getBuildingID() );
//            statement.setDate( 3, date );
//            statement.setTimestamp( 4, date_time );
//
//            statement.executeUpdate();
//            conn.commit();

            statement.close();
            conn.setAutoCommit( true );
            conn.close();

            return true;
        } catch ( SQLException e ) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch ( SQLException e1 ) {
                e1.printStackTrace();
            }
        }

        return false;
    }

//    private ByteArrayInputStream getBytesFromFile(File imageFile ) {
//        byte[] fileContent = new byte[ ( int ) imageFile.length() ];
//        FileInputStream inputStream = null;
//
//        try {
//            inputStream = new FileInputStream( imageFile );
//            inputStream.read( fileContent );
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if ( inputStream != null ) {
//                try {
//                    inputStream.close();
//                } catch ( IOException e ) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return new ByteArrayInputStream( fileContent );
//    }

    @SuppressLint("NewApi")
    public static void main(String[] args ) throws IOException {
        File file = new File( "D:\\Users\\danny\\AndroidStudioProjects\\BMC\\app\\src\\main\\res\\drawable\\image_for_upload_test.jpg" );

        Bitmap bitmap = BitmapFactory.decodeFile( file.getAbsolutePath() );
        ComplianceImage img = new ComplianceImage( bitmap );
//        System.out.println( Files.readAllBytes( img.getImageFile().toPath() ).toString() );
//        ComplianceInspection inspection = new ComplianceInspection( 1, null, null, null, null, img );
//        new DB_Handler().uploadComplianceInspection( inspection, new User( "John Doe", "John.Doe001" ) );
    }
}
