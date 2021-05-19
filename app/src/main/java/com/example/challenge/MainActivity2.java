package com.example.challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.challenge.adapters.UsersRecyclerViewAdapter;
import com.example.challenge.api.APIListener;
import com.example.challenge.entities.Profile;
import com.example.challenge.entities.User;
import com.example.challenge.model.Model;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {
    TextView myText;
    RecyclerView recyclerView;
    List<User> mUsers;
    User loggedUser;
    ArrayList<Profile> profilesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("Profiles");

        Intent i = getIntent();

        Model model = Model.getInstance(MainActivity2.this.getApplication());

        loggedUser = (User) i.getSerializableExtra("loggedUser");
        profilesList = (ArrayList<Profile>) i.getSerializableExtra("profilesList");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UsersRecyclerViewAdapter adapter = new UsersRecyclerViewAdapter(this, profilesList, loggedUser);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}