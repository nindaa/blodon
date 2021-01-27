package com.dicoding.androcoding.blodonapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dicoding.androcoding.blodonapp.EditProfileActivity;
import com.dicoding.androcoding.blodonapp.R;
import com.dicoding.androcoding.blodonapp.SessionManager;

public class HomeActivity extends AppCompatActivity {

    ImageButton imgbt1, imgbt2, imgbt3, imgbt4;
    TextView welcome;
    Button bt_editprofile;
    SessionManager sessionManager;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(HomeActivity.this);
        if (!sessionManager.IsLoggedIn()){
            moveToLogin();
        }

        imgbt1 = findViewById(R.id.imgbt1);
        imgbt2 = findViewById(R.id.imgbt2);
        imgbt3 = findViewById(R.id.imgbt3);
        imgbt4 = findViewById(R.id.imgbt4);
        bt_editprofile = findViewById(R.id.bt_editprofile);
        welcome = findViewById(R.id.welcome);

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        welcome.setText("Hi! "+username);

        imgbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });

        imgbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NeedBloodActivity.class);
            }
        });

        imgbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EducationActivity.class);
                startActivity(intent);
            }
        });

        imgbt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        bt_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionlogout:
                sessionManager.LogoutSession();
                moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }
}
