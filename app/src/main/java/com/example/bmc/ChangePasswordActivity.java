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
package com.example.bmc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.bmc.db.DB_Handler;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.User;
import com.example.bmc.validate.Validate;

import java.util.ArrayList;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText mNewPass;
    private EditText mConfirmPass;
    private Button btnChangePass;
    private View focusView;
    private boolean cancel;
    private UpdateTask mUp = null;
    private User user;
    private ArrayList<Building> buildings = new ArrayList<>();
    private View mProgressView;
    private boolean isProgressbarShowing = false;
    private View mChangePassFormView;
    private static final String PREFERENCES_CRED = "PrefsFile";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        createActivity();

        if ( savedInstanceState != null && savedInstanceState.containsKey( "progressBarIsShowing" ) ) {
            showProgress( true );
            isProgressbarShowing = true;
        }
    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        if ( isProgressbarShowing ) {
            outState.putBoolean( "progressBarIsShowing", isProgressbarShowing );
        }
        super.onSaveInstanceState( outState );
    }

    private void createActivity() {
        setContentView( R.layout.activity_change_password );

        toolbar = findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white_24dp );
        setSupportActionBar( toolbar );

        mNewPass = findViewById( R.id.password_one );
        mConfirmPass = findViewById( R.id.password_two );
        btnChangePass = findViewById( R.id.change_password_button );

        try {
            user = ( User ) getIntent().getExtras().getSerializable( "User" );
            buildings = ( ArrayList< Building > ) getIntent().getExtras().
                    getSerializable( "Buildings" );
        } catch ( NullPointerException e ) {
            user = null;
            buildings = null;
        }

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields( mNewPass.getText().toString(), mConfirmPass.getText().toString() );
            }
        });

        mChangePassFormView = findViewById( R.id.change_password_form );
        mProgressView = findViewById( R.id.change_pass_progress );
    }

    private void validateFields( String newPass, String confirmPass ) {
        if ( mUp != null ) {
            return;
        }

        // Reset errors.
        mNewPass.setError( null );
        mConfirmPass.setError( null );

        Validate validate = new Validate();
        cancel = false;

        if( newPass.equals( confirmPass ) ) {
            int newPassErrorCode = validate.validatePassword( newPass );
            int confirmPassErrorCode = validate.validatePassword( confirmPass );

            if( newPassErrorCode == -1 || confirmPassErrorCode == -1 ) {
                focusView = mNewPass;
                Toast.makeText( getApplicationContext(), getString( R.string.unexpected_error ),
                        Toast.LENGTH_SHORT ).show();
                cancel = true;
            } else if ( newPassErrorCode != 0 ) {
                mNewPass.setError( getErrorMessage( newPassErrorCode ) );
                focusView = mNewPass;
                cancel = true;
            } else if ( confirmPassErrorCode != 0 ) {
                mConfirmPass.setError( getErrorMessage( confirmPassErrorCode ) );
                focusView = mConfirmPass;
                cancel = true;
            }

            if ( cancel ) {
                focusView.requestFocus();
            } else {
                isProgressbarShowing = true;
                showProgress( true );
                mUp = new UpdateTask( user, mNewPass.getText().toString() );
                mUp.execute( ( Void ) null );
            }

        } else {
            mConfirmPass.setError( getString( R.string.error_password_mismatch ) );
            focusView = mConfirmPass;
            focusView.requestFocus();
        }
    }

    private CharSequence getErrorMessage(int errorCode ) {
        switch ( errorCode ) {
            case 4 :
                return getString( R.string.error_field_required );
            case 3 :
                return getString( R.string.error_white_space );
            case 2 :
                return getString( R.string.error_invalid_length );
            case 1 :
                return getString( R.string.error_invalid_password );
            default:
                Toast.makeText( getApplicationContext(), getString( R.string.unexpected_error ),
                        Toast.LENGTH_SHORT ).show();
                break;
        }
        return null;
    }

    @TargetApi( Build.VERSION_CODES.HONEYCOMB_MR2 )
    private void showProgress( final boolean show ) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ) {
            int shortAnimTime = getResources().getInteger( android.R.integer.config_shortAnimTime );

            mChangePassFormView.setVisibility( show ? View.GONE : View.VISIBLE );
            mChangePassFormView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 0 : 1 ).setListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd( Animator animation ) {
                    mChangePassFormView.setVisibility( show ? View.GONE : View.VISIBLE );
                }
            } );

            mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
            mProgressView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 1 : 0 ).setListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd( Animator animation ) {
                    mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
                }
            } );
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
            mChangePassFormView.setVisibility( show ? View.GONE : View.VISIBLE );
        }
    }

    private class UpdateTask extends AsyncTask< Void, Void, Boolean > {
        User user;
        String newPass;

        public UpdateTask( User user, String newPass ) {
            this.user = user;
            this.newPass = newPass;
        }

        @Override
        protected Boolean doInBackground( Void... voids ) {
            DB_Handler handler = new DB_Handler();

            if( handler.getConnection() == null ) {
                Log.d( "AsyncTask", "Null connection" );
                Snackbar.make( findViewById( R.id.password_one ), "Connection failed. Check " +
                        "your network access.", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
                return false;
            } else
                return handler.updatePassword( newPass, user );
        }

        @Override
        protected void onPostExecute( final Boolean success ) {
            mUp = null;
            showProgress( false );
            isProgressbarShowing = false;

            if ( success ) {
                SharedPreferences preferences = getSharedPreferences( PREFERENCES_CRED, MODE_PRIVATE );
                preferences.edit().clear().apply();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString( "username", user.getUsername() );
                editor.putString( "password", newPass );
                editor.putBoolean( "checkbox", true );
                editor.apply();

                Intent intent = new Intent( getApplicationContext(), DashboardActivity.class );
                intent.putExtra( "User", user );
                intent.putExtra( "Buildings", buildings );
                finish();
                startActivity( intent );
            } else {
                Snackbar.make( findViewById( R.id.password_one ), "Error updating the " +
                        "password. Check your connection and try again.", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
