package com.xtlog.android.zhihudaily.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.activity.MainActivity;
import com.xtlog.android.zhihudaily.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PushService extends Service {
    private static final String TAG = "PushService";
    private NotificationManager manager;
    private MessageThread messageThread;

    public PushService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();
        
        return super.onStartCommand(intent, flags, startId);
    }

    class MessageThread extends Thread {

        public boolean isRunning = true;

        public void run() {
            while (isRunning) {
                try {
                    // 间隔时间半小时
//                    Thread.sleep(1000*60*30);
                    Thread.sleep(1000*10);
                    // 获取服务器消息
                    String currentString = download("http://news-at.zhihu.com/api/4/news/latest");
                    // 获取上次消息
                    String sharedString = MainActivity.sMainActivity.getSharedPreferences("data", MODE_PRIVATE).getString("json", "");

                    //测试
                    Log.i(TAG, "current " + currentString);
                    Log.i(TAG, "shared " + sharedString);

                    //有新消息推送
                    if (!currentString.equals(sharedString)) {
                        //推送一次
                        pushNotify();
                        SharedPreferences.Editor editor = MainActivity.sMainActivity.getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("json", currentString);
                        editor.apply();

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String download(String url)  {
       try {
           OkHttpClient client = new OkHttpClient();
           Request request = new Request.Builder()
                   .url(url)
                   .build();
           Response response = client.newCall(request).execute();
           return response.body().string();
       }
       catch (Exception e){
           e.printStackTrace();
       }
        return null;
    }

    private void pushNotify(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, mainIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("知乎精选")
                .setContentText("有新的文章，点击这里查看")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notify_push)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .build();
        manager.notify(0,notification);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        messageThread.isRunning = false;
        Log.i(TAG, "onDestroy: ");
    }
}
