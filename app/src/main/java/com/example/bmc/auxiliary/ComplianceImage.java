package com.example.bmc.auxiliary;

import java.io.File;

public class ComplianceImage {
    private File imageFile;

    public ComplianceImage(File imageFile) {
        this.imageFile = imageFile;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
