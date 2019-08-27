package com.example.bmc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class ComplianceInspectionActivity extends AppCompatActivity {
    private ImageView imageView;
    private static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compliance_inspection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById( R.id.compliance_image );

        if( savedInstanceState == null )
            takePicture();
        else
            imageView.setImageBitmap( bitmap );

        FloatingActionButton fab = findViewById(R.id.submit_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void takePicture() {
        startActivityForResult( new Intent( MediaStore.ACTION_IMAGE_CAPTURE ), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            bitmap = (Bitmap) data.getExtras().get( "data" );
            imageView.setImageBitmap( bitmap );
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

    }
}
