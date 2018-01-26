package com.axp.axpseller.models;

import com.google.gson.annotations.SerializedName;
import com.axp.axpseller.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/6/23.
 * 分类
 */
public class GoodsType extends BaseModel {


    /**
     * typeId : 12
     * typeName : 吃喝玩
     * typeImtes : [{"typeId":"12","typeName":"中餐"}]
     */

    @SerializedName("typeId")
    private String typeId;
    @SerializedName("typeName")
    private String typeName;
    @SerializedName("typeItems")
    private List<GoodsType> typeItems;
    private Boolean select;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public List<GoodsType> getTypeItems() {
        return typeItems;
    }

    public void setTypeItems(List<GoodsType> typeItems){
        this.typeItems = typeItems;
    }

    public Boolean getSelect() {
        return select == null ? false : select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
