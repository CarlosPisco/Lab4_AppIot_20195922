package com.example.contactos_y_sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.contactos_y_sensores.databinding.ActivityMainBinding;
import com.example.contactos_y_sensores.services.ProfileService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.button.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
        });



    }
}