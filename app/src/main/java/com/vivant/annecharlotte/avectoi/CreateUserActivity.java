package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

public class CreateUserActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    ImageView userImage;
    TextView userNameTV;
    TextView userEmailTV;
    EditText userTel;
    Spinner community;
    Switch userDispo;
    Switch ironing;
    Switch household;
    Switch shopping;
    Switch cooking;
    Switch driving;
    Switch gardening;
    Switch diy;
    Switch works;
    Switch relocation;
    Switch reading;
    Switch company;
    Switch baysitting;
    Switch sewing;
    Button validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        layoutLinks();
        updateUIwhenCreating();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_create_user;
    }

    public void layoutLinks() {
        userImage = findViewById(R.id.create_face);
        userNameTV = findViewById(R.id.create_name);
        userEmailTV = findViewById(R.id.create_email);

        userTel = findViewById(R.id.create_tel);
        userDispo = findViewById(R.id.create_switch_dispo);

        community = findViewById(R.id.create_spinner_community);

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
        baysitting = findViewById(R.id.create_switch_babysitting);
        sewing = findViewById(R.id.create_switch_sewing);

        validate = findViewById(R.id.create_validate_btn);
    }

public void updateUIwhenCreating() {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.create_town, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    community.setAdapter(adapter);
    community.setOnItemSelectedListener(this);

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

        //Update views with data
        this.userNameTV.setText(username);
        this.userEmailTV.setText(email);
    }
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
