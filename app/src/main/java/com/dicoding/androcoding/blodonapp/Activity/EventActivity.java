package com.dicoding.androcoding.blodonapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.dicoding.androcoding.blodonapp.API.ApiClient;
import com.dicoding.androcoding.blodonapp.API.ApiInterfaces;
import com.dicoding.androcoding.blodonapp.Adapter.AdapterEvent;
import com.dicoding.androcoding.blodonapp.Model.event.Event;
import com.dicoding.androcoding.blodonapp.Model.event.EventData;
import com.dicoding.androcoding.blodonapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<EventData> listEvent = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private FloatingActionButton btntambahevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        btntambahevent = findViewById(R.id.btntambahevent);
        swipeRefreshLayout = findViewById(R.id.swipeevent);
        progressBar = findViewById(R.id.pb_event);

        recyclerView = findViewById(R.id.rv_event);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        //setting swipe refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                SelectEvent();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btntambahevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, EventTambahActivity.class);
                startActivity(intent);
            }
        });

    }

    public void SelectEvent(){

        //setting start progress bar
        progressBar.setVisibility(View.VISIBLE);

        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Event> tampilEvent = apiInterfaces.eventResponse();

        tampilEvent.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                int code = response.body().getCode();
                String description = response.body().getDescription();

                //Toast.makeText(EventActivity.this, "Code : "+code+" | Description : "+description, Toast.LENGTH_SHORT).show();

                listEvent = response.body().getResults();

                adapter = new AdapterEvent(EventActivity.this, listEvent);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                //setting end progress bar
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(EventActivity.this, "Failed to connect the server"+t.getMessage(), Toast.LENGTH_SHORT).show();

                //setting end progress bar
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SelectEvent();
    }
}
