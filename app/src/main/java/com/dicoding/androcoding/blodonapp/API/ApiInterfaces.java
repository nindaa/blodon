package com.dicoding.androcoding.blodonapp.API;

import com.dicoding.androcoding.blodonapp.Model.event.Event;
import com.dicoding.androcoding.blodonapp.Model.formEvent.FormEvent;
import com.dicoding.androcoding.blodonapp.Model.login.Login;
import com.dicoding.androcoding.blodonapp.Model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterfaces {
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(
            @Field("full_name") String full_name,
            @Field("username") String username,
            @Field("blood_group") String blood_group,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password
    );

    @GET("selectall.php")
    Call<Event> eventResponse();

    @FormUrlEncoded
    @POST("insert.php")
    Call<Event> addEventResponse(
            @Field("nama_event") String nama_event,
            @Field("waktu") String waktu,
            @Field("lokasi") String lokasi,
            @Field("fileUpload") String encoded
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<Event> delEventResponse(
            @Field("id_event") int id_event,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("insertformevent.php")
    Call<FormEvent> addFormEventResponse(
            @Field("id_event") int id_event,
            @Field("full_name") String full_name,
            @Field("blood_group") String blood_group,
            @Field("gender") String gender,
            @Field("age") String age,
            @Field("phone_number") String phone_number,
            @Field("job") String job
    );

}
