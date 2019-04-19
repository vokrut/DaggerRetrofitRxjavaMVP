package com.kengoweb.daggerretrofitrxjavamvp.login;

/**
 * Created by vokrut on 2019-04-19.
 */

public class LoginModel implements LoginInterface.Model {

    private LoginRepositoryInterface repositoryInterface;

    public LoginModel(LoginRepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void createUser(String firstName, String lastName) {
        repositoryInterface.saveUser(new User(firstName, lastName));
    }

    @Override
    public User getUser() {
        return repositoryInterface.getUser();
    }
}
