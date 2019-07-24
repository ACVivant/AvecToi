package com.vivant.annecharlotte.avectoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    private Spinner communitySpinner;
    private Switch userDispo;
    private Switch ironing;
    private Switch household;
    private Switch shopping;
    private Switch cooking;
    private Switch driving;
    private Switch gardening;
    private Switch diy;
    private Switch works;
    private Switch relocation;
    private Switch reading;
    private Switch company;
    private Switch babysitting;
    private Switch sewing;
    private Button validate;

    private int telData;
    private int townData;
    private boolean dispoData;
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
    private boolean companyData;
    private boolean babysittingData;
    private boolean sewingData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_create_user);
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
        userDispo = findViewById(R.id.create_switch_dispo);

        communitySpinner = findViewById(R.id.create_spinner_community);

        ironing = findViewById(R.id.create_switch_ironing);
        household = findViewById(R.id.create_switch_household);
        shopping = findViewById(R.id.create_switch_shopping);
        cooking = findViewById(R.id.create_switch_cooking);
        driving = findViewById(R.id.create_switch_driving);
        gardening = findViewById(R.id.create_switch_gardening);
        diy = findViewById(R.id.create_switch_diy);
        works = findViewById(R.id.create_switch_works);
        relocation = findViewById(R.id.create_switch_removing);
        reading = findViewById(R.id.create_switch_reading);
        company = findViewById(R.id.create_switch_company);
        babysitting = findViewById(R.id.create_switch_babysitting);
        sewing = findViewById(R.id.create_switch_sewing);

        validate = findViewById(R.id.create_validate_btn);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

public void updateUIwhenCreating() {
    Log.d(TAG, "updateUIwhenCreating");
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.create_town, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    communitySpinner.setAdapter(adapter);
    communitySpinner.setOnItemSelectedListener(this);

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
            ironing.setChecked(ironData);
            houseData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getHouseholdSP();
            household.setChecked(houseData);
            shopData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getShoppingSP();
            shopping.setChecked(shopData);
            cookData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getCookingSP();
            cooking.setChecked(cookData);
            driveData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getDrivingSP();
            driving.setChecked(driveData);
            gardenData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getGardeningSP();
            gardening.setChecked(gardenData);
            diyData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getDiySP();
            diy.setChecked(diyData);
            workData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getWorksSP();
            works.setChecked(workData);
            relocationData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getRelocationSP();
            relocation.setChecked(relocationData);
            readingData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getReadingSP();
            reading.setChecked(readingData);
            companyData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getCompanySP();
            company.setChecked(companyData);
            babysittingData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getBabysittingSP();
            babysitting.setChecked(babysittingData);
            sewingData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getSewingSP();
            sewing.setChecked(sewingData);

            telData = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserPhone();
                Log.d(TAG, "onSuccess: telData " + telData);
            userTel.setText("0"+telData);

            communitySpinner.setSelection(Objects.requireNonNull(documentSnapshot.toObject(User.class)).getCommunity());
            }
        }
    });
}

public void saveData() {
        if (userTel.getText().equals("")) {
            Toast.makeText(this, "Il faut entrer un numéro de téléphone.", Toast.LENGTH_LONG).show();
        } else {
            telData = Integer.parseInt(userTel.getText().toString());
            townData = communitySpinner.getSelectedItemPosition();
            dispoData = userDispo.isChecked();

            ironData = ironing.isChecked();
            houseData = household.isChecked();
            shopData = shopping.isChecked();
            cookData = cooking.isChecked();
            driveData = driving.isChecked();
            gardenData = gardening.isChecked();
            diyData = diy.isChecked();
            workData = diy.isChecked();
            relocationData = relocation.isChecked();
            readingData = reading.isChecked();
            companyData = company.isChecked();
            babysittingData = babysitting.isChecked();
            sewingData = sewing.isChecked();

            Log.d(TAG, "updateLikeInFirebase: idUser " + userId);
            UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Log.d(TAG, "onSuccess: documentSnapshot exists");
                        updateSuperPower(userId);
                        updateDispo(userId);
                        updateTel(userId);
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
}

public void updateDispo(String uid) {
        UserHelper.updateDisponibility(dispoData, uid);
}

public void updateTel(String uid) {
        UserHelper.updateTel(telData, uid);
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
}
