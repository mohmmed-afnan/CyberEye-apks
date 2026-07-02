package com.cybereye.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.io.*;
import java.net.*;

public class CyberEyeService extends Service {
    private static final String SERVER_IP = "192.168.1.13";
    private static final int SERVER_PORT = 4444;
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), 5000);
                    Log.d("CyberEye", "Connected!");
                    while (!socket.isClosed()) {
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    Log.e("CyberEye", "Error: " + e.getMessage());
                    try { Thread.sleep(5000); } catch (Exception ex) {}
                }
            }
        }).start();
        return START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) { return null; }
}