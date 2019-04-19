package com.kengoweb.daggerretrofitrxjavamvp.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vokrut on 2019-04-19.
 */

@Module
public class LoginModule {

    @Provides
    public LoginInterface.Presenter provideLoginActivityPresenter(LoginInterface.Model model) {
        return new LoginPresenter(model);
    }

    @Provides
    public LoginInterface.Model provideLoginActivityModel(LoginRepositoryInterface repository) {
        return new LoginModel(repository);
    }

    @Provides
    public LoginRepositoryInterface provideLoginRepositoryInterface() {
        return new MemoryRepository();
    }

}
