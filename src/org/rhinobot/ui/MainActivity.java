package org.rhinobot.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.manichord.grimlock.events.ActivityEvent;
import org.rhinobot.EventMap;
import org.rhinobot.R;
import org.rhinobot.ScriptBuilder;

public class MainActivity extends ListActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    EventMap<ActivityEvent> events = EventMap.create(ActivityEvent.class);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Creating MainActivity ");
        setContentView(R.layout.main);
        new ScriptBuilder(getAssets())
                .defineEventSource("activity", this, events)
                .installRequire()
                .evaluate("js/utils.js")
                .evaluate("js/main.js");
        events.invoke(ActivityEvent.create, savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        events.invoke(ActivityEvent.click, Integer.valueOf(position));
    }

}