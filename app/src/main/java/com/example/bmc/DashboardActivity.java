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

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private ArrayList< Building > buildings = new ArrayList<>();
    private List< String > buildingNames = new ArrayList<>();
    private User user;
    private ListView listView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        try {
            createActivity();
        } catch ( Exception e ) {
            e.printStackTrace();
            finish();
        }
    }

    private void createActivity() {
        setContentView( R.layout.activity_dashboard );
        Toolbar toolbar = findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white_24dp );
        setSupportActionBar( toolbar );

        buildings = ( ArrayList< Building > )getIntent().getExtras().getSerializable( "Buildings" );
        user = ( User )getIntent().getExtras().getSerializable( "User" );

        listView = findViewById( R.id.lv_building_names );
        populate();
        listView.setAdapter( new ArrayAdapter<>( this, android.R.layout.simple_list_item_1,
                buildingNames ) );
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
        logout();
        return true;
    }
}
