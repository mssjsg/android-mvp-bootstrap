package io.github.mssjsg.android.base.util.executor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by maksing on 26/11/2016.
 */

public final class ForExecutor {

    private ForExecutor() {}

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Main {}

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Io {}

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Computation {}
}
