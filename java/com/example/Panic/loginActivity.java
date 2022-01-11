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

public class loginActivity extends AppCompatActivity {

    private EditText emaillogEditText, passwordlogEditText;
    TextView orRegister;
    Button loginButton;
    String emaillog, passwordlog, emailCheck, passwordCheck, pCheck;

//    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        mAuth = FirebaseAuth.getInstance();

        emaillogEditText = findViewById(R.id.usernamelogEditText);
        passwordlogEditText = findViewById(R.id.passwordlogEditText);
        orRegister = findViewById(R.id.gotoRegister);
        loginButton = findViewById(R.id.loginButton);

        SharedPreferences sp = getApplication().getSharedPreferences("UserRegister", Context.MODE_PRIVATE);
        emaillog = sp.getString("Email", "");
        passwordlog = sp.getString("Password", "");
        pCheck = sp.getString("pressCheck", "");
        emailCheck = emaillogEditText.getText().toString();
        passwordCheck = passwordlogEditText.getText().toString();

        if (pCheck.equals("1")){
            orRegister.setVisibility(View.GONE);
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((emailCheck.equals(emaillog)) || (passwordCheck.equals(passwordlog))){
                    Toast.makeText(loginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(loginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }

//                signIn(usernamelogEditText.getText().toString(), passwordlogEditText.getText().toString());
            }
        });


    }

    public void gotoRegister(View view){
        Intent intent = new Intent(getApplicationContext(), registerActivity.class);
        startActivity(intent);
        if (pCheck.equals("1")){
            finish();
        }
    }
    public void forgotPassword(View view){
        Intent intent = new Intent(getApplicationContext(), forgotActivity.class);
        startActivity(intent);
    }
}

//    private void signIn(String email, String password){
//        Log.d(TAG, "signIn: Reached here");
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(loginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//
//    }
//
//    private void updateUI(FirebaseUser user) {}

//    public void loginButton(View view){
//        usernamelogEditText = (EditText) findViewById(R.id.usernamelogEditText);
//        passwordlogEditText = (EditText) findViewById(R.id.passwordlogEditText);
//
//        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
//        startActivity(intent);
//    }