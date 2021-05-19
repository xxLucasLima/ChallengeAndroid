package com.example.challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.challenge.Helper.ThreadHelper;
import com.example.challenge.api.APIListener;
import com.example.challenge.api.IAPIListener;
import com.example.challenge.dialogs.LoadingDialog;
import com.example.challenge.entities.Profile;
import com.example.challenge.entities.ProfileUpdate;
import com.example.challenge.entities.User;
import com.example.challenge.model.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {
    private static Context mContext;

    ImageView mainAvatarView;
    TextInputLayout firstName, lastName;
    EditText birthDate;
    TextView id;
    Button buttonSave;

    User userRow;
    User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        MainActivity3.mContext = getApplicationContext();

        mainAvatarView = findViewById(R.id.avatar_form);
        buttonSave = findViewById(R.id.buttonSave);
        firstName = findViewById(R.id.firstName_form);
        lastName = findViewById(R.id.lastName_form);
        id = findViewById(R.id.id_form);
        birthDate = findViewById(R.id.birthDay_form);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONDAY);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        birthDate.setFocusable(false);
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity3.this);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileUpdate profileUpdate = getSavedData();
                String idEditText = id.getText().toString();

                Model model = Model.getInstance(MainActivity3.this.getApplication());
                loadingDialog.startLoadingDialog();

                try {
                    model.updateProfile(profileUpdate, loggedUser, idEditText, new APIListener() {
                        @Override
                        public void onUpdatingProfile(boolean isSuccess) {
                            if(isSuccess){
                                try {
                                    model.getAllProfiles(loggedUser, new APIListener() {
                                        @Override
                                        public void onGettingAllProfiles(ArrayList<Profile> profiles) {
                                            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                                            intent.putExtra("loggedUser", loggedUser);
                                            intent.putExtra("profilesList", profiles);
                                            startActivity(intent);
                                            loadingDialog.dismissDialog();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                ThreadHelper helper = new ThreadHelper();
                                helper.ToastMessageHandler(getApplicationContext(), "Profile updated successfully!");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        birthDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity3.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String date = dayOfMonth + "/" + month + "/" + year;
                            birthDate.setText(date);
                        }
                    }, year, month, day);
            datePickerDialog.show();

        });
        getIntentsData();
        setDataOnForm();

    }

    private ProfileUpdate getSavedData() {
        ProfileUpdate profileUpdate = new ProfileUpdate();
        profileUpdate.setBirth_date(birthDate.getText().toString());
        profileUpdate.setFirst_name(firstName.getEditText().getText().toString());
        profileUpdate.setLast_name(lastName.getEditText().getText().toString());

        return profileUpdate;
    }

    private void VerifyUserIsTheSameLogged(User loggedUser, User userRow) {
        if (loggedUser.getProfile().getId() != userRow.getProfile().getId()) {
            buttonSave.setEnabled(false);
            firstName.setEnabled(false);
            lastName.setEnabled(false);
            birthDate.setEnabled(false);
        } else {
            buttonSave.setEnabled(true);
            firstName.setEnabled(true);
            lastName.setEnabled(true);
            birthDate.setEnabled(true);
        }
    }

    private void getIntentsData() {

        userRow = new User();
        loggedUser = new User();

        userRow.setProfile(new Profile());
        if (getIntent().hasExtra("id"))
            userRow.getProfile().setId(getIntent().getIntExtra("id", 0));
        if (getIntent().hasExtra("first_name"))
            userRow.getProfile().setFirst_name(getIntent().getStringExtra("first_name"));
        if (getIntent().hasExtra("last_name"))
            userRow.getProfile().setLast_name(getIntent().getStringExtra("last_name"));
        if (getIntent().hasExtra("birth_date"))
            userRow.getProfile().setBirth_date(getIntent().getStringExtra("birth_date"));
        if (getIntent().hasExtra("avatar"))
            userRow.getProfile().setAvatar(getIntent().getStringExtra("avatar"));

        loggedUser = (User) getIntent().getSerializableExtra("loggedUser");

        VerifyUserIsTheSameLogged(loggedUser, userRow);

    }

    private void setDataOnForm() {
        Glide.with(mContext)
                .load(userRow.getProfile().getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mainAvatarView);

        firstName.getEditText().setText(userRow.getProfile().getFirst_name());
        lastName.getEditText().setText(userRow.getProfile().getLast_name());
        birthDate.setText(userRow.getProfile().getBirth_date());
        id.setText("" + userRow.getProfile().getId());

    }
}