package com.vivant.annecharlotte.avectoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.Objects;

public class CreateUserActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "CreateUserActivity";
    private String userId;

    private ImageView userImage;
    private TextView userNameTV;
    private TextView userEmailTV;
    private EditText userTel;
    private EditText userTown;
    private Spinner communitySpinner;
    private Button ironing;
    private Button household;
    private Button shopping;
    private Button cooking;
    private Button driving;
    private Button gardening;
    private Button diy;
    private Button works;
    private Button relocation;
    private Button reading;
    private Button babysitting;
    private Button sewing;
    private Button admin;
    private Button flower;
    private Button tutoring;
    private Button company;

    private String telData;
    private String townData;
    private boolean ironData;
    private boolean houseData;
    private boolean shopData;
    private boolean cookData;
    private boolean driveData;
    private boolean gardenData;
    private boolean diyData;
    private boolean workData;
    private boolean relocationData;
    private boolean readingData;
    private boolean babysittingData;
    private boolean sewingData;
    private boolean floweringData;
    private boolean tutoringData;
    private boolean companyData;
    private boolean adminData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_create_user);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        userId = this.getCurrentUser().getUid();
        layoutLinks();
        updateUIwhenCreating();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_create_user;
    }

    public void layoutLinks() {
        Log.d(TAG, "layoutLinks");
        userImage = findViewById(R.id.create_face);
        userNameTV = findViewById(R.id.create_name);
        userEmailTV = findViewById(R.id.create_email);

        userTel = findViewById(R.id.create_tel);
        userTown = findViewById(R.id.create_town);

        ironing = findViewById(R.id.create_user_SP_ironing);
        ironing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: irondata " + ironData);
                ironData = !ironData;
                Log.d(TAG, "onClick: irondata " + ironData);
            }
        });
        household = findViewById(R.id.create_user_SP_household);
        household.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                houseData = !houseData;
            }
        });
        shopping = findViewById(R.id.create_user_SP_shopping);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopData = !shopData;
            }
        });
        cooking = findViewById(R.id.create_user_SP_cooking);
        cooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookData = !cookData;
            }
        });
        driving = findViewById(R.id.create_user_SP_driving);
        driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driveData = !driveData;
            }
        });
        gardening = findViewById(R.id.create_user_SP_gardening);
        gardening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gardenData = !gardenData;
            }
        });
        diy = findViewById(R.id.create_user_SP_diy);
        diy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diyData = !diyData;
            }
        });
        works = findViewById(R.id.create_user_SP_works);
        works.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workData = !workData;
            }
        });
        relocation = findViewById(R.id.create_user_SP_relocation);
        relocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relocationData = !relocationData;
            }
        });
        reading = findViewById(R.id.create_user_SP_reading);
        reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readingData = !readingData;
            }
        });
        company = findViewById(R.id.create_user_SP_company);
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyData = !companyData;
            }
        });
        babysitting = findViewById(R.id.create_user_SP_babysitting);
        babysitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                babysittingData = !babysittingData;
            }
        });
        sewing = findViewById(R.id.create_user_SP_sewing);
        sewing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sewingData = !sewingData;
            }
        });
        admin = findViewById(R.id.create_user_SP_admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminData = !adminData;
            }
        });
        flower = findViewById(R.id.create_user_SP_flower);
        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floweringData = !floweringData;
            }
        });
        tutoring = findViewById(R.id.create_user_SP_tutoring);
        tutoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutoringData = !tutoringData;
            }
        });
    }

public void updateUIwhenCreating() {
    Log.d(TAG, "updateUIwhenCreating");

    if (this.getCurrentUser() != null){
        //Get picture URL from Firebase
        if (this.getCurrentUser().getPhotoUrl() != null) {
            Glide.with(this)
                    .load(this.getCurrentUser().getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(userImage);
        }

        //Get email & username from Firebase
        String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
        String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ? getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();

        //Update views with login data
        this.userNameTV.setText(username);
        this.userEmailTV.setText(email);

        Log.d(TAG, "updateUIwhenCreating: userId " + userId);
        //Update views with super powers data
        launchSuperPowerData(userId);
    }
}

public void launchSuperPowerData(String uid) {
    Log.d(TAG, "launchSuperPowerData");
    UserHelper.getUser(uid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            if (documentSnapshot.exists()) {
                Log.d(TAG, "onSuccess: documentSnapshot exists");
            ironData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getIroningSP();
            if (ironData) {ironing.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            houseData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getHouseholdSP();
                if (houseData) {household.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            shopData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getShoppingSP();
                    if (shopData) {shopping.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            cookData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getCookingSP();
                        if (cookData) {cooking.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            driveData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getDrivingSP();
                            if (driveData) {driving.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            gardenData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getGardeningSP();
                                if (gardenData) {gardening.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            diyData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getDiySP();
                                    if (diyData) {diy.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            workData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getWorksSP();
                                        if (workData) {works.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            relocationData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getRelocationSP();
                                            if (relocationData) {relocation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            readingData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getReadingSP();
                                                if (readingData) {reading.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            companyData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getCompanySP();
                                                    if (companyData) {company.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            babysittingData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getBabysittingSP();
                                                        if (babysittingData) {babysitting.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            sewingData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getSewingSP();
                                                            if (sewingData) {sewing.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            adminData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getAdminSP();
                                                                if (adminData) {admin.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));}
            floweringData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getFloweringSP();
                                                                    if (floweringData) {flower.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight)); }

            telData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserPhone();
            userTel.setText(telData);


            townData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserTown();
            userTown.setText(townData);
            }
        }
    });
}

public void saveData() {
        if (userTel.getText().equals("")) {
            Toast.makeText(this, "Il faut entrer un numéro de téléphone.", Toast.LENGTH_LONG).show();
        } else {
            telData =userTel.getText().toString();
            townData = userTown.getText().toString();

            Log.d(TAG, "updateLikeInFirebase: idUser " + userId);
            UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Log.d(TAG, "onSuccess: documentSnapshot exists");
                        updateSuperPower(userId);
                        updateTelandTown(userId);
                    }
                }
            });

            launchMainActivity();
        }
}

public void updateSuperPower(String uid) {
        UserHelper.updateHouseholdSP(houseData,uid);
        UserHelper.updateIroningSP(ironData, uid);
        UserHelper.updateShoppingSP(shopData, uid);
        UserHelper.updateCookingSP(cookData, uid);
        UserHelper.updateDrivingSP(driveData, uid);
        UserHelper.updateGardeningSP(gardenData, uid);
        UserHelper.updateDiySP(diyData, uid);
        UserHelper.updateWorksSP(workData, uid);
        UserHelper.updateRelocationSP(relocationData, uid);
        UserHelper.updateReadingSP(readingData, uid);
        UserHelper.updateCompanySP(companyData, uid);
        UserHelper.updateBabysittingSP(babysittingData, uid);
        UserHelper.updateSewingSP(sewingData, uid);
        UserHelper.updateTutoringSP(tutoringData, uid);
        UserHelper.updateFloweringSP(floweringData, uid);
        UserHelper.updateAdminSP(adminData, uid);
}

public void updateTelandTown(String uid) {
        UserHelper.updateTel(telData, uid);
        UserHelper.updateTown(townData, uid);
}


public void launchMainActivity() {
        Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
        startActivity(intent);
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_event_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_event:
                saveData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
