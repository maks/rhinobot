package org.rhinobot.hostobjects;

import android.util.Log;

/**
 * Browser DOM console object implementation, writing output to logcat.
 * 
 */
public class JsConsole {

    private static final String TAG = JsConsole.class.getSimpleName();
    
    public JsConsole() {
    }

    public void debug(String mesg) {
        log(mesg);
    }

    public void log(String mesg) {
        Log.d(TAG, mesg);
    }

    public void info(String mesg) {
        Log.i(TAG, mesg);
    }

    public void warn(String mesg) {
        Log.w(TAG, mesg);
    }

    public void error(String mesg) {
        Log.e(TAG, mesg);
    }
}
