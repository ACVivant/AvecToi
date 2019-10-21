package com.vivant.annecharlotte.avectoi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.vivant.annecharlotte.avectoi.firestore.BaseActivity;
import com.vivant.annecharlotte.avectoi.firestore.User;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Create new user - launched only if user has accepted charte of application
 */
public class CreateUserActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "CreateUserActivity";
    private String userId;

    private ImageView userImage;
    private TextView userNameTV;
    private TextView userEmailTV;
    private EditText userTel;
    private EditText userTown;
    private EditText userDescription;
    private String telData;
    private String townData;
    private String descriptionData;

    private Button[] buttonTab = new Button[16];
    private boolean[] booleanTab = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    private Context context;

    private List<String> listUserSP = new ArrayList<>();
    private List<Integer> listUserSPInt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_user);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        context = this.getBaseContext();
        userId = this.getCurrentUser().getUid();

        layoutLinks();
        updateUIwhenCreating();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_create_user;
    }


    //----------------------------------------
    // Update UI
    //----------------------------------------

    public void layoutLinks() {
        userImage = findViewById(R.id.create_face);
        userNameTV = findViewById(R.id.create_name);
        userEmailTV = findViewById(R.id.create_email);

        userTel = findViewById(R.id.create_tel);
        userTown = findViewById(R.id.create_town);
        userDescription = findViewById(R.id.create_user_description);

        buttonTab[0] = findViewById(R.id.create_user_SP_0);
        buttonTab[1] = findViewById(R.id.create_user_SP_1);
        buttonTab[2] = findViewById(R.id.create_user_SP_2);
        buttonTab[3] = findViewById(R.id.create_user_SP_3);
        buttonTab[4] = findViewById(R.id.create_user_SP_4);
        buttonTab[5] = findViewById(R.id.create_user_SP_5);
        buttonTab[6] = findViewById(R.id.create_user_SP_6);
        buttonTab[7] = findViewById(R.id.create_user_SP_7);
        buttonTab[8] = findViewById(R.id.create_user_SP_8);
        buttonTab[9] = findViewById(R.id.create_user_SP_9);
        buttonTab[10] = findViewById(R.id.create_user_SP_10);
        buttonTab[11] = findViewById(R.id.create_user_SP_11);
        buttonTab[12] = findViewById(R.id.create_user_SP_12);
        buttonTab[13] = findViewById(R.id.create_user_SP_13);
        buttonTab[14] = findViewById(R.id.create_user_SP_14);
        buttonTab[15] = findViewById(R.id.create_user_SP_15);

        buttonTab[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[0] = actionClick(booleanTab[0], buttonTab[0], 0);
            }
        });

        buttonTab[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[1] = actionClick(booleanTab[1], buttonTab[1], 1);
            }
        });

        buttonTab[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[2] = actionClick(booleanTab[2], buttonTab[2], 2);
            }
        });

        buttonTab[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[3] = actionClick(booleanTab[3], buttonTab[3], 3);
            }
        });

        buttonTab[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[4] = actionClick(booleanTab[4], buttonTab[4], 4);
            }
        });

        buttonTab[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[5] = actionClick(booleanTab[5], buttonTab[5], 5);
            }
        });

        buttonTab[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[6] = actionClick(booleanTab[6], buttonTab[6], 6);
            }
        });

        buttonTab[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[7] = actionClick(booleanTab[7], buttonTab[7], 7);
            }
        });

        buttonTab[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[8] = actionClick(booleanTab[8], buttonTab[8], 8);
            }
        });

        buttonTab[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[9] = actionClick(booleanTab[9], buttonTab[9], 9);
            }
        });

        buttonTab[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[10] = actionClick(booleanTab[10], buttonTab[10], 10);
            }
        });

        buttonTab[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[11] = actionClick(booleanTab[11], buttonTab[11], 11);
            }
        });

        buttonTab[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[12] = actionClick(booleanTab[12], buttonTab[12], 12);
            }
        });

        buttonTab[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[13] = actionClick(booleanTab[13], buttonTab[13], 13);
            }
        });

        buttonTab[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[14] = actionClick(booleanTab[14], buttonTab[14], 14);
            }
        });

        buttonTab[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanTab[15] = actionClick(booleanTab[15], buttonTab[15], 15);
            }
        });
    }

    // add or remove super power of user
    public boolean actionClick(boolean data, Button button, int sp) {

        if (!data) {
            button.setBackground(context.getResources().getDrawable(R.drawable.button_background2));
            listUserSP.add(String.valueOf(sp));
        } else {
            button.setBackground(context.getResources().getDrawable(R.drawable.button_background));
            listUserSP.remove(String.valueOf(sp));
        }
        data =!data;
        return data;
    }

    //update UI when launching activity - datas stored in Firebase authentication (and now Firebase database)
public void updateUIwhenCreating() {

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
        userNameTV.setText(username);
        userEmailTV.setText(email);

        //Update views with super powers data
        launchSuperPowerData(userId);
    }
}

// update UI with datas stored in Firebase Databse
public void launchSuperPowerData(String uid) {

    UserHelper.getUser(uid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            if (documentSnapshot.exists()) {
                listUserSPInt = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserSPList();
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
            }

            String tel =  Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserPhone();
            if (userTel!=null) { userTel.setText(tel);}
            String town = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserTown();
             if (userTown!=null) {userTown.setText(town);}
             String description = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserDescription();
            if (userDescription!=null) {userDescription.setText(description);}
        }
    });
}

    //----------------------------------------
    // Store data
    //----------------------------------------
//save user profile data in Firebase
public void saveData() {

        if (userTel.getText().toString().trim().equals("")||userTown.getText().toString().trim().equals("") || userDescription.getText().toString().trim().equals("") ||
        userTel.getText()==null || userTown.getText()==null || userDescription.getText() ==null) {
            Toast.makeText(this, R.string.phone_or_town_missing, Toast.LENGTH_LONG).show();
        } else {
            if (listUserSP.size() == 0) {
                Toast.makeText(this, R.string.SP_missing, Toast.LENGTH_LONG).show();
            } else {
                telData = userTel.getText().toString();
                townData = userTown.getText().toString();
                descriptionData = userDescription.getText().toString();

                UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            updateSuperPower(userId);
                            updateTelAndTown(userId);
                            updateToken(userId);
                        }
                    }
                });

                launchMainActivity();
            }
        }
}

// Save token (used for notifications)
    private void updateToken(String uid) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(CreateUserActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
                UserHelper.updateUserToken(newToken, userId);
            }
        });

    }

    // save user super power
    public void updateSuperPower(String uid) {
        listUserSPInt = new ArrayList<>();
        for (int i=0;i<listUserSP.size();i++) {
            listUserSPInt.add(Integer.parseInt(listUserSP.get(i)));
    }
        UserHelper.updateUserSPList(listUserSPInt, uid);
}

// update user tel and town
public void updateTelAndTown(String uid) {
        UserHelper.updateTel(telData, uid);
        UserHelper.updateTown(townData, uid);
        UserHelper.updateDescription(descriptionData, uid);
}

    //----------------------------------------
    // Launch Main Activity
    //----------------------------------------

public void launchMainActivity() {
        Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
        startActivity(intent);
}

    //----------------------------------------
    // Menu and adapter
    //----------------------------------------
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
