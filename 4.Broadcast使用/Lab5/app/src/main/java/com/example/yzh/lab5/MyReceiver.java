package com.example.yzh.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("staticbrocast")) {
            Bundle bundle = intent.getExtras();
            String cont = bundle.getString("name") + "仅售" + bundle.getString("price")+ "!";
            int src = bundle.getInt("src");
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),src);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("新商品热卖");
            builder.setContentText(cont);
            builder.setLargeIcon(bm);
            builder.setSmallIcon(src);
            builder.setAutoCancel(true);

            Intent intent1 = new Intent(context, DetailActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("name", bundle.getString("name"));
            bundle1.putString("price", bundle.getString("price"));
            bundle1.putString("detail", bundle.getString("detail"));
            bundle1.putInt("src", bundle.getInt("src"));
            intent1.putExtras(bundle1);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(mPendingIntent);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = builder.build();
            manager.notify(0,notify);
        }
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

    }
}
