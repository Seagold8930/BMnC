package com.example.bmc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText mNewPass;
    private EditText mConfirmPass;
    private Button changePass;
    private View focusView;
    private boolean cancel;
    private UpdateTask mUp = null;
    private User user;
    private ArrayList<Building> buildings = new ArrayList<>();
    private View mProgressView;
    private View mChangePassFormView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        createActivity();
    }

    private void createActivity() {
        setContentView( R.layout.activity_change_password );

        toolbar = findViewById( R.id.toolbar );
        toolbar.setNavigationIcon( R.drawable.ic_arrow_back_white_24dp );
        setSupportActionBar( toolbar );

        mNewPass = findViewById( R.id.password_one );
        mConfirmPass = findViewById( R.id.password_two );
        changePass = findViewById( R.id.change_password_button );

        user = ( User ) getIntent().getExtras().getSerializable( "User" );
        buildings = ( ArrayList< Building > ) getIntent().getExtras().getSerializable( "Buildings" );

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields( mNewPass.getText().toString(), mConfirmPass.getText().toString() );
            }
        });

        mChangePassFormView = findViewById( R.id.change_password_form );
        mProgressView = findViewById( R.id.change_pass_progress );
    }

    private void validateFields(String newPass, String confirmPass) {
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

            if ( cancel ) {
                focusView.requestFocus();
            } else {
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

    private CharSequence getErrorCode( int errorCode ) {
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

    public class UpdateTask extends AsyncTask< Void, Void, Boolean > {
        User user;
        String newPass;

        public UpdateTask(User user, String newPass) {
            this.user = user;
            this.newPass = newPass;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Connection conn = getConnection();

            if( conn == null ) {
                Log.d( "AsyncTask", "Null connection" );
                Snackbar.make( findViewById( R.id.cancel_button ), "Connection failed. Check your network access.", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
                return false;
            } else {
                Log.v( "Connection: ", "Success" );
                PreparedStatement statement = null;

                try {
                    conn.setAutoCommit( false );
                    statement = conn.prepareStatement( "UPDATE [User] SET [password] = ? WHERE " +
                            "lower( [userID] ) = ?" );
                    statement.setString( 1, newPass );
                    statement.setString( 2, user.getUsername().toLowerCase() );

                    statement.execute();
                    conn.commit();

                    return true;
                } catch ( SQLException e ) {
                    e.printStackTrace();
                    try {
                        conn.rollback();
                        conn.setAutoCommit( true );
                    } catch ( SQLException e1 ) {
                        e1.printStackTrace();
                    }
                } finally {
                    try {
                        statement.close();
                        conn.setAutoCommit( true );
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            return false;
        }

        public Connection getConnection() {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy( policy );

            String connectionString = "jdbc:jtds:sqlserver://bmcs.database.windows.net:1433/BMnC;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;";
            String username = "bmcs_admin";
            String password = "Weltec2019";

            try {
                Class.forName( "net.sourceforge.jtds.jdbc.Driver" );
                return DriverManager.getConnection( connectionString, username, password );
            } catch ( ClassNotFoundException e ) {
                e.printStackTrace();
            } catch ( SQLException e ) {
                e.printStackTrace();
                Snackbar.make( findViewById( R.id.username ), "Connection failed. Check your network access.", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute( final Boolean success ) {
            mUp = null;
            showProgress( false );

            if ( success ) {
                Intent intent = new Intent( getApplicationContext(), DashboardActivity.class );
                intent.putExtra( "User", user );
                intent.putExtra( "Buildings", buildings );
                finish();
                startActivity( intent );
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
