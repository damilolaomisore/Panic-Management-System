package com.example.Panic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    private EditText firstnameEditText, lastnameEditText, usernameEditText, answerEditText, passwordEditText;
    private String firstName, lastName, email, password, answer, authQues, fName, lName, eMail, pWord;
    Button registerButton;
    SharedPreferences sp;
    String pCheck = "0";



        String[] backUpQuestion = {"Select question...", "What is the last name of your favorite president?", "What is your first pet's name?", "What is your mother's maiden name?"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_text, backUpQuestion);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Backup_question, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        firstnameEditText = findViewById(R.id.firstnameEditText);
        lastnameEditText = findViewById(R.id.lastnameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        answerEditText = findViewById(R.id.answerEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);


        sp = getSharedPreferences("UserRegister", Context.MODE_PRIVATE);



        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pCheck = "1";
                firstName = firstnameEditText.getText().toString();
                lastName = lastnameEditText.getText().toString();
                answer = answerEditText.getText().toString();
                email = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                authQues = spinner.getSelectedItem().toString();
                Log.d("MyLog", "HERE: "+authQues);


                SharedPreferences.Editor editor = sp.edit();

                editor.putString("Firstname", firstName);
                editor.putString("Lastname", lastName);
                editor.putString("Email", email);
                editor.putString("Auth_Question", authQues);
                editor.putString("Auth_Answer", answer);
                editor.putString("Password", password);
                editor.putString("pressCheck", pCheck);
                editor.commit();
                Toast.makeText(registerActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(intent);
                finish();
//                createAccount(usernameEditText.getText().toString(), passwordEditText.getText().toString());
//                saveNewUser(firstnameEditText.getText().toString(), lastnameEditText.getText().toString(), usernameEditText.getText().toString(), usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


//    private void createAccount(String email, String password){
//        Log.d(TAG, "createAccount: Got here");
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createAccount: started here");
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "createAccount: success here");
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
////                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                            userId = mAuth.getCurrentUser().getUid();
//                            DatabaseReference currentUserDatabase = FirebaseDatabase.getInstance().getReference().child("USERS").child(userId);
//                            currentUserDatabase.setValue(true);
//
//
//                            firstName = firstnameEditText.getText().toString();
//                            lastName = lastnameEditText.getText().toString();
//
//
//                            Map<String, Object> userInformation = new HashMap<String, Object>();
//                            userInformation.put("firstname",firstName);
//                            userInformation.put("lastname",lastName);
//                            currentUserDatabase.updateChildren(userInformation);
//                            Log.d(TAG, "createUserWithEmail:successful again");
//
//
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(registerActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                        Log.d(TAG, "createAccount: was here");
//                    }
//                });
//    }

//    private void reload() { }
//
//    private void updateUI(FirebaseUser user) {}
