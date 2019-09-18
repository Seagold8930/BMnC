package com.example.bmc;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;

import com.example.bmc.db.DB_Handler;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bmc.auxiliary.Building;
import com.example.bmc.auxiliary.User;
import com.example.bmc.auxiliary.UserCredentials;
import com.example.bmc.validate.Validate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    public static final int PERMISSION_REQUEST_CODE = 100;
    private UserCredentials userCredentials;
    protected boolean loginSuccess = false;
    private User user;
    private ArrayList<Building> buildings;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        checkPermissions();

        // Set up the login form.
        mUsernameView = findViewById( R.id.username );
        mPasswordView = findViewById( R.id.password );

        //TODO remove after testing
        mUsernameView.setText( "Jane.Smith001" );
        mPasswordView.setText( "MyPass1000" );
//        mUsernameView.setText( "john.doe001" );
//        mPasswordView.setText( "John.Doe001" );

        mPasswordView.setOnEditorActionListener( new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction( TextView textView, int id, KeyEvent keyEvent ) {
                if ( id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL ) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        } );

        Button mSignInButton = findViewById( R.id.sign_in_button );
        mSignInButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View view ) {
                userCredentials = new UserCredentials( mUsernameView.getText().toString(), mPasswordView.getText().toString() );
                attemptLogin();
            }
        } );

        mLoginFormView = findViewById( R.id.login_form );
        mProgressView = findViewById( R.id.login_progress );
    }

    private void checkPermissions() {
        if ( ContextCompat.checkSelfPermission( getApplicationContext(),
                Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission( getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission( getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ) {

            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions( this,
                new String[]{ Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                PERMISSION_REQUEST_CODE );
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if ( mAuthTask != null ) {
            return;
        }

        // Reset errors.
        mUsernameView.setError( null );
        mPasswordView.setError( null );

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        Validate validate = new Validate();
        int userErrorCode = validate.validateUsername( username );
        int passErrorCode = validate.validatePassword( password );

        if ( userErrorCode == -1 || passErrorCode == -1 ) {
            focusView = mUsernameView;
            Toast.makeText( getApplicationContext(), getString( R.string.unexpected_error ), Toast.LENGTH_SHORT ).show();
            cancel = true;
        } else if ( userErrorCode != 0 ) {
            mUsernameView.setError( getErrorMessage( userErrorCode ) );
            focusView = mUsernameView;
            cancel = true;
        } else if ( passErrorCode != 0 ) {
            mPasswordView.setError( getErrorMessage( passErrorCode ) );
            focusView = mPasswordView;
            cancel = true;
        }

        if ( cancel ) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress( true );
//            mAuthTask = new UserLoginTask( username, password );
            mAuthTask = new UserLoginTask( userCredentials.getUsername(), userCredentials.getPassword(), loginSuccess );
            userCredentials = null;
            mAuthTask.execute( ( Void ) null );
        }
    }

    private CharSequence getErrorMessage( int errorCode ) {
        switch ( errorCode ) {
            case 4 :
                return getString(R.string.error_field_required);
            case 3 :
                return getString( R.string.error_white_space );
            case 2 :
                return getString( R.string.error_invalid_length );
            case 1 :
                return getString( R.string.error_pattern_mismatch );
            default:
                Toast.makeText( getApplicationContext(), getString( R.string.unexpected_error ), Toast.LENGTH_SHORT ).show();
                break;
        }

        return null;
    }

    private boolean isUsernameValid( String username ) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi( Build.VERSION_CODES.HONEYCOMB_MR2 )
    private void showProgress( final boolean show ) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ) {
            int shortAnimTime = getResources().getInteger( android.R.integer.config_shortAnimTime );

            mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
            mLoginFormView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 0 : 1 ).setListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd( Animator animation ) {
                    mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
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
            mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
        }
    }

    @Override
    public Loader< Cursor > onCreateLoader( int i, Bundle bundle ) {
        return new CursorLoader( this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath( ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY ), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE },

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC" );
    }

    @Override
    public void onLoadFinished( Loader< Cursor > cursorLoader, Cursor cursor ) {
        List< String > usernames = new ArrayList<>();
        cursor.moveToFirst();
        while ( !cursor.isAfterLast() ) {
            usernames.add( cursor.getString( ProfileQuery.ADDRESS ) );
            cursor.moveToNext();
        }

        addUsernamesToAutoComplete( usernames );
    }

    @Override
    public void onLoaderReset( Loader< Cursor > cursorLoader ) {

    }

    private void addUsernamesToAutoComplete( List< String > usernamesCollection ) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter< String > adapter =
                new ArrayAdapter<>( LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, usernamesCollection );

        mUsernameView.setAdapter( adapter );
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask< Void, Void, Boolean > {
        private final String mUsername;
        private final String mPassword;
        private boolean loginSuccess;

        UserLoginTask( String username, String password, boolean loginSuccess ) {
            this.mUsername = username;
            this.mPassword = password;
            this.loginSuccess = loginSuccess;
        }

        @Override
        protected Boolean doInBackground( Void... params ) {
            DB_Handler handler = new DB_Handler();
            loginSuccess = false;

            if( handler.getConnection() == null ) {
                Snackbar.make( findViewById( R.id.username ), "Connection failed. Check your network access.", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
                return false;
            } else {
                user = handler.login( mUsername, mPassword );

                if (user == null) {
                    loginSuccess = false;
                    return true;
                } else {
                    handler = new DB_Handler();
                    buildings = handler.getBuildingName( user );
                    loginSuccess = true;
                    return true;
                }
            }
        }

        @Override
        protected void onPostExecute( final Boolean success ) {
            mAuthTask = null;
            showProgress( false );

            if ( success && loginSuccess ) {
                if( mUsername.toLowerCase().equals( mPassword.toLowerCase() ) ) {
                    Intent intent = new Intent( getApplicationContext(), ChangePasswordActivity.class );
                    intent.putExtra( "User", user );
                    intent.putExtra( "Buildings", buildings );
                    intent.putExtra( "Default Password", mPassword );
                    startActivity( intent );
                } else {
                    Intent intent = new Intent( getApplicationContext(), DashboardActivity.class );
                    intent.putExtra( "User", user );
                    intent.putExtra( "Buildings", buildings );
                    startActivity( intent );
                }
            } else if ( success && ! loginSuccess ) {
                Snackbar.make( findViewById( R.id.username ), "Login failed. Check your credentials.", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            } else {
                Snackbar.make( findViewById( R.id.username ), "Connection failed. Check your network access.", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress( false );
        }
    }
}