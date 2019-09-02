package com.example.bmc.dbTests;

import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.db.DB_Handler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UploadTest {
    private DB_Handler handler;
    private ComplianceInspection complianceInspection;

    @Before
    public void setUp() {
        handler = new DB_Handler();
        complianceInspection = new ComplianceInspection();
    }

    @After
    public void tearDown() {
        handler = null;
        complianceInspection = null;
    }

    @Test
    public void upload_compliance_inspection_test() {
        Assert.assertTrue( handler.uploadComplianceInspection( complianceInspection ) );
    }
}
