package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2018/1/11.
 */
public class RechargeCardModel extends BaseModel {

    @SerializedName("score")
    String score;

    @SerializedName("cardNum")
    String cardNum;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @SerializedName("password")
    String password;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
