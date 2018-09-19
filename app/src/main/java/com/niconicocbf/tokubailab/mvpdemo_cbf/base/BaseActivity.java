package com.niconicocbf.tokubailab.mvpdemo_cbf.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.Window;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    public T mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getViewResId());
        mPresenter=createPresenter();
        if (null == mPresenter) {
            throw new IllegalStateException("Please call mPresenter in BaseMVPActivity(createPresenter) to create!");
        } else {
            mPresenter.attachView(this);
        }

        initView();
    }



    protected abstract T createPresenter();
    protected abstract int getViewResId();
    protected abstract void initView();
    public T getmPresenter() {
        return mPresenter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
