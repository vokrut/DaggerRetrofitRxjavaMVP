package com.kengoweb.daggerretrofitrxjavamvp.login;

/**
 * Created by vokrut on 2019-04-19.
 */

public interface LoginRepositoryInterface {

    User getUser();

    void saveUser(User user);

}
