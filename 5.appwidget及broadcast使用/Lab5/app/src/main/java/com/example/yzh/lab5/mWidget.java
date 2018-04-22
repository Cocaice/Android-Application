package com.example.yzh.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class mWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

       //CharSequence widgetText = context.getString(R.string.appwidget_text);
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews view = new RemoteViews(context.getPackageName(),R.layout.m_widget);
        Intent clickInt = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickInt, 0);

        view.setOnClickPendingIntent(R.id.widget, pendingIntent);
        ComponentName me = new ComponentName(context, mWidget.class);
        appWidgetManager.updateAppWidget(me, view);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals("staticbrocast")) {
            int src = bundle.getInt("src");
            String name = bundle.getString("name") + "仅售" + bundle.getString("price") + "!";
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
            views.setTextViewText(R.id.appwidget_text, name);
            views.setImageViewResource(R.id.widget_photo, src);
            ComponentName me = new ComponentName(context, mWidget.class);


            Intent intent1 = new Intent(context, DetailActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("name", bundle.getString("name"));
            bundle1.putString("price", bundle.getString("price"));
            bundle1.putString("detail", bundle.getString("detail"));
            bundle1.putInt("src", bundle.getInt("src"));
            intent1.putExtras(bundle1);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(me, views);

        }
    }
}

