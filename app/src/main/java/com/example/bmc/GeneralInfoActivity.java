package com.example.bmc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bmc.dummy.Building;

import java.util.ArrayList;
import java.util.List;

public class GeneralInfoActivity extends AppCompatActivity {
    private Building building;
    private List<String> buildingInfo = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        building = (Building) getIntent().getSerializableExtra( "MyBuilding" );
        populate( building );
        listView = findViewById( R.id.lv_general_info );
        listView.setAdapter( new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, buildingInfo ) );
    }

    private void populate( Building building ) {
        Log.i( "Name", building.getName() );
        buildingInfo.add( building.getName() );

        Log.i( "Address", building.getAddress() );
        buildingInfo.add( building.getAddress() );

        Log.i( "Location", building.getLocation() );
        buildingInfo.add( building.getLocation() );

        Log.i( "Year", String.valueOf(building.getYearBuilt()));
        buildingInfo.add( String.valueOf( building.getYearBuilt() ) );
    }

}
