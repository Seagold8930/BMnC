package com.example.bmc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
    private File file;
    private User user;
    private Building building;
    private String imageName;
    private ImageView imageView;
    private static Uri uri;
    private static Bitmap bitmap;
    private View mUpCIFormView;
    private View mProgressView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        createActivity();
        myEditTexts();
        mySpinner();
        myObjects();

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
//                DB_Handler handler = new DB_Handler();
//                ComplianceInspection inspection = new ComplianceInspection( 1, null, null, null, null, new ComplianceImage( file ) );
//                User user = new User( "John Doe", "John.Doe001" );
//                handler.uploadComplianceInspection( inspection, user );
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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, spinnerList );
        spinner.setAdapter( arrayAdapter );
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ( spinner.getSelectedItem().toString().equals( spinnerList.get( 0 ) ) ) {
                    ( ( TextView ) parent.getChildAt(0 ) ).setTextColor( getResources().getColor( android.R.color.holo_red_dark ) );
                } else {
                    ( ( TextView ) parent.getChildAt(0 ) ).setTextColor( getResources().getColor( R.color.colorAccent ) );
                }

                statusSelection = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        Intent photo = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
//        String filepath = "file:///sdcard/";
        imageName = String.format( "%s%s%s", "BM&C_", new SimpleDateFormat("ddMMyyhhmmss").format( new Date() ), ".jpg" );
//        Uri uri = Uri.parse( filepath + imageName );
//        photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult( photo, 0 );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );
        if ( resultCode == RESULT_OK ) {
            try {
//                file = new File( Environment.getExternalStorageDirectory().getPath(), imageName );
//                Uri uri = Uri.fromFile( file );
//                uri = data.getData();
//                bitmap = MediaStore.Images.Media.getBitmap( this.getContentResolver(), uri );
                bitmap = ( Bitmap ) data.getExtras().get( "data" );

//                ExifInterface exif = new ExifInterface( uri.getPath() );
//                int orientation = exif.getAttributeInt( ExifInterface.TAG_ORIENTATION, 1 );
//                Matrix matrix = new Matrix();
//
//                if ( orientation == 6 ) {
//                    matrix.postRotate( 90 );
//                } else if ( orientation == 3 ) {
//                    matrix.postRotate( 180 );
//                } else if ( orientation == 8 ) {
//                    matrix.postRotate( 270 );
//                }
//
//                bitmap = Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true );
                imageView.setImageBitmap( bitmap );
                MediaStore.Images.Media.insertImage( getContentResolver(), bitmap, imageName, null );

            } catch ( Exception e ) {
                e.printStackTrace();
                finish();
            }
        } else {
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
            showProgress( true );
            ComplianceInspection inspection = new ComplianceInspection(building.getBuildingID(),
                    date.getText().toString(), finding.getText().toString(),
                    description.getText().toString(), statusSelection, encodeImage(),
                    user.getName(), new Date().toString(), user.getName(),
                    new Date().toString(), "Open");

            new Firebase_Helper().addComplianceInspection( inspection, new Firebase_Helper.DataStatus() {
                @Override
                public void dataIsInserted() {
                    showProgress( false );
                    Toast.makeText(getApplicationContext(), "Compliance Inspection upload successful.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } );
        }
    }

    private String encodeImage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, outputStream );
        String encodedImage = Base64.encodeToString( outputStream.toByteArray(), Base64.DEFAULT );
        return encodedImage;
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
        ArrayList<Building> buildings = new ArrayList<>();
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
