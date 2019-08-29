package com.example.bmc;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.text.DateFormat.getDateInstance;

public class ComplianceInspectionActivity extends AppCompatActivity {
    EditText date;
    EditText finding;
    EditText description;
    private List<String> spinnerList;
    private static File file;
    private static int counter = 0;
    private static String imageName;
    private ImageView imageView;
    private static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compliance_inspection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);

        myEditTexts();
        mySpinner();

        imageView = findViewById(R.id.compliance_image);

        if (savedInstanceState == null) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            takePicture();
        } else
            imageView.setImageBitmap(bitmap);

        FloatingActionButton fab = findViewById(R.id.submit_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void myEditTexts() {
        date = findViewById(R.id.date);
        finding = findViewById(R.id.finding);
        description = findViewById(R.id.description);

        date.setText( getSystemDate() );

        View focusView = finding;
        focusView.requestFocus();
    }

    private String getSystemDate() {
        Date sysDate = new Date();
        SimpleDateFormat format = (SimpleDateFormat) getDateInstance();
        return format.format( sysDate );
    }

    private void mySpinner() {
        Spinner spinner = findViewById( R.id.status );
        populateList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( getApplication(), R.layout.spinner_item, spinnerList );
        arrayAdapter.setDropDownViewResource( R.layout.spinner_dropdown_item );
        spinner.setAdapter( arrayAdapter );
    }

    private void populateList() {
        spinnerList = new ArrayList<>();
        spinnerList.add( "Select Status (Open/Closed)" );
        spinnerList.add( "Open" );
        spinnerList.add( "Closed" );
    }

    private void takePicture() {
        Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String filepath = "file:///sdcard/";
        imageName = "photo" + counter++ + ".jpg";
        Uri uri = Uri.parse(filepath + imageName);
        photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(photo, 0);
//        startActivityForResult( new Intent( MediaStore.ACTION_IMAGE_CAPTURE ), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                file = new File(Environment.getExternalStorageDirectory().getPath(), imageName);
                Uri uri = Uri.fromFile(file);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ExifInterface exif = new ExifInterface(uri.getPath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Matrix matrix = new Matrix();

                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }

                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);

//                if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT )
//                    imageView.setRotation(90);

            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void openDashboard() {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
