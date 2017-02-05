package io.github.mssjsg.android.base.dagger;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sing on 1/22/17.
 */

public class DaggerActivityInjectHelper<DaggerComponent> {

    private static final String WORKER_TAG = "TAG_DAGGER_COMPONENT_WORKER";

    private AppCompatActivity mAppCompatActivity;

    public DaggerActivityInjectHelper(AppCompatActivity activity) {
        mAppCompatActivity = activity;
    }

    public void initialize(ComponentFactory<DaggerComponent> factory) {
        DaggerWorkerFragment fragment = getFragment();
        if (fragment == null) {
            fragment = new DaggerWorkerFragment();
            fragment.setComponent(factory.onCreateComponent());
            mAppCompatActivity.getSupportFragmentManager()
                    .beginTransaction().add(fragment, WORKER_TAG).commitNow();
        }
    }

    public interface ComponentFactory<DaggerComponent> {
        DaggerComponent onCreateComponent();
    }

    public DaggerComponent getComponent() {
        return (DaggerComponent) getFragment().getComponent();
    }

    private DaggerWorkerFragment getFragment() {
        FragmentManager fragmentManager = mAppCompatActivity.getSupportFragmentManager();
        return (DaggerWorkerFragment) fragmentManager.findFragmentByTag(WORKER_TAG);
    }
}
