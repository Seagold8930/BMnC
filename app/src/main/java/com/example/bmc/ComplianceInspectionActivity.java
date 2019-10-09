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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.Nullable;

import com.example.bmc.db.Firebase_Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.text.DateFormat.getDateInstance;

public class ComplianceInspectionActivity extends AppCompatActivity {
    private EditText date;
    private EditText finding;
    private EditText description;
    private Spinner spinner;
    private List<String> spinnerList;
    private String statusSelection;
    private User user;
    private Building building;
    private ImageView imageView;
    private static Bitmap bitmap;
    private View mUpCIFormView;
    private View mProgressView;
    private InsertTask mIn;
    private Firebase_Helper helper;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        createActivity();

        if ( savedInstanceState == null ) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy( builder.build() );
            takePicture();
        } else {
            try {
                imageView.setImageBitmap( bitmap );
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        }
    }

    private void createActivity() {
        setContentView( R.layout.activity_compliance_inspection );
        Toolbar toolbar = findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white_24dp );
        setSupportActionBar( toolbar );
        imageView = findViewById( R.id.compliance_image );

        FloatingActionButton fabSubmit = findViewById( R.id.submit_button );
        fabSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                attemptUpload();
            }
        } );

        FloatingActionButton fabCancel = findViewById( R.id.cancel_button );
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myEditTexts();
        mySpinner();
        myObjects();
    }

    private void myEditTexts() {
        date = findViewById( R.id.date );
        finding = findViewById( R.id.finding );
        description = findViewById( R.id.description );
        date.setText( getSystemDate() );

        View focusView = finding;
        focusView.requestFocus();
        mUpCIFormView = findViewById( R.id.compliance_form );
        mProgressView = findViewById( R.id.upload_progress );
    }

    private String getSystemDate() {
        Date sysDate = new Date();
        SimpleDateFormat format = ( SimpleDateFormat ) getDateInstance();
        return format.format( sysDate );
    }

    private void mySpinner() {
        spinner = findViewById( R.id.status );
        populateList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( this, android.R.layout.
                simple_list_item_1, spinnerList );
        spinner.setAdapter( arrayAdapter );
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
                if ( spinner.getSelectedItem().toString().equals( spinnerList.get( 0 ) ) ) {
                    ( ( TextView ) parent.getChildAt(0 ) ).setTextColor( getResources().
                            getColor( android.R.color.holo_red_dark ) );
                } else {
                    ( ( TextView ) parent.getChildAt(0 ) ).setTextColor( getResources().
                            getColor( R.color.colorAccent ) );
                }

                statusSelection = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {

            }
        } );
    }

    private void myObjects() {
        building = ( Building )getIntent().getExtras().getSerializable( "MyBuilding" );
        user = ( User )getIntent().getExtras().getSerializable( "User" );
    }

    private void populateList() {
        spinnerList = new ArrayList<>();
        spinnerList.add( "Select Status (Open/Closed)" );
        spinnerList.add( "Open" );
        spinnerList.add( "Closed" );
    }

    private void takePicture() {
        Intent cameraIntent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult( cameraIntent, 1 );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if ( requestCode == 1 && resultCode == RESULT_OK ) {
            bitmap = ( Bitmap ) data.getExtras().get( "data" );
            imageView.setImageBitmap( bitmap );
        } else if ( resultCode == RESULT_CANCELED ){
            finish();
        }
    }

    private void attemptUpload() {

        if ( finding.getText().toString().trim().isEmpty() ) {
            finding.setError( getString( R.string.error_field_required ) );
            finding.requestFocus();
        } else if ( description.getText().toString().trim().isEmpty() ) {
            description.setError( getString( R.string.error_field_required ) );
            description.requestFocus();
        } else if ( statusSelection.equals( spinnerList.get( 0 ) ) ) {
            TextView errorText = ( TextView ) spinner.getSelectedView();
            errorText.setError( getString( R.string.error_selection_required ) );
            errorText.requestFocus();
        } else {
            startUploadTask();
        }
    }

    private void startUploadTask() {
        if ( isDeviceConnected() ) {
            showProgress( true );
            ComplianceInspection inspection = new ComplianceInspection(building.getBuildingID(),
                    date.getText().toString(), finding.getText().toString(),
                    description.getText().toString(), statusSelection, encodeImage(),
                    user.getUsername(), new Date().toString(), user.getUsername(),
                    new Date().toString(), "Open");

            mIn = new InsertTask( inspection );
            mIn.execute( ( Void ) null );
        } else {
            Snackbar.make( findViewById( R.id.finding_input ), "Connection failed. Check " +
                    "your network access.", Snackbar.LENGTH_LONG )
                    .setAction( "Action", null ).show();
        }

    }

    private boolean isDeviceConnected() {
        ConnectivityManager manager = ( ConnectivityManager ) this.
                getSystemService( CONNECTIVITY_SERVICE );

        NetworkInfo info = manager.getActiveNetworkInfo();

        if ( info != null && info.isConnected() ) {
            return true;
        }

        return false;
    }

    private String encodeImage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, outputStream );
        return Base64.encodeToString( outputStream.toByteArray(), Base64.DEFAULT );
    }

    @TargetApi( Build.VERSION_CODES.HONEYCOMB_MR2 )
    private void showProgress( final boolean show ) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ) {
            int shortAnimTime = getResources().getInteger( android.R.integer.config_shortAnimTime );

            mUpCIFormView.setVisibility( show ? View.GONE : View.VISIBLE );
            mUpCIFormView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 0 : 1 ).setListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd( Animator animation ) {
                    mUpCIFormView.setVisibility( show ? View.GONE : View.VISIBLE );
                }
            } );

            mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
            mProgressView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 1 : 0 ).setListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd( Animator animation ) {
                    mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
                }
            } );
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
            mUpCIFormView.setVisibility( show ? View.GONE : View.VISIBLE );
        }
    }

    private class InsertTask extends AsyncTask< Void, Void, Boolean > {
        ComplianceInspection inspection;

        public InsertTask( ComplianceInspection inspection ) {
            this.inspection = inspection;
        }

        @Override
        protected Boolean doInBackground( Void... voids ) {
            helper = new Firebase_Helper();

            try {
                helper.addComplianceInspection( inspection, new Firebase_Helper.DataStatus() {
                    @Override
                    public void dataIsInserted() {
                    }
                });

                return true;
            } catch ( Exception e ) {
                return false;
            }
        }

        @Override
        protected void onPostExecute( final Boolean success ) {
            mIn = null;
            showProgress( false );

            if ( success ) {
                Toast.makeText(getApplicationContext(), "Compliance Inspection upload " +
                        "successful.", Toast.LENGTH_SHORT).show();
                    finish();
            } else {
                Snackbar.make( findViewById( R.id.finding_input ), "Compliance Inspection " +
                        "upload failed. Please, try again", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_dashboard:
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
        Bundle bundle = getIntent().getExtras();
        ArrayList<Building> buildings;
        buildings = ( ArrayList< Building > )bundle.getSerializable( "Buildings" );
        user = ( User )bundle.getSerializable( "User" );
        intent.putExtra( "Buildings", buildings );
        intent.putExtra( "User", user );
        startActivity( intent );
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
