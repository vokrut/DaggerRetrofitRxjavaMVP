package com.kengoweb.daggerretrofitrxjavamvp;

import com.kengoweb.daggerretrofitrxjavamvp.login.LoginInterface;
import com.kengoweb.daggerretrofitrxjavamvp.login.LoginPresenter;
import com.kengoweb.daggerretrofitrxjavamvp.login.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by vokrut on 2019-04-19.
 */

public class PresenterTests {

    private static final String USER_FIRST_NAME = "Thor";
    private static final String USER_LAST_NAME = "Vokrut";

    LoginInterface.Model mockLoginModel;
    LoginInterface.View mockView;
    LoginPresenter presenter;
    User user;

    @Before
    public void setup() {

        mockLoginModel = mock(LoginInterface.Model.class);

        user = new User(USER_FIRST_NAME, USER_LAST_NAME);

        mockView = mock(LoginInterface.View.class);

        presenter = new LoginPresenter(mockLoginModel);

        presenter.setView(mockView);

    }

    @Test
    public void shouldShowErrorWhenUserIsNull() {

        when(mockLoginModel.getUser()).thenReturn(null);

        presenter.getCurrentUser();

        verify(mockLoginModel, times(1)).getUser();

        verify(mockView, never()).setFirstName(USER_FIRST_NAME);
        verify(mockView, never()).setLastName(USER_LAST_NAME);
        verify(mockView, times(1)).showUserNotAvailable();

    }

    @Test
    public void loadTheUserFromTheRepositoryWhenValidUserIsPresent() {

        when(mockLoginModel.getUser()).thenReturn(user);

        presenter.getCurrentUser();

        verify(mockLoginModel, times(1)).getUser();

        verify(mockView, times(1)).setFirstName(USER_FIRST_NAME);
        verify(mockView, times(1)).setLastName(USER_LAST_NAME);
        verify(mockView, never()).showUserNotAvailable();

    }

    @Test
    public void shouldCreateErrorMessageWhenIfFirstNameIsEmpty() {

        when(mockView.getFirstName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockView, times(1)).getFirstName();
        verify(mockView, never()).getLastName();
        verify(mockView, times(1)).showInputError();

    }

    @Test
    public void shouldCreateErrorMessageWhenIfLastNameIsEmpty() {

        when(mockView.getFirstName()).thenReturn(USER_FIRST_NAME);
        when(mockView.getLastName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockView, times(1)).getFirstName();
        verify(mockView, times(1)).getLastName();
        verify(mockView, times(1)).showInputError();

    }

    @Test
    public void shouldBeAbleToSaveAValidUser() {

        when(mockView.getFirstName()).thenReturn(USER_FIRST_NAME);
        when(mockView.getLastName()).thenReturn(USER_LAST_NAME);

        presenter.loginButtonClicked();

        verify(mockView, times(2)).getFirstName();
        verify(mockView, times(2)).getLastName();
        verify(mockView, never()).showInputError();

        verify(mockLoginModel, times(1)).createUser(USER_FIRST_NAME, USER_LAST_NAME);

        verify(mockView, times(1)).showUserSavedMessage();

    }

}
