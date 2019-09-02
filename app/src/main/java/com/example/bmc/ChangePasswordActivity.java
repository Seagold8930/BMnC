package com.example.bmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bmc.validate.Validate;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText mNewPass;
    private EditText mConfirmPass;
    private View focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);

        mNewPass = findViewById( R.id.password_one );
        mConfirmPass = findViewById( R.id.password_two );
        Button changePass = findViewById( R.id.change_password_button );

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields( mNewPass.getText().toString(), mConfirmPass.getText().toString() );
            }
        });
    }

    private void validateFields(String newPass, String confirmPass) {
        Validate validate = new Validate();
        boolean cancel = false;

        if( newPass.equals( confirmPass ) ) {
            int newPassErrorCode = validate.validatePassword( newPass );
            int confirmPassErrorCode = validate.validatePassword( confirmPass );

            if( newPassErrorCode == -1 || confirmPassErrorCode == -1 ) {
                focusView = mNewPass;
                Toast.makeText( getApplicationContext(), getString( R.string.unexpected_error ), Toast.LENGTH_SHORT ).show();
                cancel = true;
            } else if ( newPassErrorCode != 0 ) {
                mNewPass.setError( getErrorCode( newPassErrorCode ) );
                focusView = mNewPass;
                cancel = true;
            } else if ( confirmPassErrorCode != 0 ) {
                mConfirmPass.setError( getErrorCode( confirmPassErrorCode ) );
                focusView = mConfirmPass;
                cancel = true;
            }
//            if ( newPassErrorCode == 4 ) {
//                mNewPass.setError( getString( R.string.error_field_required ) );
//                focusView = mNewPass;
//                cancel = true;
//            } else if ( newPassErrorCode == 3 ) {
//                mNewPass.setError( getString( R.string.error_white_space ) );
//                focusView = mNewPass;
//                cancel = true;
//            } else if ( newPassErrorCode == 2 ) {
//                mNewPass.setError( getString( R.string.error_invalid_length ) );
//                focusView = mNewPass;
//                cancel = true;
//            } else if ( newPassErrorCode == 1 ) {
//                mNewPass.setError( getString( R.string.error_invalid_password ) );
//                focusView = mNewPass;
//                cancel = true;
//            } else if ( confirmPassErrorCode == 4 ) {
//                mConfirmPass.setError( getString( R.string.error_field_required ) );
//                focusView = mConfirmPass;
//                cancel = true;
//            } else if ( confirmPassErrorCode == 3 ) {
//                mConfirmPass.setError( getString( R.string.error_white_space ) );
//                focusView = mConfirmPass;
//                cancel = true;
//            } else if ( confirmPassErrorCode == 2 ) {
//                mConfirmPass.setError( getString( R.string.error_invalid_length ) );
//                focusView = mConfirmPass;
//                cancel = true;
//            } else if ( confirmPassErrorCode == 1 ) {
//                mConfirmPass.setError( getString( R.string.error_invalid_password ) );
//                focusView = mConfirmPass;
//                cancel = true;
//            } else if ( newPassErrorCode == -1 || confirmPassErrorCode == -1 ) {
//                focusView = mNewPass;
//                Toast.makeText( getApplicationContext(), getString( R.string.unexpected_error ), Toast.LENGTH_SHORT ).show();
//                cancel = true;
//            }

            if ( cancel ) {
                focusView.requestFocus();
            } else {
                //TODO overwrite the database
                startActivity( new Intent( getApplicationContext(), DashboardActivity.class ) );
            }

        } else {
            mConfirmPass.setError( getString( R.string.error_password_mismatch ) );
            focusView = mConfirmPass;
            focusView.requestFocus();
        }
    }

    private CharSequence getErrorCode(int errorCode) {
        switch (errorCode) {
            case 4 :
                return getString( R.string.error_field_required );
            case 3 :
                return getString( R.string.error_white_space );
            case 2 :
                return getString( R.string.error_invalid_length );
            case 1 :
                return getString( R.string.error_invalid_password );
            default:
                Toast.makeText( getApplicationContext(), getString( R.string.unexpected_error ), Toast.LENGTH_SHORT ).show();
                break;
        }
        return null;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
