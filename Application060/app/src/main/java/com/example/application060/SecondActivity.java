package com.example.application060;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SecondActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_2);
        actionBar.setTitle("Европа");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame_2, new EuropeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_europe) {
                actionBar.setTitle("Европа");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame_2, new EuropeFragment()).commit();
            } else if (item.getItemId() == R.id.nav_asia) {
                actionBar.setTitle("Азия");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame_2, new AsiaFragment()).commit();
            } else if (item.getItemId() == R.id.nav_america) {
                actionBar.setTitle("Америка");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame_2, new AmericaFragment()).commit();
            }
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}