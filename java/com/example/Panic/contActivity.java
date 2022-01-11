package com.example.Panic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import static android.content.ContentValues.TAG;

public class contActivity extends AppCompatActivity {

    EditText nameEditText, phoneEditText, messageEditText;
    Button addButton;
    SharedPreferences sp;
    String name, phone, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        messageEditText = findViewById(R.id.messageEditText);
        addButton = findViewById(R.id.addButton);

        sp = getSharedPreferences("UserContact", Context.MODE_PRIVATE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Here we go");
                name = nameEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                message = messageEditText.getText().toString();
                Log.d(TAG, "Lets go");

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("Name", name);
                editor.putString("Phone", phone);
                editor.putString("Message", message);
                editor.commit();
                Log.d(TAG, "Here we go again");

                Toast.makeText(contActivity.this, "Information Saved", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

//                saveNewUser(firstnameEditText.getText().toString(), lastnameEditText.getText().toString(), usernameEditText.getText().toString(), usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
    }

//    public void addButton(View view){
//
//
//        Toast.makeText(this, "Saved " + nameEditText.getText().toString() + " and " + phoneEditText.getText().toString(), Toast.LENGTH_LONG).show();
//
//    }
}