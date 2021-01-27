package com.dicoding.androcoding.blodonapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.androcoding.blodonapp.API.ApiClient;
import com.dicoding.androcoding.blodonapp.API.ApiInterfaces;
import com.dicoding.androcoding.blodonapp.Activity.EventActivity;
import com.dicoding.androcoding.blodonapp.Activity.FormEventActivity;
import com.dicoding.androcoding.blodonapp.Model.event.Event;
import com.dicoding.androcoding.blodonapp.Model.event.EventData;
import com.dicoding.androcoding.blodonapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.HolderData> {
    private Context ctx;
    private List<EventData> listEvent;
    private int id_event;

    public AdapterEvent(Context ctx, List<EventData> listEvent) {
        this.ctx = ctx;
        this.listEvent = listEvent;
    }

    @NonNull
    //Data dari item_event ke RecyclerView
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    //memanggil model
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        final EventData eventData = listEvent.get(position);

        holder.ie_id.setText(String.valueOf(eventData.getId_event()));
        holder.ie_namaevent.setText(eventData.getNama_event());
        holder.ie_waktu.setText(eventData.getWaktu());
        holder.ie_lokasi.setText(eventData.getLokasi());
        String urlgambar = "http://192.168.42.76:8012/blodon/images/"+eventData.getFoto();
        Picasso.get().load(urlgambar).into(holder.imgEvent);

         holder.ie_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,FormEventActivity.class);
                intent.putExtra("id",eventData.getId_event());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView ie_id,ie_namaevent,ie_waktu,ie_lokasi;
        Button ie_register;
        ImageView imgEvent;
        String foto;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            ie_id = itemView.findViewById(R.id.ie_id);
            ie_namaevent = itemView.findViewById(R.id.ie_namaevent);
            ie_waktu = itemView.findViewById(R.id.ie_waktu);
            ie_lokasi = itemView.findViewById(R.id.ie_lokasi);
            ie_register = itemView.findViewById(R.id.ie_register);
            imgEvent = itemView.findViewById(R.id.imgEvent);



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogpesan = new AlertDialog.Builder(ctx);
                    dialogpesan.setMessage("Are you sure want to delete?");
                    dialogpesan.setCancelable(true);

                    id_event = Integer.parseInt(ie_id.getText().toString());

                    dialogpesan.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteEvent(id_event, foto);
                            dialogInterface.dismiss();
                            ((EventActivity)ctx).SelectEvent();
                        }
                    });

                    dialogpesan.show();

                    return false;
                }
            });
        }

        private void deleteEvent(int id_event, String foto){
            ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
            Call<Event> hapusEvent = apiInterfaces.delEventResponse(id_event,foto);

            hapusEvent.enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    int code = response.body().getCode();
                    String description = response.body().getDescription();

                    Toast.makeText(ctx, "Code : "+code+" | Description : "+description, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    Toast.makeText(ctx, "Failed to connect the server"+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
