package com.example.application03;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

public class RelativeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative);

        TextView messageText = findViewById(R.id.textView2);
        messageText.setTextSize(26);
        messageText.setPadding(16, 16, 16, 16);

        MyObject myObject = (MyObject) getIntent().getSerializableExtra("myObject");
        if (myObject != null) {
            messageText.setText("Name: " + myObject.getName() + "\nAge: " + myObject.getAge());
        }

        Button nextButton = findViewById(R.id.button21);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelativeActivity.this, ConstraintActivity.class);
                intent.putExtra("message", "Message from RelativeActivity");
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
