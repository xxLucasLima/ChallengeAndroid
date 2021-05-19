package com.example.challenge.model;

import android.app.Application;

import com.example.challenge.api.IAPIListener;
import com.example.challenge.api.UserClient;
import com.example.challenge.entities.Login;
import com.example.challenge.entities.Profile;
import com.example.challenge.entities.ProfileUpdate;
import com.example.challenge.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model sInstance;
    private final UserClient mUserClient;

    public static Model getInstance(Application application){
        if(sInstance == null){
            sInstance = new Model(application);
        }
        return sInstance;
    }

    private Model (Application application){
        mApplication = application;
        mUserClient = new UserClient(mApplication);
    }

    private final Application mApplication;
    public Application getApplication(){ return mApplication; }

    public void logIn (Login login, IAPIListener listener) throws IOException {
        mUserClient.logIn(login, listener);
    }

    public void getAllProfiles (User user, IAPIListener listener) throws IOException {
        mUserClient.getAllProfiles(user, listener);
    }

    public void updateProfile(ProfileUpdate profileUpdate, User user, String idUser, IAPIListener listener)throws IOException {
        mUserClient.updateProfile(profileUpdate, user,idUser, listener);
    }

}
