package com.example.application082;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private static final String DOG_API_URL = "https://random.dog/woof.json";
    private ImageView imageView;
    private Button loadImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        loadImageButton = findViewById(R.id.button);

        loadImageButton.setOnClickListener(v -> loadImage());
    }

    private void loadImage() {
        new Thread(() -> {
            try {
                URL url = new URL(DOG_API_URL);
                InputStream in = url.openStream();
                String json = new java.util.Scanner(in).useDelimiter("\\A").next();

                JSONObject jsonObject = new JSONObject(json);
                String imageUrl = jsonObject.getString("url");

                InputStream imageStream = new URL(imageUrl).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                new Handler(Looper.getMainLooper()).post(() -> imageView.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
