package com.example.bmc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.Nullable;

import com.example.bmc.db.Firebase_Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.ComplianceImage;
import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.text.DateFormat.getDateInstance;

public class ComplianceInspectionActivity extends AppCompatActivity {
    private EditText date;
    private EditText finding;
    private EditText description;
    private List<String> spinnerList;
    private File file;
    private User user;
    private Building building;
    private String imageName;
    private ImageView imageView;
    private Bitmap bitmap;

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
        } else
            imageView.setImageBitmap( bitmap );

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
    }

    private String getSystemDate() {
        Date sysDate = new Date();
        SimpleDateFormat format = ( SimpleDateFormat ) getDateInstance();
        return format.format( sysDate );
    }

    private void mySpinner() {
        Spinner spinner = findViewById( R.id.status );
        populateList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( getApplication(), R.layout.spinner_item, spinnerList );
        arrayAdapter.setDropDownViewResource( R.layout.spinner_dropdown_item );
        spinner.setAdapter( arrayAdapter );
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
        String filepath = "file:///sdcard/";
        imageName = String.format( "%s%s%s", "BM&C_", new SimpleDateFormat("ddMMyyhhmmss").format( new Date() ), ".jpg" );
        Uri uri = Uri.parse( filepath + imageName );
        photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult( photo, 0 );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );
        if ( resultCode == RESULT_OK ) {
            try {
                file = new File( Environment.getExternalStorageDirectory().getPath(), imageName );
                Uri uri = Uri.fromFile( file );
                bitmap = MediaStore.Images.Media.getBitmap( this.getContentResolver(), uri );

                ExifInterface exif = new ExifInterface( uri.getPath() );
                int orientation = exif.getAttributeInt( ExifInterface.TAG_ORIENTATION, 1 );
                Matrix matrix = new Matrix();

                if ( orientation == 6 ) {
                    matrix.postRotate( 90 );
                } else if ( orientation == 3 ) {
                    matrix.postRotate( 180 );
                } else if ( orientation == 8 ) {
                    matrix.postRotate( 270 );
                }

                bitmap = Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true );
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
//        if ( mUp != null ) {
//            return;
//        }
//
//        mUp = new UploadTask( user, building, date.getText().toString(), finding.getText().toString(), description.getText().toString(), bitmap );
//        mUp.execute( ( Void ) null );
        ComplianceInspection inspection = new ComplianceInspection( building.getBuildingID(),
                date.getText().toString(), finding.getText().toString(),
                //TODO get inspection status from spinner
                description.getText().toString(), "Open", encodeImage(),
                user.getName(), date.getText().toString(), user.getName(),
                date.getText().toString(), "Open" );

        new Firebase_Helper().addComplianceInspection(inspection, new Firebase_Helper.DataStatus() {
            @Override
            public void dataIsInserted() {
                Snackbar.make( findViewById( R.id.date ), "Compliance inspection uploaded.", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        });
    }

    private String encodeImage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, outputStream );
        String encodedImage = Base64.encodeToString( outputStream.toByteArray(), Base64.DEFAULT );
        return encodedImage;
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
