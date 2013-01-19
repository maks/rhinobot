package org.rhinobot.hostobjects;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

import android.util.Log;

/**
 * Implementation in support of JS setTimout/setInterval
 * 
 * 
 * based on: http://stackoverflow.com/questions/2261705/how-to-run-a-javascript
 * -function-asynchronously-without-using-settimeout
 * 
 */

public class JsTimers {

    private static final String TAG = JsTimers.class.getSimpleName();
    private HashMap<Integer, TimerTask> callbackMap = new HashMap<Integer, TimerTask>();
    private Integer counter = 1;
    private Timer timer = new java.util.Timer();
    
    
    private void invokeCallback(final Function fn) {
        Log.d(TAG, "running timer callback " + fn);
        ContextFactory.getGlobal().call(new ContextAction() {
            public Object run(Context cx) {
                Scriptable scope = fn.getParentScope();
                WrapFactory wrapFactory = cx.getWrapFactory();
                wrapFactory.setJavaPrimitiveWrap(false);
                return fn.call(cx, scope, scope, new Object[0]);
            }
        });
    }

    /**
     * Register a callback function for the given event.
     * 
     * @param delay
     *            delay before callback is invoked in ms
     * @param callback
     *            the callback function
     */
    public Integer setTimeout(final Function callback, final int delay) {
        Log.d(TAG, "setting new timer " + delay);
        TimerTask tt = new TimerTask() {
            private Function cbFn = callback;

            @Override
            public void run() {
                invokeCallback(cbFn);
            }
        };
        callbackMap.put(counter++, tt);
        timer.schedule(tt, delay);
        return counter;
    }


    public void clear(Integer id) {
        TimerTask tt = callbackMap.get(id);
        if (tt != null) {
            tt.cancel();
            callbackMap.remove(id);
            timer.purge();
        }
    }

    public void cancel() {
        timer.cancel();
    }

    public Integer setInterval(final Function callback, final int delay) {
        Log.d(TAG, "setting new timer " + delay);
        TimerTask tt = new TimerTask() {
            private Function cbFn = callback;

            @Override
            public void run() {
                invokeCallback(cbFn);
            }
        };
        callbackMap.put(counter++, tt);
        timer.schedule(tt, delay, delay);
        return counter;
    }
}
