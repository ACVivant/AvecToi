package com.vivant.annecharlotte.avectoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vivant.annecharlotte.avectoi.firestore.SosEventHelper;
import com.vivant.annecharlotte.avectoi.firestore.UserHelper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "CreateEventActivity";

    private Spinner themeSpinner;
    private ImageButton dateButton;
    private TextView dateText;
    private EditText description;
    private EditText town;
    private NumberPicker np;

    private String descriptionToSave;
    private String dateToShow;
    private String townToSave;
    private Date dateToSave;
    private Date dateToday;
    private int themeToSave;
    private int numberOfHeroToSave;
    private String eventId;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close); // Pour mettre une petite croix à la place de la petite flèche en haut à gauche

        // NUMBER OF HEROS
        np = (NumberPicker) findViewById(R.id.np);
        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
       np.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                numberOfHeroToSave = newVal;
            }
        });

        // SPINNER THEME
        themeSpinner = findViewById(R.id.event_theme_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_theme, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);
        themeSpinner.setOnItemSelectedListener(this);

        // DATE PICKER
        dateButton = (ImageButton) findViewById(R.id.event_date_open);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        dateText = (TextView)findViewById(R.id.event_date);

        // DESCRIPTION
        description = (EditText) findViewById(R.id.event_description);
        town = (EditText) findViewById(R.id.event_town);

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

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_create_event;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        dateToSave = c.getTime();
        dateToShow = DateFormat.getDateInstance().format(dateToSave);
        dateText.setText(dateToShow);
    }

    public void saveData() {
        descriptionToSave = description.getText().toString();
        themeToSave = themeSpinner.getSelectedItemPosition();
        townToSave = town.getText().toString();
        // la date de l'événement et le nombre de personnes sont déjà enregistrés via les méthodes d'affichage

        userId = this.getCurrentUser().getUid();
        dateToday = new Date();

        if (descriptionToSave.trim().isEmpty() || townToSave.trim().isEmpty()) {
            Toast.makeText(this, R.string.event_info_missing, Toast.LENGTH_LONG).show();
        } else {
            createEvent();
        }

    }

    public void createEvent() {
        Log.d(TAG, "createEvent");
        eventId = userId + dateToday;
        SosEventHelper.createEvent(eventId,themeToSave, descriptionToSave,townToSave, numberOfHeroToSave, userId, dateToday, dateToSave).addOnFailureListener(this.onFailureListener());
        finish();
       // Intent intent = new Intent(this, MainActivity.class);
       // startActivity(intent);
    }

    public void resetNewEvent() {
        Toast.makeText(this, "annulation", Toast.LENGTH_LONG).show();
    }
}
