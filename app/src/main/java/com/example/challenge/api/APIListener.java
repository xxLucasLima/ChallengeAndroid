package com.example.challenge.api;

import com.example.challenge.entities.Profile;
import com.example.challenge.entities.User;

import java.util.ArrayList;
import java.util.List;

public class APIListener implements IAPIListener{
    @Override
    public void onLogin(User user) {

    }
    @Override
    public void onGettingAllProfiles(ArrayList<Profile> profiles) {

    }
    @Override
    public void onUpdatingProfile(boolean isSuccess) {

    }
}
