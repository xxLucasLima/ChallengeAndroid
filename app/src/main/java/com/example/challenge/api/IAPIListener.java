package com.example.challenge.api;

import com.example.challenge.entities.Profile;
import com.example.challenge.entities.User;

import java.util.ArrayList;
import java.util.List;

public interface IAPIListener {
    void onLogin(User user);
    void onGettingAllProfiles(ArrayList<Profile> profiles);
    void onUpdatingProfile(boolean isSuccess);
}
