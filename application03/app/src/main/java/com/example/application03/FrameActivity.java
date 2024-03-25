package com.example.application03;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class FrameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        Button previousButton = findViewById(R.id.button4);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
