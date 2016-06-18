package net.onesandzeros.zero.photocast.presentation.activity;

import android.app.Application;

import net.onesandzeros.zero.photocast.utils.VolleyLib;

public class MyApplication extends Application {

    private static MyApplication mMyApplication;

    public MyApplication() {
        mMyApplication = this;
    }

    public static MyApplication getMyApplicationContext() {
        return mMyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        VolleyLib.init(this);
    }
}
