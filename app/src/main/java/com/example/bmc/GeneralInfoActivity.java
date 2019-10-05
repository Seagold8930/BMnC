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
package com.example.bmc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GeneralInfoActivity extends AppCompatActivity {
    private Building building;
    private User user;
    private LinkedHashMap< String,String > buildingInfo = new LinkedHashMap<>();
    private ListView listView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        createActivity();
    }

    private void createActivity() {
        setContentView( R.layout.activity_general_info );
        Toolbar toolbar = findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white_24dp );
        setSupportActionBar( toolbar );

        building = ( Building ) getIntent().getSerializableExtra( "MyBuilding" );
        user = ( User ) getIntent().getSerializableExtra( "User" );

        listView = findViewById( R.id.lv_general_info );
        populate( building );
        show( buildingInfo );
        myFAB();
    }

    private void populate( Building building ) {
        buildingInfo.put( "Building Name", building.getBuildingName() );
        buildingInfo.put( "Address", building.getBuildingAddress() );
        buildingInfo.put( "Location", building.getBuildingLocation() );
        buildingInfo.put( "Year Built", String.valueOf( building.getBuildingYearBuilt() ) );
    }

    private void show( HashMap< String, String > buildingInfo ) {
        listView.setAdapter( new GeneralInfoAdapter( buildingInfo ) );
    }

    private void myFAB() {
        floatingActionButton = findViewById( R.id.fab );
        floatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( checkPermissions() ) {
                    displayPermissionAlert();
                } else {
                    Intent intent = new Intent( getApplicationContext(),
                            ComplianceInspectionActivity.class );
                    intent.putExtra( "User", user );
                    intent.putExtra( "Buildings", getIntent().
                            getSerializableExtra( "Buildings" ) );
                    intent.putExtra( "MyBuilding", building );
                    startActivity( intent );
                }
            }
        } );
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission( getApplicationContext(),
                Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission( getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission( getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED;
    }

    private void displayPermissionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder( GeneralInfoActivity.this );
        builder.setTitle( "Alert" );
        builder.setMessage("Camera and Storage permissions are required for proper app " +
                "functionality.");
        builder.setCancelable( false );

        builder.setPositiveButton(
                "EXIT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        logout();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {
            case R.id.action_logout :
                logout();
                return true;
            case R.id.action_dashboard :
                openDashboard();
                return true;
        }

        return false;
    }

    private void logout() {
        Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( intent );
    }

    private void openDashboard() {
        Intent intent = new Intent( getApplicationContext(), DashboardActivity.class );
        ArrayList<Building> buildings = new ArrayList<>();
        buildings = (ArrayList< Building >)getIntent().getExtras().getSerializable( "Buildings" );
        user = ( User )getIntent().getExtras().getSerializable( "User" );
        intent.putExtra( "Buildings", buildings );
        intent.putExtra( "User", user );
        startActivity( intent );
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
