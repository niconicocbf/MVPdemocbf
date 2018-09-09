package com.niconicocbf.tokubailab.mvpdemo_cbf.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.niconicocbf.tokubailab.mvpdemo_cbf.DisplayView;

public abstract class BaseActivity<T extends Ipresenter> extends AppCompatActivity implements DisplayView {
    public T mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getViewResId());
        mPresenter=getPresenter();
        if (null == mPresenter) {
            throw new IllegalStateException("Please call mPresenter in BaseMVPActivity(createPresenter) to create!");
        } else {
            mPresenter.attachView(this);
        }

        initView();
    }

    protected abstract T getPresenter();
    protected abstract int getViewResId();
    protected abstract void initView();

    @Override
    public void getDataSuccess(String successMsg) {

    }

    @Override
    public void getDataFail(String errorMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
