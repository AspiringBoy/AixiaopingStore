package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YY on 2017/12/14.
 */
public class SecKillTimeResModel extends BaseModel {
    public ArrayList<Secondskilltime> getSecondskilltime() {
        return secondskilltime;
    }

    public void setSecondskilltime(ArrayList<Secondskilltime> secondskilltime) {
        this.secondskilltime = secondskilltime;
    }

    @SerializedName("secondskilltime")
    ArrayList<Secondskilltime> secondskilltime;
}
