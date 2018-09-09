package com.niconicocbf.tokubailab.mvpdemo_cbf.base;

import com.niconicocbf.tokubailab.mvpdemo_cbf.DisplayView;

public interface Ipresenter<T extends DisplayView> {
    void attachView(T view);
    void detachView();
}
