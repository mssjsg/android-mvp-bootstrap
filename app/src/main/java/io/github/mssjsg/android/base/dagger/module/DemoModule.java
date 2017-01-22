package io.github.mssjsg.android.base.dagger.module;

import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import io.github.mssjsg.android.base.demo.DemoPresenter;

/**
 * Created by sing on 1/22/17.
 */
@Module
public class DemoModule extends ActivityModule<DemoPresenter> {

    public DemoModule(AppCompatActivity activity) {
        super(activity, new DemoPresenter());
    }
}
