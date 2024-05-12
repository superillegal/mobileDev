package com.example.application081;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "$$$";
    Button btParallel, btSequential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btParallel = findViewById(R.id.btParallel);
        btSequential = findViewById(R.id.btSequential);


        btParallel.setOnClickListener(view -> {
            OneTimeWorkRequest work1 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task", "Parallel Task 1").build())
                    .build();
            OneTimeWorkRequest work2 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task", "Parallel Task 2").build())
                    .build();

            WorkManager.getInstance(this)
                    .enqueue(Arrays.asList(work1, work2));
        });


        btSequential.setOnClickListener(view -> {
            OneTimeWorkRequest work1 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task", "Sequential Task 1").build())
                    .build();
            OneTimeWorkRequest work2 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task", "Sequential Task 2").build())
                    .build();
            OneTimeWorkRequest work3 = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("task", "Sequential Task 3").build())
                    .build();

            WorkManager.getInstance(this)
                    .beginWith(work1)
                    .then(work2)
                    .then(work3)
                    .enqueue();
        });
    }
}
