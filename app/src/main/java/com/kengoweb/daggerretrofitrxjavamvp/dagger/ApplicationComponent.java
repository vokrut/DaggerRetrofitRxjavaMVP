package com.kengoweb.daggerretrofitrxjavamvp.dagger;

import com.kengoweb.daggerretrofitrxjavamvp.login.LoginActivity;
import com.kengoweb.daggerretrofitrxjavamvp.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);

}
