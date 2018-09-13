package com.niconicocbf.tokubailab.mvpdemo_cbf.base;

public interface DisplayView<T> extends BaseView {
    void getDataSuccess(T successMsg);
    void getDataFail(T errorMsg);
}
