package com.kengoweb.daggerretrofitrxjavamvp.login;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by vokrut on 2019-04-19.
 */

public class LoginPresenter implements LoginInterface.Presenter {

    @Nullable
    private LoginInterface.View view;
    private LoginInterface.Model model;

    public LoginPresenter(LoginInterface.Model model) {
        this.model = model;
    }

    @Override
    public void setView(@Nullable LoginInterface.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {

        if (view != null) {
            if (TextUtils.isEmpty(view.getFirstName()) || TextUtils.isEmpty(view.getLastName())) {
                view.showInputError();
            } else {
                model.createUser(view.getFirstName(), view.getLastName());
                view.showUserSavedMessage();
            }
        }

    }

    @Override
    public void getCurrentUser() {

        User user = model.getUser();

        if (user != null) {
            if (view != null) {
                view.setFirstName(user.getFirstName());
                view.setLastName(user.getLastName());
            }
        } else {
            if (view != null) {
                view.showUserNotAvailable();
            }
        }

    }
}
