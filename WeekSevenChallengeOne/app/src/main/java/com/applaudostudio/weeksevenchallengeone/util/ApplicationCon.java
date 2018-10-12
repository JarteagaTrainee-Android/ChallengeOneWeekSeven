package com.applaudostudio.weeksevenchallengeone.util;

import android.app.Application;
import android.content.Context;

public class ApplicationCon extends Application {

    public static ApplicationCon app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Context getContext() {
        return app;
    }
}
