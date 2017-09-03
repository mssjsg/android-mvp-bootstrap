package io.github.mssjsg.android.base.util.logger;

/**
 * Created by Sing on 26/3/2017.
 */

public abstract class Logger {

    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int WARMING = 2;
    public static final int ERROR = 3;

    public void d(String tag, String message) {
        debug(tag, message);
    }

    public void i(String tag, String message) {
        info(tag, message);
    }

    public void w(String tag, String message) {
        warm(tag, message);
    }

    public void e(String tag, String message) {
        error(tag, message, null);
    }

    public void e(String tag, Exception e) {
        error(tag, null, e);
    }

    public void e(String tag, String message, Exception e) {
        error(tag, message, e);
    }

    protected abstract void debug(String tag, String message);

    protected abstract void info(String tag, String message);

    protected abstract void warm(String tag, String message);

    protected abstract void error(String tag, String message, Exception e);
}
