package com.example.bmc.dbTests;

import android.net.Uri;

import com.example.bmc.auxiliary.ComplianceImage;
import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;
import com.example.bmc.db.DB_Handler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class UploadTest {
    private DB_Handler handler;
    private ComplianceInspection complianceInspection;
    private User user;

    @Before
    public void setUp() {
//        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(  );
        handler = new DB_Handler();
        complianceInspection = new ComplianceInspection( 1, null, null, null, null, new ComplianceImage() );
        user = new User( "John Doe", "John.Doe001" );

//        Uri uri = Uri.parse( "android.resource://com.example.bmc/res/drawable/image_for_upload_test.jpg" );
    }

    @After
    public void tearDown() {
        handler = null;
        complianceInspection = null;
        user = null;
    }

    @Test
    public void upload_compliance_inspection_test() {
        Assert.assertTrue( handler.uploadComplianceInspection( complianceInspection, user ) );
    }
}
