package com.niconicocbf.tokubailab.mvpdemo_cbf;

import com.niconicocbf.tokubailab.mvpdemo_cbf.base.BasePresenter;

public class PhotoZoupicpresenter extends BasePresenter {
    PhotozoupicModel photoModel;
    public PhotoZoupicpresenter() {
        photoModel=new PhotozoupicModel();
    }
    public void getPicInfo(){
        photoModel.getPhotoInfo(getMview());
    }
}
