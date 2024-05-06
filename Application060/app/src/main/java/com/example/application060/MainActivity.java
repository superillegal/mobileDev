package com.example.application060;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Страны");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CountriesFragment()).commit();
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_countries) {
                actionBar.setTitle("Страны");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CountriesFragment()).commit();
            } else if (item.getItemId() == R.id.nav_cities) {
                actionBar.setTitle("Города");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CitiesFragment()).commit();
            } else if (item.getItemId() == R.id.nav_attractions) {
                actionBar.setTitle("Достопримечательности");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AttractionsFragment()).commit();
            } else if (item.getItemId() == R.id.nav_go_to_activity2) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}