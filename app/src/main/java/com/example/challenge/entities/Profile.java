package com.example.challenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Profile implements Serializable {
    private int user_id;
    private int id;
    private String avatar;
    private String first_name;
    private String last_name;
    private String birth_date;
    private String gender;
    private String should_use_metric_system;
    private String height;
    private String weight;
    private String eating_style;
    private String activity_level;
    private String goal;
    private String created_at;
    private String updated_at;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getShould_use_metric_system() {
        return should_use_metric_system;
    }

    public void setShould_use_metric_system(String should_use_metric_system) {
        this.should_use_metric_system = should_use_metric_system;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEating_style() {
        return eating_style;
    }

    public void setEating_style(String eating_style) {
        this.eating_style = eating_style;
    }

    public String getActivity_level() {
        return activity_level;
    }

    public void setActivity_level(String activity_level) {
        this.activity_level = activity_level;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    @JsonIgnore
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
