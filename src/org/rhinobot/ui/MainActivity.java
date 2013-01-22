package org.rhinobot.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.rhinobot.EventMap;
import org.rhinobot.R;
import org.rhinobot.ScriptBuilder;
import org.rhinobot.events.ActivityEvent;

import java.io.File;

public class MainActivity extends ListActivity {

    public static final File EXT_MODULE_DIR = new File(
            Environment.getExternalStorageDirectory(),
            "rhinobot");

    private static final String TAG = MainActivity.class.getSimpleName();
    EventMap<ActivityEvent> events = EventMap.create(ActivityEvent.class);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Creating MainActivity ");

        EXT_MODULE_DIR.mkdirs();

        setContentView(R.layout.main);

        try {
            new ScriptBuilder(getAssets())
                    .defineEventSource("activity", this, events)
                    .installRequire()
                    .evaluate("js/utils.js")
                    .evaluate("js/main.js");
            events.invoke(ActivityEvent.create, savedInstanceState);
        } catch (Exception e) {
            Toast.makeText(this, "Error running main.js: " + e,
                    Toast.LENGTH_LONG).show();
        }
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