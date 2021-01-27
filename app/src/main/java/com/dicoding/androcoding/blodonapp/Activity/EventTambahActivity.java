package com.dicoding.androcoding.blodonapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dicoding.androcoding.blodonapp.API.ApiClient;
import com.dicoding.androcoding.blodonapp.API.ApiInterfaces;
import com.dicoding.androcoding.blodonapp.Model.event.Event;
import com.dicoding.androcoding.blodonapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventTambahActivity extends AppCompatActivity {

    EditText et_nama_event, et_waktu, et_lokasi;
    Button bttambah_event;
    ImageButton imagePreviewEvent;
    Bitmap bitmap;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    String nama_event,waktu,lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_tambah);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        et_nama_event = findViewById(R.id.et_nama_event);
        et_waktu = findViewById(R.id.et_waktu);
        et_lokasi = findViewById(R.id.et_lokasi);
        bttambah_event = findViewById(R.id.bttambah_event);
        imagePreviewEvent = findViewById(R.id.imagePreviewEvent);

        //cek permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        findViewById(R.id.imagePreviewEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        myCalendar =Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                EditText waktu = findViewById(R.id.et_waktu);
                String myFormat = "yyyy-MMMM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                waktu.setText(sdf.format(myCalendar.getTime()));
            }
        };

        et_waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EventTambahActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        bttambah_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_event = et_nama_event.getText().toString();
                waktu = et_waktu.getText().toString();
                lokasi = et_lokasi.getText().toString();

                if (nama_event.trim().equals("")){
                    et_nama_event.setError("Name Is Required");
                }
                else if (waktu.trim().equals("")){
                    et_waktu.setError("Time Must Be Filled");
                }
                else if (lokasi.trim().equals("")){
                    et_lokasi.setError("Location Is Required");
                }
                else {
                    addEvent();
                }
            }
        });
    }

    private void addEvent(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        final String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("zzzResponse", encoded);

        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Event> insertEvent = apiInterfaces.addEventResponse(nama_event,waktu,lokasi,encoded);

        insertEvent.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful()){

                    onBackPressed();
                }
                int code = response.body().getCode();
                String description = response.body().getDescription();

                //Toast.makeText(EventTambahActivity.this, "Code : "+code+" | Description : "+description, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(EventTambahActivity.this, "Failed to connect the server"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // jika user telah memilih foto, proses menjadi bitmap dan tampilkan dalam preview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                imagePreviewEvent.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
