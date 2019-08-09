package com.vivant.annecharlotte.avectoi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.Arrays;
import java.util.Objects;

public class WelcomeActivity extends BaseActivity {

    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN_EMAIL = 123;
    private static final String TAG = "WelcomeActivity";
    public static final String USER_PHOTO = "userPhotoUrl";
    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "userEmail";

    public String userId;

    //FOR DESIGN
    private Button loginBtn;
    private FrameLayout mainActivityLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mainActivityLayout = findViewById(R.id.welcome_activity_layout);

        loginBtn = findViewById(R.id.mainactivity_button_already_user);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInActivityEmail();
            }
        });
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_welcome;
    }

    // --------------------
    // NAVIGATION
    // --------------------

    //Launch Sign-In Activity with email
    private void startSignInActivityEmail() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN_EMAIL);
    }

    // Retrieves the return of the authentication activity to check if it went well
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    // Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){
        IdpResponse response = IdpResponse.fromResultIntent(data);

        Log.d(TAG, "handleResponseAfterSignIn");
        if (requestCode == RC_SIGN_IN_EMAIL) {
            if (resultCode == RESULT_OK) { // SUCCESS
                Log.d(TAG, "handleResponseAfterSignIn: Success");


                if (isCurrentUserLogged()) {
                    userId = this.getCurrentUser().getUid();
                    UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {  // Si l'utilisateur a déjà un compte
                                Log.d(TAG, "onSuccess: documentSnapshot exists");
                                startMainActivity();
                            } else {
                                // CREATE USER
                                createUserInFirestore();
                                startCharteActivity();
                            }
                        }
                    });
                    }
                }

            } else { // ERRORS
                if (response == null) {
                    showSnackBar(this.mainActivityLayout, getString(R.string.error_authentication_canceled));
                } else if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackBar(this.mainActivityLayout, getString(R.string.error_no_internet));
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackBar(this.mainActivityLayout, getString(R.string.error_unknown_error));
                }
            }
        }


    //  Show Snack Bar with a message
    private void showSnackBar(FrameLayout frameLayout, String message){
        Snackbar.make(frameLayout, message, Snackbar.LENGTH_SHORT).show();
    }


    //---------------------
    // REST REQUEST
    //---------------------
    // Http request that create user in firestore

    private void createUserInFirestore(){
        Log.d(TAG, "createUserInFirestore");
        String urlPicture = (Objects.requireNonNull(this.getCurrentUser()).getPhotoUrl() != null) ? Objects.requireNonNull(this.getCurrentUser().getPhotoUrl()).toString() : null;
        String username = this.getCurrentUser().getDisplayName();
        String uid = this.getCurrentUser().getUid();
        String userEmail = this.getCurrentUser().getEmail();
        UserHelper.createUser(uid, username, userEmail, urlPicture).addOnFailureListener(this.onFailureListener());
    }

    private void startCharteActivity() {
        Intent intent = new Intent(this, CharteActivity.class);
        startActivity(intent);
    }

    // launch lunch activity
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USER_ID, Objects.requireNonNull(this.getCurrentUser()).getUid());
        startActivity(intent);
    }
}
