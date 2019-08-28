package com.example.bmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);

        building = (Building) getIntent().getSerializableExtra("MyBuilding");
        populate(building);
        listView = findViewById(R.id.lv_general_info);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, buildingInfo));

        FloatingActionButton floatingActionButton = findViewById( R.id.fab );
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), ComplianceInspectionActivity.class );
                intent.putExtra( "MyBuilding", building );
                startActivity( intent );
            }
        });
    }

    private void populate(Building building) {
        buildingInfo.add(building.getName());
        buildingInfo.add(building.getAddress());
        buildingInfo.add(building.getLocation());
        buildingInfo.add(String.valueOf(building.getYearBuilt()));
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
        startActivity( new Intent( getApplicationContext(), Dashboard.class ) );
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
