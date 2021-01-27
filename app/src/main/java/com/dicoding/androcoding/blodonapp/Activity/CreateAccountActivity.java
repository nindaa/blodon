package com.dicoding.androcoding.blodonapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.androcoding.blodonapp.API.ApiClient;
import com.dicoding.androcoding.blodonapp.API.ApiInterfaces;
import com.dicoding.androcoding.blodonapp.Model.register.Register;
import com.dicoding.androcoding.blodonapp.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout inpass1;
    private TextInputLayout inconpass;

    EditText infullnm, inuser1, ingoldar;
    TextView tvloginhere;
    Button btregis;
    String full_name,username,blood_group,password,confirm_password;
    ApiInterfaces apiInterfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        infullnm = findViewById(R.id.infullnm);
        inuser1 =  findViewById(R.id.inuser1);
        ingoldar = findViewById(R.id.ingoldar);
        inpass1 = findViewById(R.id.inpass1);
        inconpass = findViewById(R.id.inconpass);

        tvloginhere = findViewById(R.id.tvloginhere);
        tvloginhere.setOnClickListener(this);

        btregis = findViewById(R.id.btregis);
        btregis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btregis:
                full_name = infullnm.getText().toString();
                username = inuser1.getText().toString();
                blood_group = ingoldar.getText().toString();
                password = inpass1.getEditText().getText().toString();
                confirm_password = inconpass.getEditText().getText().toString();

                register(full_name,username,blood_group,password,confirm_password);
                break;
            case R.id.tvloginhere:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void register(String full_name, String username, String blood_group, String password, String confirm_password) {

        apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Register> call = apiInterfaces.registerResponse(full_name,username,blood_group,password,confirm_password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(CreateAccountActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CreateAccountActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(CreateAccountActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
