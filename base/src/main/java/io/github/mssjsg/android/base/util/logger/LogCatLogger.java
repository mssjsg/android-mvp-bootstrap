package io.github.mssjsg.android.base.util.logger;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sing on 26/3/2017.
 */
@Singleton
public class LogCatLogger extends Logger {

    private static final String PREFIX = "[BookBag]";

    @Inject
    public LogCatLogger() {
    }

    @Override
    protected void debug(String tag, String message) {
        Log.d(tag, PREFIX + message);
    }

    @Override
    protected void info(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    protected void warm(String tag, String message) {
        Log.w(tag, message);
    }

    @Override
    protected void error(String tag, String message, Exception e) {
        Log.e(tag, message, e);
    }
}
