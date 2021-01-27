package com.dicoding.androcoding.blodonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private CircleImageView circleImageView;

    Button bt_done,ep_bt_ubah;
    EditText ep_fullnm, ep_username,ep_gender,ep_dateofbirth,ep_phonenumber,ep_email,ep_address,ep_bloodgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bt_done = findViewById(R.id.bt_done);
        ep_bt_ubah = findViewById(R.id.ep_bt_ubah);
        ep_fullnm = findViewById(R.id.ep_fullnm);
        ep_username = findViewById(R.id.ep_username);
        ep_gender = findViewById(R.id.ep_gender);
        ep_dateofbirth = findViewById(R.id.ep_dateofbirth);
        ep_phonenumber = findViewById(R.id.ep_phonenumber);
        ep_email = findViewById(R.id.ep_email);
        ep_address = findViewById(R.id.ep_address);
        ep_bloodgroup = findViewById(R.id.ep_bloodgroup);

        final Bundle bundle = getIntent().getExtras();
        ep_fullnm.setText(bundle.getString("full_name"));
        ep_username.setText(bundle.getString("usernamee"));
        ep_gender.setText(bundle.getString("gender"));
        ep_dateofbirth.setText(bundle.getString("date_of_birth"));
        ep_phonenumber.setText(bundle.getString("phone_number"));
        ep_email.setText(bundle.getString("email"));
        ep_address.setText(bundle.getString("address"));
        ep_bloodgroup.setText(bundle.getString("blood_group"));
    }

}
