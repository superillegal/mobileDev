package com.example.application03;

// ConstraintLayoutActivity.java
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class ConstraintActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint);

        Button nextButton = findViewById(R.id.button31);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConstraintActivity.this, FrameActivity.class);
                intent.putExtra("message", "Hello from ConstraintLayoutActivity");
                startActivity(intent);
            }
        });

        Button previousButton = findViewById(R.id.button32);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
