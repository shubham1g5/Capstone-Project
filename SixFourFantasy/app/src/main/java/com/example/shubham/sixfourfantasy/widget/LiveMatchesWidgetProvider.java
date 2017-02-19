package com.example.shubham.sixfourfantasy.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.example.shubham.sixfourfantasy.R;
import com.example.shubham.sixfourfantasy.data.sync.MatchesSyncTask;
import com.example.shubham.sixfourfantasy.matchdetail.MatchDetailActivity;
import com.example.shubham.sixfourfantasy.matches.MatchesActivity;

public class LiveMatchesWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.matches_widget);

            // Launch MainActivity onClicking Title
            Intent intent = new Intent(context, MatchesActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_title, pendingIntent);


            remoteViews.setRemoteAdapter(R.id.matcheslist_listview, new Intent(context, MatchesWidgetRemoteViewsService.class));

            // Set PendingIntentTemplate
            Intent clickIntentTemplate = new Intent(context, MatchDetailActivity.class);
            PendingIntent clickPendingItentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.matcheslist_listview, clickPendingItentTemplate);
            remoteViews.setEmptyView(R.id.matcheslist_listview, R.id.error);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        if (MatchesSyncTask.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.matcheslist_listview);
        }
    }
}
