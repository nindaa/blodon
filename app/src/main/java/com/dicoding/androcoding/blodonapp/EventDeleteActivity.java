package com.dicoding.androcoding.blodonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EventDeleteActivity extends AppCompatActivity {

    TextView delevent;
    Button btn_delete, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_delete);

        btn_delete = findViewById(R.id.btn_delete);
        btn_cancel = findViewById(R.id.btn_cancel);
        delevent = findViewById(R.id.delevent);
        final Bundle bundle = getIntent().getExtras();
        delevent.setText("Apakah Anda ingin menghapus"+bundle.getString("nama_produk"));

    }
}