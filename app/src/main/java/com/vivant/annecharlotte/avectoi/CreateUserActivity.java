package com.vivant.annecharlotte.avectoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateUserActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "CreateUserActivity";
    private String userId;

    private ImageView userImage;
    private TextView userNameTV;
    private TextView userEmailTV;
    private EditText userTel;
    private EditText userTown;
    private String telData;
    private String townData;

    private Button[] buttonTab = new Button[16];
    private boolean[] booleanTab = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    private Context context;

    List<String> listUserSP = new ArrayList<>();
    List<Integer> listUserSPInt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
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

    public void layoutLinks() {
        Log.d(TAG, "layoutLinks");
        userImage = findViewById(R.id.create_face);
        userNameTV = findViewById(R.id.create_name);
        userEmailTV = findViewById(R.id.create_email);

        userTel = findViewById(R.id.create_tel);
        userTown = findViewById(R.id.create_town);

        buttonTab[0] = findViewById(R.id.create_user_SP_ironing);
        buttonTab[1] = findViewById(R.id.create_user_SP_household);
        buttonTab[2] = findViewById(R.id.create_user_SP_shopping);
        buttonTab[3] = findViewById(R.id.create_user_SP_cooking);
        buttonTab[4] = findViewById(R.id.create_user_SP_driving);
        buttonTab[5] = findViewById(R.id.create_user_SP_gardening);
        buttonTab[6] = findViewById(R.id.create_user_SP_diy);
        buttonTab[7] = findViewById(R.id.create_user_SP_works);
        buttonTab[8] = findViewById(R.id.create_user_SP_relocation);
        buttonTab[9] = findViewById(R.id.create_user_SP_reading);
        buttonTab[10] = findViewById(R.id.create_user_SP_babysitting);
        buttonTab[11] = findViewById(R.id.create_user_SP_sewing);
        buttonTab[12] = findViewById(R.id.create_user_SP_flowering);
        buttonTab[13] = findViewById(R.id.create_user_SP_tutoring);
        buttonTab[14] = findViewById(R.id.create_user_SP_company);
        buttonTab[15] = findViewById(R.id.create_user_SP_admin);

/*        for (int i = 0; i< stringTab.length; i++) { // ne fonctionne pas parce que i doit être déclaré final
            buttonTab[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    booleanTab[i] = actionClick(booleanTab[i], buttonTab[i], i);
                }
            });
        }*/


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

    public boolean actionClick(boolean data, Button button, int sp) {
        Log.d(TAG, "actionClick: listUserSP " + listUserSP);
        Log.d(TAG, "actionClick: sp "+sp);
        Log.d(TAG, "actionClick: data "+ data);
        if (!data) {
            button.setBackground(context.getResources().getDrawable(R.drawable.button_background2));
            listUserSP.add(String.valueOf(sp));
            Log.d(TAG, "actionClick: listUserSP add " + listUserSP);
        } else {
            button.setBackground(context.getResources().getDrawable(R.drawable.button_background));
            listUserSP.remove(String.valueOf(sp));
            Log.d(TAG, "actionClick: listUserSP remove " + listUserSP);
        }
        data =!data;
        Log.d(TAG, "actionClick: data " +data);
        return data;
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
        userNameTV.setText(username);
        userEmailTV.setText(email);

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
                listUserSPInt = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserSPList();
                for (int i=0; i<listUserSPInt.size(); i++) {
                    listUserSP.add(String.valueOf(listUserSPInt.get(i)));
                }
                for (int i=0; i<booleanTab.length; i++) {
                    if (listUserSP.contains(String.valueOf(i))) {
                        Log.d(TAG, "onSuccess: contains");
                        buttonTab[i].setBackground(getResources().getDrawable(R.drawable.button_background2));
                        booleanTab[i]=true;
                    } else {
                        Log.d(TAG, "onSuccess: not contains");
                        buttonTab[i].setBackground(getResources().getDrawable(R.drawable.button_background));
                        booleanTab[i]=false;
                    }
                }
                Log.d(TAG, "onSuccess: booleanData " + booleanTab);
            }

            String tel =  Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserPhone();
            if (userTel!=null) { userTel.setText(tel);}
            String town = Objects.requireNonNull(documentSnapshot.toObject(User.class)).getUserTown();
             if (userTown!=null) {userTown.setText(town);}
        }
    });
}

public void saveData() {
    Log.d(TAG, "saveData: userTel " + userTel.getText().toString());
        if (userTel.getText().toString().equals("")||userTown.getText().toString().equals("")) {
            Toast.makeText(this, R.string.phone_or_town_missing, Toast.LENGTH_LONG).show();
        } else {
            if (listUserSP.size() == 0) {
                Toast.makeText(this, R.string.SP_missing, Toast.LENGTH_LONG).show();
            } else {
                telData = userTel.getText().toString();
                townData = userTown.getText().toString();

                Log.d(TAG, "updateLikeInFirebase: idUser " + userId);
                UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Log.d(TAG, "onSuccess: documentSnapshot exists");
                            updateSuperPower(userId);
                            updateTelAndTown(userId);
                        }
                    }
                });

                launchMainActivity();
            }
        }
}

public void updateSuperPower(String uid) {
        listUserSPInt = new ArrayList<>();
        for (int i=0;i<listUserSP.size();i++) {
            listUserSPInt.add(Integer.parseInt(listUserSP.get(i)));
    }
        UserHelper.updateUserSPList(listUserSPInt, uid);
}

public void updateTelAndTown(String uid) {
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
