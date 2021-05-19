package com.example.challenge.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.challenge.R;

public class LoadingDialog {

    private Activity mActivity;
    private AlertDialog mDialog;

    public LoadingDialog (Activity activity){
        mActivity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        builder.setView(R.layout.loading_dialog);
        builder.setCancelable(true);

        mDialog = builder.create();
        mDialog.show();
    }

    public void dismissDialog(){
        mDialog.dismiss();
    }
}
