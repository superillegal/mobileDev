package com.example.application071;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private CameraManager cameraManager;
    private String cameraId;
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Сервис создан");

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            Log.e(TAG, "Ошибка получения камеры: " + e.getMessage());
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                blinkFlashlight();
                handler.postDelayed(this, 10000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Запуск сервиса");
        handler.postDelayed(runnable, 10000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Сервис уничтожен");
        handler.removeCallbacks(runnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void blinkFlashlight() {
        Random random = new Random();
        int blinkCount = random.nextInt(5) + 1;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < blinkCount; i++) {
                        cameraManager.setTorchMode(cameraId, true);
                        Thread.sleep(500);
                        cameraManager.setTorchMode(cameraId, false);
                        Thread.sleep(500);
                    }
                } catch (CameraAccessException | InterruptedException e) {
                    Log.e(TAG, "Ошибка управления фонариком: " + e.getMessage());
                }
            }
        }).start();
    }
}
