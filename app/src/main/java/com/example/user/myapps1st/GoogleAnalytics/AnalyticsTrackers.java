package com.example.user.myapps1st.GoogleAnalytics;

import android.content.Context;

import com.example.user.myapps1st.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suraj on 12/29/2016.
 */
public class AnalyticsTrackers {
    public enum Target {APP,}
    private static AnalyticsTrackers sInstance;
    public static synchronized void initialize(Context context)
    {
        if (sInstance != null)
        {
            throw new IllegalStateException("Extra call to initialize analytics trackers");
        }
        sInstance = new AnalyticsTrackers(context);
    }
    public static synchronized AnalyticsTrackers getInstance()
    {
        if (sInstance == null) {
            throw new IllegalStateException("Call initialize() before getInstance()");
        }
        return sInstance;
    }
    private final Map<Target, Tracker> mTrackers = new HashMap<Target, Tracker>();
    private final Context mContext;
    private AnalyticsTrackers(Context context)
    {
        mContext = context.getApplicationContext();
    }
    public synchronized Tracker get(Target target)
    {
        if (!mTrackers.containsKey(target))
        {
            Tracker tracker;
            switch (target)
            {
                case APP:
                    tracker = GoogleAnalytics.getInstance(mContext).newTracker(R.xml.app_tracker);
                    break;
                default:
                    throw new IllegalArgumentException("Unhandled analytics target " + target);
            }
            mTrackers.put(target, tracker);
        }
        return mTrackers.get(target);
    }
}
