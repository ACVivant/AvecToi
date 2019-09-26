package com.vivant.annecharlotte.avectoi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;

public class WelcomeActivity extends BaseActivity{

    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN_EMAIL = 123;
    private static final String TAG = "WelcomeActivity";
    public static final String USER_PHOTO = "userPhotoUrl";
    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "userEmail";
    private static final int DELETE_USER_TASK = 30;

    private String userId;
    private boolean createOK;
    private Context context;
    private User userHero;

    //FOR DESIGN
    private Button loginBtn;
    private Button newAccountBtn;
    private FrameLayout mainActivityLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mainActivityLayout = findViewById(R.id.welcome_activity_layout);

        createOK = false;
        context = getApplicationContext();

        loginBtn = findViewById(R.id.mainactivity_button_already_user);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInActivityEmail();
            }
        });

        newAccountBtn = findViewById(R.id.mainactivity_button_new_user);
        newAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (createOK) {
                    startSignInActivityEmail();
                } else {
                    showSnackBar(mainActivityLayout, getResources().getString(R.string.error_you_need_invitation));
                }
            }
        });

            // On gère si l'utilisateur s'est connecté via un lien Firebase

            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(getIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                            // Get deep link from result (may be null if no link is found)
                            Uri deepLink = null;
                            if (pendingDynamicLinkData != null) {
                                deepLink = pendingDynamicLinkData.getLink();
                                Log.d(TAG, "onSuccess: deepLink " + deepLink.toString());
                                Log.d(TAG, "onSuccess: un lien a été reçu");
                                createOK=true;
                                Log.d(TAG, "onSuccess: createOK " + createOK);
                            } else {
                                Log.d(TAG, "onSuccess: il n'y a pas de lien");
                                createOK = false;
                                Log.d(TAG, "onSuccess: createOK " + createOK);
                            }
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "getDynamicLink:onFailure", e);
                        }
                    });

            // Pour le premier utilisateur, on récupère la liste des utilisateurs. Si elle est vide l'inscription est posible sans invitation
       UserHelper.getAllUsers().whereEqualTo("activeAccount", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i=0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        i+=1;
                    }
                    if (i==0) createOK = true;
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    createOK = true;
                }
            }
        });;

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
                                if (createOK) {
                                    Log.d(TAG, "onSuccess: createOK " + createOK);
                                    createUserInFirestore();
                                    startCharteActivity();
                                } else {
                                    Log.d(TAG, "onSuccess: createOK " + createOK);
                                    //Toast.makeText(context, "Invitation nécessaire", Toast.LENGTH_LONG).show();
                                    showSnackBar(mainActivityLayout, getResources().getString(R.string.error_you_need_invitation));
                                    AuthUI.getInstance()
                                            .delete(context);
                                }
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
