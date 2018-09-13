package com.niconicocbf.tokubailab.mvpdemo_cbf.base;

public class BasePresenter<V extends BaseView> implements Ipresenter<V> {
    public V getMview() {
        return mview;
    }

    public void setMview(V mview) {
        this.mview = mview;
    }

    V mview;
    @Override
    public void attachView(V view) {
        mview=view;
    }

    @Override
    public void detachView() {
        mview=null;
    }
}
