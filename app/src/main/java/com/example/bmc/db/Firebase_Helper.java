package com.example.bmc.db;

import com.example.bmc.auxiliary.ComplianceInspection;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase_Helper {
    private FirebaseDatabase db;
    private DatabaseReference reference;

    public interface DataStatus {
        void dataIsInserted();
    }

    public Firebase_Helper() {
        db = FirebaseDatabase.getInstance();
        reference = db.getReference( "Compliance_Inspection" );
    }

    public void addComplianceInspection( ComplianceInspection inspection, final DataStatus status ) {

        String entry_key = reference.push().getKey();
        reference.child( entry_key ).setValue( inspection ).addOnSuccessListener( new OnSuccessListener< Void >() {
            @Override
            public void onSuccess( Void aVoid ) {
                status.dataIsInserted();
            }
        } );

    }
}
