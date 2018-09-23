package com.niconicocbf.tokubailab.mvpdemo_cbf.utils;

import android.widget.Toast;

import com.niconicocbf.tokubailab.mvpdemo_cbf.MvpApplication;

public class ToastUtils {
    public static void showToast(String msg){
        Toast.makeText(MvpApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
}
