package com.example.challenge.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class UserReturnFail implements Serializable {
    private boolean success;
    private ArrayList<String> errors;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }
}
