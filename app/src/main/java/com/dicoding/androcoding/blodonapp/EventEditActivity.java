package com.dicoding.androcoding.blodonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EventEditActivity extends AppCompatActivity {

    EditText ed_nama_event, ed_waktu, ed_lokasi;
    Button btedit_event;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        ed_nama_event = findViewById(R.id.ed_nama_event);
        ed_waktu = findViewById(R.id.ed_waktu);
        ed_lokasi = findViewById(R.id.ed_lokasi);
        btedit_event = findViewById(R.id.btedit_event);

        final Bundle bundle = getIntent().getExtras();
        ed_nama_event.setText(bundle.getString("nama_event"));
        ed_waktu.setText(bundle.getString("waktu"));
        ed_lokasi.setText(bundle.getString("lokasi"));
        btedit_event = findViewById(R.id.btedit_event);

        myCalendar =Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "yyyy-MMMM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                ed_waktu.setText(sdf.format(myCalendar.getTime()));
            }
        };

        ed_waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EventEditActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btedit_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_event = ed_nama_event.getText().toString();
                String waktu = ed_waktu.getText().toString();
                String lokasi = ed_lokasi.getText().toString();
            }
        });
    }
}
