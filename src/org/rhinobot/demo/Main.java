package org.rhinobot.demo;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.rhinobot.EventMap;
import org.rhinobot.R;
import org.rhinobot.ScriptBuilder;
import org.rhinobot.events.ActivityEvent;

public class Main extends ListActivity {

    EventMap<ActivityEvent> events = EventMap.create(ActivityEvent.class);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new ScriptBuilder(getAssets())
                .defineEventSource("activity", this, events)
                .evaluate("js/utils.js")
                .evaluate("js/main.js");
        events.invoke(ActivityEvent.create, savedInstanceState);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        events.invoke(ActivityEvent.click, Integer.valueOf(position));
    }
    


}