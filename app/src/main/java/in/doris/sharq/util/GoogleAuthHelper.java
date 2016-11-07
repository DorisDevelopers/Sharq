package in.doris.sharq.util;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import in.doris.sharq.activities.LoginActivity;
import in.doris.sharq.activities.DisplayMenuActivity;

/**
 * Created by Nishu on 07-11-2016.
 */

public class GoogleAuthHelper {
    private static GoogleAuthHelper instance = null;
    public static final String TAG = "SignInActivity";
    public static final int RC_SIGN_IN = 9001;
    public static GoogleApiClient mGoogleApiClient = null;
    private static GoogleSignInOptions gso = null;
    private GoogleAuthHelper(){

    }

    /*public static GoogleAuthHelper getInstance() {
        if (instance == null) {
            instance = new GoogleAuthHelper();
        }
        return instance;
    }*/
    public static GoogleAuthHelper initialiseGoogleApiClient(LoginActivity obj){
        if (instance == null) {
            instance = new GoogleAuthHelper();
        }
        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        if(gso == null){
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                /*.requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))*/
                    .requestEmail()
                    .build();
        }

        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(obj)
                    .enableAutoManage(obj /* FragmentActivity */, obj /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

        }
        // [END build_client]
        return instance;


    }
    public static GoogleAuthHelper getInstance(){
        return instance;
    }

    // [START signOut]
    public boolean signOut(DisplayMenuActivity obj) {
        try{
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]

                            //updateUI(false);
                            // [END_EXCLUDE]
                        }
                    });
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // [END signOut]
}
