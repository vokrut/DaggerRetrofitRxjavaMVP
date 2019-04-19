package com.kengoweb.daggerretrofitrxjavamvp.login;

/**
 * Created by vokrut on 2019-04-19.
 */

public interface LoginInterface {

    interface View {

        String getFirstName();
        String getLastName();

        void showUserNotAvailable();
        void showInputError();
        void showUserSavedMessage();

        void setFirstName(String firstName);
        void setLastName(String lastName);

    }

    interface Presenter {

        void setView(LoginInterface.View view);

        void loginButtonClicked();

        void getCurrentUser();

    }

    interface Model {

        void createUser(String firstName, String lastName);

        User getUser();

    }

}
