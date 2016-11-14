package in.doris.sharq.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.ArrayList;

import in.doris.sharq.db.beans.MstUserBean;
import in.doris.sharq.constants.SharqConstants;
import in.doris.sharq.db.datasources.MstUserDataSource;
import in.doris.sharq.db.dbHelper.SharqDbHelper;
import in.doris.sharq.util.GoogleAuthHelper;

import static in.doris.sharq.constants.SharqConstants.KEY_EMAIL;
import static in.doris.sharq.constants.SharqConstants.KEY_LNAME;
import static in.doris.sharq.constants.SharqConstants.KEY_NAME;

/**
 * Activity to demonstrate basic retrieval of the Google user's ID, email address, and basic
 * profile.
 */
public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {


    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    private GoogleAuthHelper googleAuthHelper;
    SharqDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        /*findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);*/

        googleAuthHelper = GoogleAuthHelper.initialiseGoogleApiClient(this);//Initialise Login Api
        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        //signInButton.setScopes(gso.getScopeArray());
        // [END customize_button]
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(GoogleAuthHelper.mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(GoogleAuthHelper.TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleAuthHelper.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Intent intent = null;
        Log.d(GoogleAuthHelper.TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));

            dbHelper = SharqDbHelper.getInstance(getApplicationContext());
            //Actions to perform on database after first successful login starts.
            switch(isNewUser(acct)){
                case SharqConstants.NO_USER:
                        //Pass name and email from login details.
                        intent =new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra(KEY_NAME, acct.getGivenName());
                        intent.putExtra(KEY_LNAME, acct.getFamilyName());
                        intent.putExtra(KEY_EMAIL, acct.getEmail());
                        startActivity(intent);
                    break;
                case SharqConstants.EXISTING_USER:
                    intent = new Intent(LoginActivity.this,DisplayMenuActivity.class);
                    startActivity(intent);
                    break;
                case SharqConstants.DIFFERENT_EMAIL:
                    //Return Error page and ask to login with correct user.
                    googleAuthHelper.signOut();
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Different Email")
                            .setMessage("Kindly Sign in with registered Email.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Whatever...
                                }
                            }).create().show();
            }
            //Actions to perform on database after first successful login Ends.



            //updateUI(true);



        } else {
            // Signed out, show unauthenticated UI.
            /*new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Not Signed in")
                    .setMessage("Kindly Sign in to proceed.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).create().show();*/
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(GoogleAuthHelper.mGoogleApiClient);
        startActivityForResult(signInIntent, GoogleAuthHelper.RC_SIGN_IN);
    }
    // [END signIn]

    // [START revokeAccess]
    /*private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }*/
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(GoogleAuthHelper.TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    /*private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }*/

    @Override
    public void onClick(View v) {
        if(R.id.sign_in_button==v.getId()){
            signIn();
        }
        /*switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }*/
    }
    private int isNewUser(GoogleSignInAccount acct){

        MstUserDataSource userDs = new MstUserDataSource(getApplicationContext());
        MstUserBean userBean = new MstUserBean();
        try{
            userBean.setEmail(acct.getEmail());
            userDs.open();
            ArrayList<MstUserBean> userlist = (ArrayList<MstUserBean>) userDs.getAllUsers();
            if(userlist.size()==0){
                return SharqConstants.NO_USER;
            }else if(userlist.size()>1){
                if(userDs.deleteAllUsers() != 0){
                    return SharqConstants.NO_USER;
                }else{
                    throw new Exception();
                }
            }else{
                if(userlist.get(0).getEmail().equalsIgnoreCase(acct.getEmail())){
                    return SharqConstants.EXISTING_USER;
                }else{
                    return SharqConstants.DIFFERENT_EMAIL;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return SharqConstants.EXCEPTION;
        }
    }
}