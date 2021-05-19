package com.example.challenge.entities;

import com.example.challenge.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsersListHolder implements Serializable {

    private ArrayList<Profile> profiles;

    public @JsonProperty("data")
    ArrayList<Profile> getProfiles() {
        return profiles;
    }

    @JsonIgnore
    public void setUsers(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

}