package com.kengoweb.daggerretrofitrxjavamvp;

import android.app.Application;

import com.kengoweb.daggerretrofitrxjavamvp.dagger.ApplicationComponent;
import com.kengoweb.daggerretrofitrxjavamvp.dagger.ApplicationModule;
import com.kengoweb.daggerretrofitrxjavamvp.dagger.DaggerApplicationComponent;
import com.kengoweb.daggerretrofitrxjavamvp.login.LoginModule;

public class MyApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .loginModule(new LoginModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
