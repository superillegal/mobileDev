package com.example.application101;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private TextView currentName;
    private Button saveButton;
    private Button deleteButton;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_NAME = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        currentName = findViewById(R.id.currentName);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        displayCurrentName();

        saveButton.setOnClickListener(view -> saveName());
        deleteButton.setOnClickListener(view -> deleteName());
    }

    private void displayCurrentName() {
        String name = sharedPreferences.getString(KEY_NAME, "Имя не задано");
        currentName.setText("Текущее имя: " + name);
    }

    private void saveName() {
        String name = nameInput.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
        displayCurrentName();
        Toast.makeText(this, "Имя сохранено", Toast.LENGTH_SHORT).show();
    }

    private void deleteName() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_NAME);
        editor.apply();
        displayCurrentName();
        Toast.makeText(this, "Имя удалено", Toast.LENGTH_SHORT).show();
    }
}
