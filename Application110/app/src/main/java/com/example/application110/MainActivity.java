package com.example.application110;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "main_channel";
    protected static final String DELAYED_CHANNEL_ID = "delayed_channel";
    private WebView webView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannels();

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        Button webViewButton = findViewById(R.id.webViewButton);
        webViewButton.setOnClickListener(view -> {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("https://www.google.com");
        });

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playButton.setText("Воспроизвести аудио");
            } else {
                mediaPlayer.start();
                playButton.setText("Пауза");
            }
        });

        Button notifyButton = findViewById(R.id.notifyButton);
        notifyButton.setOnClickListener(view -> showNotification());

        Button delayedNotifyButton = findViewById(R.id.delayedNotifyButton);
        delayedNotifyButton.setOnClickListener(view -> scheduleNotification(10000)); // 10 секунд

        ImageView animatedImageView = findViewById(R.id.animatedImageView);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        animatedImageView.startAnimation(rotateAnimation);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Основные уведомления",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Канал для основных уведомлений");
            notificationManager.createNotificationChannel(channel);

            NotificationChannel delayedChannel = new NotificationChannel(
                    DELAYED_CHANNEL_ID,
                    "Отложенные уведомления",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            delayedChannel.setDescription("Канал для отложенных уведомлений");
            notificationManager.createNotificationChannel(delayedChannel);
        }
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Уведомление")
                .setContentText("Это простое уведомление")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1, builder.build());
    }

    private void scheduleNotification(long delay) {
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long futureInMillis = System.currentTimeMillis() + delay;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
