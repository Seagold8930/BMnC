//**************************************************************************************************
// Copyright <2019> <DAN MOTA>
//
// Permission to use, copy, modify, and/or distribute this software for any purpose with or without
// fee is hereby granted, provided that the above copyright notice and this permission notice appear
// in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS
// SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
// AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
// NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
// OF THIS SOFTWARE.
//**************************************************************************************************
package com.example.bmc.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.bmc.auxiliary.ComplianceInspection;
import com.example.bmc.auxiliary.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;

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

    public boolean addComplianceInspection( ComplianceInspection inspection, final DataStatus status ) {
        try {
            String entry_key = reference.push().getKey();
            reference.child( entry_key ).setValue( inspection ).addOnSuccessListener(
                    new OnSuccessListener< Void >() {
                        @Override
                        public void onSuccess( Void aVoid ) {
                            status.dataIsInserted();
                        }
                    } );
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main( String args[] ) {
        Firebase_Helper helper = new Firebase_Helper();
        User user = new User( "John Doe", "John.Doe001" );
        File file = new File( "D:\\Users\\danny\\AndroidStudioProjects\\BMC\\app\\src\\main\\res\\drawable\\image_for_upload_test.jpg" );
        Bitmap bitmap = BitmapFactory.decodeFile( file.getPath() );
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, outputStream );

        ComplianceInspection complianceInspection = new ComplianceInspection( 1, null, null, null, null, Base64.encodeToString( outputStream.toByteArray(), Base64.DEFAULT ),
                user.getName(), null, null, null, null );
        helper.addComplianceInspection( complianceInspection, new DataStatus() {
            @Override
            public void dataIsInserted() {
                System.out.println( "Data inserted" );
            }
        });
    }
}
