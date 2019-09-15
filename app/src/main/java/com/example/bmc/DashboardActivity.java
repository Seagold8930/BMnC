package com.example.bmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.User;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private ArrayList<Building> buildings = new ArrayList<>();
    private List<String> buildingNames = new ArrayList<>();
    private User user;
    private ListView listView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        createActivity();
    }

    private void createActivity() {
        setContentView( R.layout.activity_dashboard );
        Toolbar toolbar = findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white_24dp );
        setSupportActionBar( toolbar );

        Bundle bundle = getIntent().getExtras();

        buildings = ( ArrayList< Building > )bundle.getSerializable( "Buildings" );
        user = ( User )bundle.getSerializable( "User" );

        listView = findViewById( R.id.lv_building_names );
        populate();
        listView.setAdapter( new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, buildingNames ) );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                Building building = buildings.get( position );
                Intent intent = new Intent( getApplicationContext(), GeneralInfoActivity.class );
                intent.putExtra( "MyBuilding", building );
                intent.putExtra( "Buildings", buildings );
                intent.putExtra( "User", user );
                startActivity( intent );
            }
        } );
    }

    private void populate() {
        for ( Building building : buildings ) {
            buildingNames.add( building.getBuildingName() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.dashboard_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {
            case R.id.action_logout :
                logout();
                return true;
        }

        return false;
    }

    private void logout() {
        Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( intent );
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
