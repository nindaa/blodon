package com.dicoding.androcoding.blodonapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dicoding.androcoding.blodonapp.API.ApiClient;
import com.dicoding.androcoding.blodonapp.API.ApiInterfaces;
import com.dicoding.androcoding.blodonapp.Model.formEvent.FormEvent;
import com.dicoding.androcoding.blodonapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormEventActivity extends AppCompatActivity {

    EditText fe_fullname, fe_bg, fe_gender, fe_age, fe_phone, fe_job;
    Button btregisevent;
    private String fullname, bloodgroup, gender, age, phone, job;
    private int id_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_event);

        fe_fullname = findViewById(R.id.fe_fullname);
        fe_bg = findViewById(R.id.fe_bg);
        fe_gender = findViewById(R.id.fe_gender);
        fe_age = findViewById(R.id.fe_age);
        fe_phone = findViewById(R.id.fe_phone);
        fe_job = findViewById(R.id.fe_job);
        btregisevent = findViewById(R.id.btregisevent);

        btregisevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = fe_fullname.getText().toString();
                bloodgroup = fe_bg.getText().toString();
                gender = fe_gender.getText().toString();
                age = fe_age.getText().toString();
                phone = fe_phone.getText().toString();
                job = fe_job.getText().toString();

                if (fullname.trim().equals("")){
                    fe_fullname.setError("Name Is Required");
                }
                else if (bloodgroup.trim().equals("")){
                    fe_bg.setError("Blood Group Is Required");
                }
                else if (gender.trim().equals("")){
                    fe_gender.setError("Gender Is Required");
                }
                else if (age.trim().equals("")){
                    fe_age.setError("Age Is Required");
                }
                else if (phone.trim().equals("")){
                    fe_phone.setError("Phone Number Must Be Field");
                }
                else if (job.trim().equals("")){
                    fe_job.setError("Job Is Required");
                }
                else {
                    FormEvent();
                }
            }
        });
    }
    private void FormEvent(){
        int id = getIntent().getIntExtra("id",0);
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<FormEvent> simpandata = apiInterfaces.addFormEventResponse(id,fullname,bloodgroup,gender,age,phone,job);

        simpandata.enqueue(new Callback<FormEvent>() {
            @Override
            public void onResponse(Call<FormEvent> call, Response<FormEvent> response) {
                int code = response.body().getCode();
                String description = response.body().getDescription();
                finish();
                Toast.makeText(FormEventActivity.this, "Register is Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FormEvent> call, Throwable t) {
                Toast.makeText(FormEventActivity.this, "Failed to connect the server"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}