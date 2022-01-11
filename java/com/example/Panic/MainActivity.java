package com.example.Panic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.ContentValues.TAG;
import static android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH;
import static android.webkit.WebSettings.PluginState.OFF;
import static android.webkit.WebSettings.PluginState.ON;

public class MainActivity<timer> extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognizer;
    private TextView textView;
    String robbery, kidnapping, fire, med, rape, assault, disaster, accident, explosion, phone, message, newMessage;
    String address, cityName, userAddress;
    Button panicButton;
    public FusedLocationProviderClient fusedLocationClient;
    public LatLng latLng;
    public Location lastLocation;
    LocationRequest myLocationRequest;


//    private GoogleMap mMap;
    LocationCallback myLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            for (Location location : locationResult.getLocations()) {
                if (getApplicationContext() != null) {
                    lastLocation = location;
                    locationResult.getLastLocation();
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    cityName = getCityName(latLng);
                    Log.d("TAG", "Address: "+ address);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        myLocationRequest = LocationRequest.create();
        myLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(myLocationRequest,myLocationCallback,Looper.myLooper());
//            mMap.setMyLocationEnabled(true);
        }else{
            checkLocationPermission();
        }

        textView = findViewById(R.id.textView);
        intentRecognizer = new Intent(ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle bundle) {

                SharedPreferences sp = getApplication().getSharedPreferences("UserKeywords", Context.MODE_PRIVATE);
                robbery = sp.getString("Robbery", "");
                kidnapping = sp.getString("Kidnapping", "");
                fire = sp.getString("Fire", "");
                med = sp.getString("Med", "");
                rape = sp.getString("Rape", "");
                assault = sp.getString("Assault", "");
                disaster = sp.getString("Disaster", "");
                accident = sp.getString("Accident", "");
                explosion = sp.getString("Explosion", "");

                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String string = "";
                if (matches != null) {
                    string = matches.get(0);
                    textView.setText(string);
                    if (string.contains(robbery)) {
                        textView.setText("Robbery");
                    } else if (string.contains(kidnapping)) {
                        textView.setText("Kidnap");
                    } else if (string.contains(fire)) {
                        textView.setText("Fire");
                    } else if (string.contains(med)) {
                        textView.setText("Medical");
                    } else if (string.contains(rape)) {
                        textView.setText("Rape");
                    } else if (string.contains(assault)) {
                        textView.setText("Assault");
                    } else if (string.contains(disaster)) {
                        textView.setText("Disaster");
                    } else if (string.contains(accident)) {
                        textView.setText("Accident");
                    } else if (string.contains(explosion)) {
                        textView.setText("Explosion");
                    } else{
                        textView.setText(string);
                        string += "(Unknown Situaton)";
                    }


                }


            }


            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        panicButton = findViewById(R.id.panicButton);

        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechRecognizer.startListening(intentRecognizer);
                timer();
                speechRecognizer.stopListening();
                sendSMS();
            }
        });
        Switch simpleSwitch = findViewById(R.id.switch1);
        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleSwitch.isChecked()) {
                    Log.d("mylog", "SWITCH ON");
                    Toast.makeText(MainActivity.this, "Panic Button active", Toast.LENGTH_SHORT).show();
                    panicButton.setEnabled(true);
                } else {
                    Log.d("mylog", "SWITCH OFF");
                    Toast.makeText(MainActivity.this, "Panic Button inactive", Toast.LENGTH_SHORT).show();
                    panicButton.setEnabled(false);
                }
            }
        });

    }



    private String getCityName(LatLng latLng) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            myCity = addresses.get(0).getLocality();
            Log.d("mylog", "Complete Address: "+ addresses.toString());
            Log.d("mylog", "Address: "+ address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCity;
    }


//    public void panicButton(View view){
//
//        speechRecognizer.startListening(intentRecognizer);
//        timer();
//        speechRecognizer.stopListening();
//        sendSMS();
//
//    }

//    public void StopButton(View view){
//
//        speechRecognizer.stopListening();
//        SmsManager mySmsManager = SmsManager.getDefault();
//        mySmsManager.sendTextMessage();
//
//    }

    public void mapButton(View view){
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
    }

    public void settButton(View view){
        Intent intent = new Intent(getApplicationContext(), loginActivity.class);
        startActivity(intent);
    }

    public void timer(){
        new CountDownTimer(5000, 1000){
            public void onTick(long millisecondsUntilDone){
                Toast.makeText(getApplicationContext(), "Timer : " + millisecondsUntilDone/1000, Toast.LENGTH_SHORT).show();
            }
            public void onFinish(){
                Toast.makeText(getApplicationContext(), "Time out", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void sendSMS() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);


        SharedPreferences sp = getApplication().getSharedPreferences("UserContact", Context.MODE_PRIVATE);
        phone = sp.getString("Phone", "");
        message = sp.getString("Message", "");
        newMessage = message + "\n Situation: " + textView.getText().toString() + "\n Address: " + address;
        Log.d("mylog", "Address: "+ address);

        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(phone, null, newMessage, null, null);
        Log.d(TAG, "PLEASE WORK");
    }

    private void checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(this)
                        .setTitle("Give Permission")
                        .setMessage("Give Permission Message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();

            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.requestLocationUpdates(myLocationRequest, myLocationCallback, Looper.myLooper());
//                    mMap.setMyLocationEnabled(true);
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    updateMap(lastKnownLocation);
                }
            }else{
//                Toast.makeText(getApplicationContext(), "Please provide the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}
