package com.lsq.easton.mvvm;

import android.app.Application;

public class App extends Application {
    private static App context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
}
