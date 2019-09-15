package com.example.bmc.auxiliary;

import android.graphics.Bitmap;

import java.io.File;

public class ComplianceImage {
    private Bitmap imageFile;

    public ComplianceImage() {
        this.imageFile = null;
    }

    public ComplianceImage( Bitmap imageFile ) {
        this.imageFile = imageFile;
    }

    public Bitmap getImageFile() {
        return imageFile;
    }

    public void setImageFile( Bitmap imageFile ) {
        this.imageFile = imageFile;
    }
}
