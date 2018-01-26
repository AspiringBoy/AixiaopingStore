package com.axp.axpseller.models;

import com.google.gson.annotations.SerializedName;
import com.axp.axpseller.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/7/13.
 * 免单商城
 */
public class FreeMall extends BaseModel {

    @SerializedName("becomeVipBanners")
    /** 成为会员横幅广告 */
    private List<ImageText> becomeVipBanners;


    public List<ImageText> getBecomeVipBanners() {
        return becomeVipBanners;
    }

    public void setBecomeVipBanners(List<ImageText> becomeVipBanners) {
        this.becomeVipBanners = becomeVipBanners;
    }
}
