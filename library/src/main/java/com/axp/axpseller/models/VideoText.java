package com.axp.axpseller.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xu on 2016/6/4.
 * 视频与文字
 */
public class VideoText extends ImageText {

    @SerializedName("video")
    /** 视频链接 */
    private String video;


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
