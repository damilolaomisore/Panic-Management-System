package com.example.Panic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class forgotActivity extends AppCompatActivity {
    private EditText firstnamePass, lastnamePass, usernamePass, answerPass, passwordPass;
    private TextView authText;
    Button resetButton;
    String firstReset, lastReset, userReset, answerReset, passReset, authQuestion, firstCheck, lastCheck, userCheck, answerCheck, passCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firstnamePass = findViewById(R.id.firstnamePass);
        lastnamePass = findViewById(R.id.lastnamePass);
        usernamePass = findViewById(R.id.usernamePass);
        answerPass = findViewById(R.id.answerPass);
        passwordPass = findViewById(R.id.passwordPass);
        resetButton = findViewById(R.id.resetButton);
        authText = findViewById(R.id.authText);

        SharedPreferences sp = getApplication().getSharedPreferences("UserRegister", Context.MODE_PRIVATE);
        firstReset = sp.getString("Firstname", "");
        lastReset = sp.getString("Lastname", "");
        userReset = sp.getString("Email", "");
        answerReset = sp.getString("Auth_Answer", "");
        passReset = sp.getString("Password", "");
        authQuestion = sp.getString("Auth_Question", "");

        authText.setText(authQuestion);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstCheck = firstnamePass.getText().toString();
                lastCheck = lastnamePass.getText().toString();
                userCheck = usernamePass.getText().toString();
                answerCheck = answerPass.getText().toString();
                passCheck = passwordPass.getText().toString();

                if ((firstCheck.equals(firstReset)) || (lastCheck.equals(lastReset)) || (userCheck.equals(userReset)) || (answerCheck.equals(answerReset)) ){
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("Password", passCheck);
                    editor.commit();
                    Toast.makeText(forgotActivity.this, "Password reset successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(forgotActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}