package com.example.Panic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class keyActivity extends AppCompatActivity {

    EditText robberyEditText, kidnappingEditText, fireEditText, medEditText, rapeEditText, assaultEditText, disasterEditText, accidentEditText, explosionEditText;
    Button saveButton;
    SharedPreferences sp;
    String robbery, kidnapping, fire, med, rape, assault, disaster, accident, explosion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        robberyEditText = findViewById(R.id.robberyEditText);
        kidnappingEditText = findViewById(R.id.kidnappingEditText);
        fireEditText = findViewById(R.id.fireEditText);
        medEditText = findViewById(R.id.medEditText);
        rapeEditText = findViewById(R.id.rapeEditText);
        assaultEditText = findViewById(R.id.assaultEditText);
        disasterEditText = findViewById(R.id.disasterEditText);
        accidentEditText = findViewById(R.id.accidentEditText);
        explosionEditText = findViewById(R.id.explosionEditText);
        saveButton = findViewById(R.id.saveButton);

        sp = getSharedPreferences("UserKeywords", Context.MODE_PRIVATE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robbery = robberyEditText.getText().toString();
                kidnapping = kidnappingEditText.getText().toString();
                fire = fireEditText.getText().toString();
                med = medEditText.getText().toString();
                rape = rapeEditText.getText().toString();
                assault = assaultEditText.getText().toString();
                disaster = disasterEditText.getText().toString();
                accident = accidentEditText.getText().toString();
                explosion = explosionEditText.getText().toString();

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("Robbery", robbery);
                editor.putString("Kidnapping", kidnapping);
                editor.putString("Fire", fire);
                editor.putString("Med", med);
                editor.putString("Rape", rape);
                editor.putString("Assault", assault);
                editor.putString("Disaster", disaster);
                editor.putString("Accident", accident);
                editor.putString("Explosion", explosion);
                editor.commit();
                Toast.makeText(keyActivity.this, "Keywords Saved", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


//                saveNewUser(firstnameEditText.getText().toString(), lastnameEditText.getText().toString(), usernameEditText.getText().toString(), usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

    }
}

/*
    public void saveButton(View view){
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Panic", MODE_PRIVATE, null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (emergency VARCHAR, keyword VARCHAR)");
        myDatabase.execSQL("INSERT INTO users (emergency, keyword) VALUES ('Robbery'," + robberyEditText.getText().toString() + ");");

        Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);
        int emergencyIndex = c.getColumnIndex("emergency");
        int keywordIndex = c.getColumnIndex("keyword");
        c.moveToFirst();
        Log.i("emergency", c.getString(emergencyIndex));
        Log.i("keyword", c.getString(keywordIndex));
        c.close();
  }
}
*/

