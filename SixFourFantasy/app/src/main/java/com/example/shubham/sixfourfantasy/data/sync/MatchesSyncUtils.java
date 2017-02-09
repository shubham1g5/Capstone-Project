package com.example.shubham.sixfourfantasy.data.sync;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;


public class MatchesSyncUtils {

    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    private static boolean sInitialized;

    private static final String MATCHES_SYNC_TAG = "matches-sync";


    static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncSunshineJob = dispatcher.newJobBuilder()
                .setService(MatchesSyncFirebaseJobService.class)
                .setTag(MATCHES_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(syncSunshineJob);
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     *
     * @param context Context that will be passed to other methods and used to access the
     *                ContentResolver
     */
    synchronized public static void initialize(@NonNull final Context context) {

        /*
         * Only perform initialization once per app lifetime. If initialization has already been
         * performed, we have nothing to do in this method.
         */
        if (sInitialized) return;

        sInitialized = true;

        scheduleFirebaseJobDispatcherSync(context);


         /*
         * We need to check to see if our ContentProvider has data to display in our forecast
         * list. However, performing a query on the main thread is a bad idea as this may
         * cause our UI to lag. Therefore, we create a thread in which we will run the query
         * to check the contents of our ContentProvider.
         */
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        BriteContentResolver resolver = sqlBrite.wrapContentProvider(context.getContentResolver(), Schedulers.io());
        Observable<SqlBrite.Query> matchesQuery = resolver.createQuery(MatchesPersistenceContract.MatchEntry.CONTENT_URI,
                new String[]{MatchesPersistenceContract.MatchEntry.COL_MATCH_ID},
                null,
                null,
                null,
                false);
        matchesQuery.count().subscribe(count -> {
           if(count == 0){
              MatchesSyncTask.syncMatches(context);
           }
        });
    }
}
