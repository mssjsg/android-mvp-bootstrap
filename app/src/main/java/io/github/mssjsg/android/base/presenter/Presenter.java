package io.github.mssjsg.android.base.presenter;

/**
 * Created by sing on 1/22/17.
 */

public abstract class Presenter<V extends Presenter.View> {

    private V mView;

    public void initialize(V view) {
        setView(view);
        mView.showInitializingView();
        if (!isAppInitialized()) {
            initializeApp();
            mView.showInitializingApp();
        } else {
            initializeView();
        }
    }

    private void onAppInitialized() {
        initializeView();
    }

    protected void onViewInitialized() {
        mView.showViewInitialized();
    }

    public boolean isAppInitialized() {
        return true;
    }

    protected abstract void initializeApp();

    protected abstract void initializeView();

    public abstract void dispose();

    protected V getView() {
        return mView;
    }

    public void setView(V view) {
        mView = view;
    }

    public interface View {
        void showInitializingApp();

        void showInitializingView();

        void showViewInitialized();
    }
}
