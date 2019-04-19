package com.kengoweb.daggerretrofitrxjavamvp.dagger;

import com.kengoweb.daggerretrofitrxjavamvp.login.LoginActivity;
import com.kengoweb.daggerretrofitrxjavamvp.login.LoginModule;
import com.kengoweb.daggerretrofitrxjavamvp.twitch.TwitchActivity;
import com.kengoweb.daggerretrofitrxjavamvp.twitch.TwitchModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class, TwitchModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);
    void inject(TwitchActivity target);

}
