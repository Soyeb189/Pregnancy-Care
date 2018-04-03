package com.muktadir.pregnancycare;


import android.app.Application;
import android.content.Context;

import com.muktadir.pregnancycare.Helper.LocaleHelper;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
