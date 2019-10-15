package com.vivant.annecharlotte.avectoi;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.vivant.annecharlotte.avectoi.Adapters.HeroAdapter;
import com.vivant.annecharlotte.avectoi.firestore.BaseActivity;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Display user details
 */

public class HeroDetailActivity extends BaseActivity {

    private static final String TAG = "HeroDetailActivity";
    private static final int REQUEST_CALL = 1;
    private String userId;

    private ImageView userPhoto;
    private TextView userName, userEmail, userTel, userTown, userDescription, noReference;
    private ImageButton phoneBtn;
    private ImageButton mailBtn;

    private Button[] buttonTab = new Button[16];
    private boolean[] booleanTab = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    // RecyclerView
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mUserAskId = new ArrayList<>();
    private RecyclerView recyclerView;

    private String userPhoneNumber;
    private String toEmail;

    private List<String> listUserSP = new ArrayList<>();
    private List<Integer> listUserSPInt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);

        userId = getIntent().getStringExtra(HeroAdapter.HERO_ID);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
            }
        });


        layoutLinks();
        updateView(userId);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_hero_detail;
    }

    //--------------------------
    // Update UI
    //--------------------------

    private void layoutLinks() {
        userPhoto = findViewById(R.id.hero_detail_face);
        userName = findViewById(R.id.hero_detail_name);
        userEmail = findViewById(R.id.hero_detail_email);
        userTel = findViewById(R.id.hero_detail_tel);
        userTown = findViewById(R.id.hero_detail_town);
        userDescription = findViewById(R.id.hero_detail_description);
        noReference = findViewById(R.id.hero_no_ref);
        recyclerView = findViewById(R.id.hero_ref_rv);
        phoneBtn = findViewById(R.id.hero_phone_btn);
        mailBtn = findViewById(R.id.hero_email_btn);
        
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        buttonTab[0] = findViewById(R.id.hero_detail_SP_ironing);
        buttonTab[1] = findViewById(R.id.hero_detail_SP_household);
        buttonTab[2] = findViewById(R.id.hero_detail_SP_shopping);
        buttonTab[3] = findViewById(R.id.hero_detail_SP_cooking);
        buttonTab[4] = findViewById(R.id.hero_detail_SP_driving);
        buttonTab[5] = findViewById(R.id.hero_detail_SP_gardening);
        buttonTab[6] = findViewById(R.id.hero_detail_SP_diy);
        buttonTab[7] = findViewById(R.id.hero_detail_SP_works);
        buttonTab[8] = findViewById(R.id.hero_detail_SP_relocation);
        buttonTab[9] = findViewById(R.id.hero_detail_SP_reading);
        buttonTab[10] = findViewById(R.id.hero_detail_SP_babysitting);
        buttonTab[11] = findViewById(R.id.hero_detail_SP_sewing);
        buttonTab[12] = findViewById(R.id.hero_detail_SP_flowering);
        buttonTab[13] = findViewById(R.id.hero_detail_SP_tutoring);
        buttonTab[14] = findViewById(R.id.hero_detail_SP_company);
        buttonTab[15] = findViewById(R.id.hero_detail_SP_admin);
    }

    private void updateView(String uid) {

        UserHelper.getUser(uid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = Objects.requireNonNull(documentSnapshot.toObject(User.class));
                userName.setText(user.getUserName());
                userTel.setText(user.getUserPhone());
                userEmail.setText(user.getUserEmail());
                userTown.setText(user.getUserTown());
                userDescription.setText(user.getUserDescription());

                userPhoneNumber =  user.getUserPhone();
                toEmail = user.getUserEmail();

                if (user.getUrlPicture() != null) {
                    Glide.with(HeroDetailActivity.this)
                            .load(user.getUrlPicture())
                            .apply(RequestOptions.circleCropTransform())
                            .into(userPhoto);
                }

                //Super Power
                listUserSPInt = user.getUserSPList();

                for (int i=0; i<listUserSPInt.size(); i++) {
                    listUserSP.add(String.valueOf(listUserSPInt.get(i)));
                }
                for (int i=0; i<booleanTab.length; i++) {
                    if (listUserSP.contains(String.valueOf(i))) {
                        buttonTab[i].setBackground(getResources().getDrawable(R.drawable.button_background2));
                        booleanTab[i]=true;
                    } else {
                        buttonTab[i].setBackground(getResources().getDrawable(R.drawable.button_background));
                        booleanTab[i]=false;
                    }
                }

                // References
                List<User> listEventRef = user.getEventHeroRefList();

                if (listEventRef.get(0)!= null) {
                    for (int i = 0; i < listEventRef.size(); i++) {
                        mNames.add(listEventRef.get(i).getUserName());
                        mUserAskId.add(listEventRef.get(i).getUid());

                        if (listEventRef.get(i).getUrlPicture() != null) {
                            mImages.add(listEventRef.get(i).getUrlPicture());
                        } else {
                            mImages.add(EventDetailActivity.NO_PHOTO);
                        }
                    }
                }

                if(mNames.size()>0) {
                    noReference.setVisibility(View.GONE);
                    initRecyclerView();
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        HeroAdapter adapter = new HeroAdapter(this, mNames, mImages, mUserAskId);
        recyclerView.setAdapter(adapter);
    }

    //-------------------------------------
    // Phone call and email
    //-------------------------------------

    // Phone call
    private void makePhoneCall() {
        if (userPhoneNumber.trim().length()>0) {
            if(ContextCompat.checkSelfPermission(HeroDetailActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HeroDetailActivity.this, new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);

            } else {
                String dial = "tel:"+userPhoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(HeroDetailActivity.this, getResources().getString(R.string.tel_missing), Toast.LENGTH_LONG).show();
        }
    }

    // Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CALL) {
            if (grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(HeroDetailActivity.this, getResources().getString(R.string.permission_needed), Toast.LENGTH_LONG).show();
            }
        }
    }

    // Email
    private void sendEmail() {
    String subjectEmail = getResources().getString(R.string.app_name);
    String[] recipient = {toEmail};

    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.putExtra(Intent.EXTRA_EMAIL, recipient);
    intent.putExtra(Intent.EXTRA_SUBJECT, subjectEmail);

    intent.setType("message/rfc822");
    startActivity(Intent.createChooser(intent, getResources().getString(R.string.send_email)));
    }
}
