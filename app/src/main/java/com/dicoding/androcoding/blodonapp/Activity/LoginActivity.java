package com.dicoding.androcoding.blodonapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.dicoding.androcoding.blodonapp.API.ApiClient;
import com.dicoding.androcoding.blodonapp.API.ApiInterfaces;
import com.dicoding.androcoding.blodonapp.Model.login.Login;
import com.dicoding.androcoding.blodonapp.Model.login.LoginData;
import com.dicoding.androcoding.blodonapp.R;
import com.dicoding.androcoding.blodonapp.SessionManager;
import com.google.android.material.textfield.TextInputLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout inuser;
    private  TextInputLayout inpass;

    Button btlogin, btcreate;
    ApiInterfaces apiInterfaces;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inuser = findViewById(R.id.inuser);
        inpass = findViewById(R.id.inpass);

        btlogin = findViewById(R.id.btlogin);
        btlogin.setOnClickListener(this);

        btcreate = findViewById(R.id.btcreate);
        btcreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btlogin:
                String username = inuser.getEditText().getText().toString();
                String password = inpass.getEditText().getText().toString();

                login(username,password);
                break;
            case R.id.btcreate:
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
        }
    }

    private void login(String username, String password) {

        apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Login> loginCall = apiInterfaces.loginResponse(username,password);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){

                    sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getData();
                    sessionManager.createLoginSession(loginData);

                    Toast.makeText(LoginActivity.this, response.body().getData().getUsername(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("inuser",inuser.getEditText().getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
