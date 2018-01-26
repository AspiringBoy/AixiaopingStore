package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/12/8.
 */
public class GoodDetailModel extends BaseModel {

    //类型
    @SerializedName("detailType")
    String detailType;

    //文字描述
    @SerializedName("text")
    String text;

    //图片
    @SerializedName("picture")
    String picture;

    //图片宽
    @SerializedName("pictureWidth")
    String pictureWidth;

    public String getPictureHeight() {
        return pictureHeight;
    }

    public void setPictureHeight(String pictureHeight) {
        this.pictureHeight = pictureHeight;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureWidth() {
        return pictureWidth;
    }

    public void setPictureWidth(String pictureWidth) {
        this.pictureWidth = pictureWidth;
    }

    //图片高
    @SerializedName("pictureHeight")
    String pictureHeight;
}
