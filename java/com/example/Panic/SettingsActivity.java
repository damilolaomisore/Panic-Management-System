package com.example.Panic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void settKey(View view){
        Intent intent = new Intent(getApplicationContext(), keyActivity.class);
        startActivity(intent);
    }

    public void settCont(View view){
        Intent intent = new Intent(getApplicationContext(), contActivity.class);
        startActivity(intent);
    }
}