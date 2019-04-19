package com.kengoweb.daggerretrofitrxjavamvp.login;

/**
 * Created by vokrut on 2019-04-19.
 */

public class MemoryRepository implements LoginRepositoryInterface {

    private User user;

    @Override
    public User getUser() {

        if (user == null) {
            User user = new User("Thor", "Vokrut");
            user.setId(0);
            this.user = user;
        }

        return this.user;
    }

    @Override
    public void saveUser(User user) {

        if (user == null) {
            user = getUser();
        }

        this.user = user;

    }
}
