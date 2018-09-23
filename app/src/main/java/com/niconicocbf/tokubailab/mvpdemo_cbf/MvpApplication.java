package com.niconicocbf.tokubailab.mvpdemo_cbf;

import android.app.Application;

public class MvpApplication extends Application {
    private static MvpApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MvpApplication getInstance(){
        return instance;
    }
}

