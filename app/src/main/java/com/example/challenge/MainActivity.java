package com.example.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.challenge.Helper.ThreadHelper;
import com.example.challenge.adapters.UsersRecyclerViewAdapter;
import com.example.challenge.api.APIListener;
import com.example.challenge.dialogs.LoadingDialog;
import com.example.challenge.entities.Login;
import com.example.challenge.entities.Profile;
import com.example.challenge.entities.User;
import com.example.challenge.model.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    TextView myText;
    EditText emailText;
    EditText passwordText;
    Button myButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Login");

        myText = findViewById(R.id.TextId);
        myButton = findViewById(R.id.ButtonId);
        emailText = findViewById(R.id.editTextTextEmailAddress);
        passwordText = findViewById(R.id.editTextTextPassword);
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login login = GetLogin();

                Model model = Model.getInstance(MainActivity.this.getApplication());
                loadingDialog.startLoadingDialog();

                try {
                    model.logIn(login, new APIListener() {
                        @Override
                        public void onLogin(User user) {
                            if (user.getSuccess()) {
                                try {
                                    model.getAllProfiles(user, new APIListener() {
                                        @Override
                                        public void onGettingAllProfiles(ArrayList<Profile> profiles) {
                                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                            intent.putExtra("loggedUser", user);
                                            ArrayList<Profile> mProfiles = new ArrayList(profiles);
                                            intent.putExtra("profilesList", mProfiles);
                                            startActivity(intent);
                                            loadingDialog.dismissDialog();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                loadingDialog.dismissDialog();
                                ThreadHelper helper = new ThreadHelper();
                                helper.ToastMessageHandler(getApplicationContext(), user.getErrors().get(0));
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Login GetLogin() {
        Login login = new Login();
        login.email = emailText.getText().toString();
        login.password = passwordText.getText().toString();

        return login;
    }
}
