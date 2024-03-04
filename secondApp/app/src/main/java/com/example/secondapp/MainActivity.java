package com.example.secondapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Build;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RRR","onCreate()");
        setContentView(R.layout.activity_main);

        String androidVersion = getString(R.string.version, Build.VERSION.SDK_INT);
        TextView textViewVersion = findViewById(R.id.version);
        textViewVersion.setText(androidVersion);


    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SET", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SET", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SET", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SET", "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SET", "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SET", "onDestroy: ");
    }

}