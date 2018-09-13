package com.niconicocbf.tokubailab.mvpdemo_cbf.base;

public interface Ipresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
