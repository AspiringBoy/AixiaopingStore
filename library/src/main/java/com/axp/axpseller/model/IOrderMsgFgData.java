package com.axp.axpseller.model;

import android.content.Context;

/**
 * Created by YY on 2017/5/5.
 */
public interface IOrderMsgFgData {
    void getOrderMsgList(Context context,String typeId, IOrderMsgFgListenner listenner);
}
