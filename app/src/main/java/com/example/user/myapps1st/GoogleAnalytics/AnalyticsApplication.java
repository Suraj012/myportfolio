package com.example.user.myapps1st.GoogleAnalytics;

import android.app.Application;

import com.example.user.myapps1st.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Suraj on 12/29/2016.
 */
public class AnalyticsApplication extends Application {
    private Tracker mTracker;
    private static AnalyticsApplication mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }

    synchronized public Tracker getDefaultTracker()
    {
        if (mTracker == null)
        {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(AnalyticsApplication.this);

            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }

    public synchronized Tracker getGoogleAnalyticsTracker()
    {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    public void trackEvent(String category, String action, String label)
    {
        Tracker t = getDefaultTracker();
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

    public static synchronized AnalyticsApplication getInstance()
    {
        return mInstance;
    }

    public void trackScreenView(String screenName)
    {
        Tracker t = getGoogleAnalyticsTracker();
        t.setScreenName(screenName);
        t.send(new HitBuilders.ScreenViewBuilder().build());
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }


    public void trackException(Exception e)
    {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();
            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription( new StandardExceptionParser(this, null)
                            .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }
}
