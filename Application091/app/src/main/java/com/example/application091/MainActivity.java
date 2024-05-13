package com.example.application091;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText editTextFileName;
    private EditText editTextFileData;
    private Button buttonCreateFile;
    private Button buttonAppendToFile;
    private Button buttonReadFile;
    private Button buttonDeleteFile;

    private String fileName;
    private String fileData;

    private static final String KEY_FILE_NAME = "file_name";
    private static final String KEY_FILE_DATA = "file_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFileName = findViewById(R.id.editTextFileName);
        editTextFileData = findViewById(R.id.editTextFileData);
        buttonCreateFile = findViewById(R.id.buttonCreateFile);
        buttonAppendToFile = findViewById(R.id.buttonAppendToFile);
        buttonReadFile = findViewById(R.id.buttonReadFile);
        buttonDeleteFile = findViewById(R.id.buttonDeleteFile);

        buttonCreateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFile();
            }
        });

        buttonAppendToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToFile();
            }
        });

        buttonReadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
            }
        });

        buttonDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFileWithConfirmation();
            }
        });

        if (savedInstanceState != null) {
            fileName = savedInstanceState.getString(KEY_FILE_NAME);
            fileData = savedInstanceState.getString(KEY_FILE_DATA);

            editTextFileName.setText(fileName);
            editTextFileData.setText(fileData);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FILE_NAME, editTextFileName.getText().toString());
        outState.putString(KEY_FILE_DATA, editTextFileData.getText().toString());
    }

    private void createFile() {
        fileName = editTextFileName.getText().toString();
        fileData = editTextFileData.getText().toString();

        File storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File file = new File(storageDir, fileName);

        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    FileWriter writer = new FileWriter(file);
                    writer.append(fileData);
                    writer.flush();
                    writer.close();
                    Toast.makeText(this, "File created", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "File already exists", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void appendToFile() {
        fileName = editTextFileName.getText().toString();
        String dataToAppend = editTextFileData.getText().toString();

        File storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(storageDir, fileName);

        try {
            if (file.exists()) {
                FileWriter writer = new FileWriter(file, true);
                writer.append(dataToAppend);
                writer.flush();
                writer.close();
                Toast.makeText(this, "Data appended to file", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        fileName = editTextFileName.getText().toString();

        File storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(storageDir, fileName);

        if (file.exists()) {
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }

                br.close();
                editTextFileData.setText(text.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFileWithConfirmation() {
        fileName = editTextFileName.getText().toString();

        new AlertDialog.Builder(this)
                .setTitle("Delete File")
                .setMessage("Are you sure you want to delete this file?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteFile())
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteFile() {
        File storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(storageDir, fileName);

        if (file.exists()) {
            boolean deleted = file.delete();

            if (deleted) {
                Toast.makeText(this, "File deleted", Toast.LENGTH_SHORT).show();
                editTextFileName.setText("");
                editTextFileData.setText("");
            } else {
                Toast.makeText(this, "Failed to delete file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show();
        }
    }
}
