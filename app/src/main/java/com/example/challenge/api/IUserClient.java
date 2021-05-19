package com.example.challenge.api;

import android.app.Activity;

import com.example.challenge.entities.Login;
import com.example.challenge.entities.User;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public interface IUserClient {

    void logIn(Login login, IAPIListener listener) throws IOException;
    void getAllProfiles(User user, IAPIListener listener) throws IOException;
}
