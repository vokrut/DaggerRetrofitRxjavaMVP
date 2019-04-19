package com.kengoweb.daggerretrofitrxjavamvp.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kengoweb.daggerretrofitrxjavamvp.MyApplication;
import com.kengoweb.daggerretrofitrxjavamvp.R;
import com.kengoweb.daggerretrofitrxjavamvp.twitch.TwitchActivity;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginInterface.View {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    LoginInterface.Presenter presenter;

    private EditText etFirstName;
    private EditText etLastName;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((MyApplication) getApplication()).getComponent().inject(this);

        etFirstName = findViewById(R.id.et_login_firstname);
        etLastName = findViewById(R.id.et_login_lastname);
        btnLogin = findViewById(R.id.btn_login_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginButtonClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    @Override
    public String getFirstName() {
        return etFirstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return etLastName.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "Error the user is not available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "First name or Last name cannot be empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSavedMessage() {
        Toast.makeText(this, "User saved!", Toast.LENGTH_SHORT).show();
        startTwitchActivity();
    }

    @Override
    public void setFirstName(String firstName) {
        etFirstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        etLastName.setText(lastName);
    }

    private void startTwitchActivity() {
        Intent intent = new Intent(this, TwitchActivity.class);
        startActivity(intent);
        finish();
    }
}
