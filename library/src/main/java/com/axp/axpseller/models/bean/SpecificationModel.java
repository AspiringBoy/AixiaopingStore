package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/12/8.
 */
public class SpecificationModel extends BaseModel {

    //现价
    @SerializedName("price")
    String price;

    //库存
    @SerializedName("stock")
    String stock;

    //规格id
    @SerializedName("specId")
    String specId;

    //型号
    @SerializedName("specStr")
    String specStr;

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecStr() {
        return specStr;
    }

    public void setSpecStr(String specStr) {
        this.specStr = specStr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

}
