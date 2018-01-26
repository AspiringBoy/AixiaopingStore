package com.axp.axpseller.model;

import android.content.Context;

/**
 * Created by YY on 2017/5/5.
 */
public interface ISystemMsgData {
    void getSystemMsgList(Context context,String typeId,IOrderMsgFgListenner listenner);
}
