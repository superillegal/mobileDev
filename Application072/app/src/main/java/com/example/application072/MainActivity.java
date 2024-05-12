package com.example.application072;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView tvSelectedDate, tvSelectedTime;
    Button btnAlertDialog, btnDatePickerDialog, btnTimePickerDialog, btnCustomDialog;

    int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        btnAlertDialog = findViewById(R.id.btnAlertDialog);
        btnDatePickerDialog = findViewById(R.id.btnDatePickerDialog);
        btnTimePickerDialog = findViewById(R.id.btnTimePickerDialog);
        btnCustomDialog = findViewById(R.id.btnCustomDialog);

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        btnAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        btnDatePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnTimePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btnCustomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog");
        builder.setMessage("Вы хотите перейти на другую Activity?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvSelectedDate.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvSelectedTime.setText(hourOfDay + ":" + minute);
                    }
                },
                hour, minute, true);
        timePickerDialog.show();
    }

    private void showCustomDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomDialogFragment dialogFragment = new CustomDialogFragment();
        dialogFragment.show(fragmentManager, "customDialog");
    }

    public static class CustomDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(R.layout.custom_dialog);
            builder.setTitle("Custom Dialog");

            Dialog dialog = builder.create();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button btnChangeFragment = ((AlertDialog) dialogInterface).findViewById(R.id.btnChangeFragment);
                    btnChangeFragment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "Сохранено!", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
                }
            });

            return dialog;
        }
    }

}
