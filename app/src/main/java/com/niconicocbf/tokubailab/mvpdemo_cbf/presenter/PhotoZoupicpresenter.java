package com.niconicocbf.tokubailab.mvpdemo_cbf.presenter;

import com.niconicocbf.tokubailab.mvpdemo_cbf.base.BasePresenter;
import com.niconicocbf.tokubailab.mvpdemo_cbf.base.DisplayView;
import com.niconicocbf.tokubailab.mvpdemo_cbf.model.PhotozoupicModel;
public class PhotoZoupicpresenter extends BasePresenter<DisplayView> {
    PhotozoupicModel photoModel;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private String keyword;
    public PhotoZoupicpresenter() {
        photoModel=new PhotozoupicModel();
    }
    public void getPicInfo(){
        photoModel.getPhotoInfo(getMview(),keyword);
    }
}
