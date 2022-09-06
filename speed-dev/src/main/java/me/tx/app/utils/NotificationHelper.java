package me.tx.app.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationHelper {
    public void show(Context context, String title, String info, int bigIcon, int smallIcon, PendingIntent intent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService
                (NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "110";
            String channelName = "提示";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }
        /**
         *  设置Builder
         */
        //设置标题
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"110");

        mBuilder.setContentTitle(title)
                //设置内容
                .setContentText(info)
                //设置大图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), bigIcon))
                //设置小图标
                .setSmallIcon(smallIcon)
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                //跳转
                .setAutoCancel(true)
                .setContentIntent(intent)
                //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                .setDefaults(Notification.DEFAULT_SOUND);
        //发送通知请求
        notificationManager.notify((int)(System.currentTimeMillis()%100000), mBuilder.build());
    }
}
