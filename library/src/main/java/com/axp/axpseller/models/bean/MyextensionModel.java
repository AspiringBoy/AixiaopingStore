package com.axp.axpseller.models.bean;

import com.axp.axpseller.core.BaseModel;

/**
 * Created by YY on 2017/12/15.
 */
public class MyextensionModel extends BaseModel {

    int activityId;

    int pageIndex;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
