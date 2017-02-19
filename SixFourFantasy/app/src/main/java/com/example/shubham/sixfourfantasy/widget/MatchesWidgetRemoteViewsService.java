package com.example.shubham.sixfourfantasy.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.shubham.sixfourfantasy.R;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.MatchStatus;
import com.example.shubham.sixfourfantasy.data.model.Team;
import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;

import java.util.ArrayList;
import java.util.List;

import static com.example.shubham.sixfourfantasy.matches.MatchesActivity.EXTRA_MATCH;

public class MatchesWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StocksWidgetRemoteViewsFactory();
    }

    private class StocksWidgetRemoteViewsFactory implements RemoteViewsFactory {

        private Cursor data = null;
        private List<Team> teams;

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            if (data != null) {
                data.close();
            }

            final long identityToken = Binder.clearCallingIdentity();

            Cursor teamsData = getContentResolver().query(MatchesPersistenceContract.TeamEntry.CONTENT_URI,
                    MatchesPersistenceContract.TeamEntry.TEAMS_COLUMNS,
                    null,
                    null,
                    null);
            teams = new ArrayList<>();

            if (teamsData.moveToFirst()) {
                do {
                    teams.add(Team.from(teamsData));
                }
                while (teamsData.moveToNext());
            }
            teamsData.close();


            data = getContentResolver().query(MatchesPersistenceContract.MatchEntry.CONTENT_URI,
                    MatchesPersistenceContract.MatchEntry.MATCHES_COLUMNS,
                    MatchesPersistenceContract.MatchEntry.COL_STATUS + " = ?",
                    new String[]{MatchStatus.COMPLETED.toString()},
                    null);
            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            if (data != null) {
                data.close();
                data = null;
            }
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (data == null || position == AdapterView.INVALID_POSITION || !data.moveToPosition(position)) {
                return null;
            }

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.match_widget_item);
            Match match = Match.from(data);

            // Set Teams
            for (Team team : teams) {
                if (match.team1.teamId == team.teamId)
                    match.team1 = team;
                if (match.team2.teamId == team.teamId)
                    match.team2 = team;
            }

            remoteViews.setTextViewText(R.id.match_title, match.name);
            remoteViews.setTextViewText(R.id.team1_name, match.team1.symbol);
            remoteViews.setTextViewText(R.id.team1_score, match.team1Score);
            remoteViews.setTextViewText(R.id.team2_name, match.team2.symbol);
            remoteViews.setTextViewText(R.id.team2_score, match.team2Score);


            // Set FillInIntent
            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(EXTRA_MATCH, match);
            remoteViews.setOnClickFillInIntent(R.id.list_item, fillInIntent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return new RemoteViews(getPackageName(), R.layout.match_widget_item);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            if (data.moveToPosition(position))
                return data.getLong(MatchesPersistenceContract.MatchEntry.COL_MATCH_ID_INDEX);
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
