package io.github.mssjsg.android.base.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import io.github.mssjsg.android.base.dagger.DaggerActivityInjectHelper;
import io.github.mssjsg.android.base.dagger.qualifier.ForApplication;

public class DemoActivity extends AppCompatActivity
        implements DaggerActivityInjectHelper.ComponentFactory<DemoComponent>, DemoPresenter.MainView {

    private DaggerActivityInjectHelper<DemoComponent> mInjectHelper;

    @Inject @ForApplication Context application;
    @Inject AppCompatActivity mAppCompatActivity;
    @Inject DemoPresenter mDemoPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        mInjectHelper = new DaggerActivityInjectHelper<>(this);
        mInjectHelper.initialize(this);

        mInjectHelper.getComponent().inject(this);

        mDemoPresenter.initialize(this);
    }

    @Override
    public DemoComponent onCreateComponent() {
        return ((DemoApplication)getApplication()).getComponent().just(new DemoModule(this));
    }

    @Override
    public void showInitializingApp() {

    }

    @Override
    public void showInitializingView() {

    }

    @Override
    public void showViewInitialized() {

    }
}
