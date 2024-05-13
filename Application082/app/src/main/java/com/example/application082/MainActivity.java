package com.example.application082;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String API_URL = "https://random.dog/woof.json";
    private static final String TAG = "ImageLoader";
    private ImageView imageView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        handler = new Handler(Looper.getMainLooper());

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }

    private void loadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(API_URL);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String response = convertStreamToString(inputStream);
                        JSONObject json = new JSONObject(response);
                        String imageUrl = json.getString("url");

                        Bitmap bitmap = downloadImage(imageUrl);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });

                    } else {
                        Log.e(TAG, "Error: HTTP " + connection.getResponseCode());
                    }

                    connection.disconnect();

                } catch (IOException | JSONException e) {
                    Log.e(TAG, "Error loading image", e);
                }
            }
        }).start();
    }

    private Bitmap downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        return BitmapFactory.decodeStream(input);
    }

    private static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
