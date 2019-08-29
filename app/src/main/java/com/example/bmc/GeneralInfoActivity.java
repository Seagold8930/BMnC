package com.example.bmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.bmc.dummy.Building;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class GeneralInfoActivity extends AppCompatActivity {
    private Building building;
    private LinkedHashMap<String,String> buildingInfo = new LinkedHashMap<>();
    ListView listView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);

        building = (Building) getIntent().getSerializableExtra("MyBuilding");
        populate(building);
        listView = findViewById(R.id.lv_general_info);
        show( buildingInfo );
        myFAB();
    }

    private void populate(Building building) {
        buildingInfo.put( "Building Name", building.getName() );
        buildingInfo.put( "Address", building.getAddress() );
        buildingInfo.put( "Location", building.getLocation() );
        buildingInfo.put( "Year Built", String.valueOf( building.getYearBuilt() ) );
    }

    private void show( HashMap<String, String> buildingInfo ) {
        listView.setAdapter( new GeneralInfoAdapter( buildingInfo ) );
    }

    private void myFAB() {
        floatingActionButton = findViewById( R.id.fab );
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), ComplianceInspectionActivity.class );
                intent.putExtra( "MyBuilding", building );
                startActivity( intent );
            }
        });
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
        startActivity( new Intent( getApplicationContext(), DashboardActivity.class ) );
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
