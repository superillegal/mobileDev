package com.example.application03;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);

        Button nextButton = findViewById(R.id.button11);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.edit_text);
                String message = editText.getText().toString();

                MyObject myObject = new MyObject(message, 30);
                Intent intent = new Intent(MainActivity.this, RelativeActivity.class);
                intent.putExtra("myObject", myObject);
                startActivity(intent);
            }
        });

        Button previousButton = findViewById(R.id.button22);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
