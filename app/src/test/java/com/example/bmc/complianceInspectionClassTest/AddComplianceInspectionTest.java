package com.example.bmc.complianceInspectionClassTest;

import com.example.bmc.auxiliary.ComplianceInspection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AddComplianceInspectionTest {
    private ComplianceInspection complianceInspection;
    private int buildingID;
    private String date;
    private String finding;
    private String description;
    private String status;

    @Before
    public void setUp() {
        complianceInspection = new ComplianceInspection();
        buildingID = 11;
        date = "30 Aug, 2019";
        finding = "some finding";
        description = "some description";
        status = "Open";
    }

    @After
    public void tearDown() {
        complianceInspection = null;
        buildingID = 0;
        date = null;
        finding = null;
        description = null;
        status = null;
    }

    @Test
    public void input_validator_add_compliance_data() {
//        Assert.assertTrue( complianceInspection.addComplianceInspectionData( buildingID, date, finding, description, status ) );
    }
}
