//**************************************************************************************************
// Copyright <2019> <DAN MOTA>
//
// Permission to use, copy, modify, and/or distribute this software for any purpose with or without
// fee is hereby granted, provided that the above copyright notice and this permission notice appear
// in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS
// SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
// AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
// NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
// OF THIS SOFTWARE.
//**************************************************************************************************
package com.example.bmc.db;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.User;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DB_Handler implements Serializable {
    private Connection conn = null;
    private PreparedStatement statement;
    private ResultSet set;
    private ArrayList< Building > buildings = new ArrayList<>();

    public DB_Handler() {

        try {
            Class.forName( "net.sourceforge.jtds.jdbc.Driver" );
            String username = "bmcs_admin";
            String password = "Weltec2019";
            String connectionString = "jdbc:jtds:sqlserver://bmcs.database.windows.net:1433/BMnC;" +
                    "encrypt=true;trustServerCertificate=false;hostNameInCertificate=" +
                    "*.database.windows.net;";
            conn = DriverManager.getConnection( connectionString, username, password );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.conn;
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
            statement = conn.prepareStatement( "UPDATE [User] SET [password] = ?, " +
                    "[modifiedBy] = ?, [modifiedDate] = ? WHERE lower( [userID] ) = ?" );
            statement.setString( 1, newPassword );
            statement.setString( 2, user.getUsername() );
            statement.setTimestamp( 3, new Timestamp( Calendar.getInstance().getTimeInMillis() ) );
            statement.setString( 4, user.getUsername().toLowerCase() );

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

    public ArrayList<Building> getBuildingName( User user ) {
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

}
