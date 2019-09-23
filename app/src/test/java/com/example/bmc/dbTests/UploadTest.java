package com.example.bmc.dbTests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;
import com.example.bmc.db.Firebase_Helper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class UploadTest {
//    private Firebase_Helper helper;
//    private ComplianceInspection complianceInspection;
//    private User user;
//
//    @Before
//    public void setUp() {
//        helper = new Firebase_Helper();
//        user = new User( "John Doe", "John.Doe001" );
//        File file = new File( "D:\\Users\\danny\\AndroidStudioProjects\\BMC\\app\\src\\main\\res\\drawable\\image_for_upload_test.jpg" );
//        Bitmap bitmap = BitmapFactory.decodeFile( file.getPath() );
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, outputStream );
//
//        complianceInspection = new ComplianceInspection( 1, null, null, null, null, Base64.encodeToString( outputStream.toByteArray(), Base64.DEFAULT ),
//                user.getName(), null, null, null, null );
//    }
//
//    @After
//    public void tearDown() {
//        helper = null;
//        complianceInspection = null;
//        user = null;
//    }
//
//    @Test
//    public void upload_compliance_inspection_test() {
//        Assert.assertTrue( helper.addComplianceInspection(complianceInspection, new Firebase_Helper.DataStatus() {
//            @Override
//            public void dataIsInserted() {} } ) );
//    }
}
