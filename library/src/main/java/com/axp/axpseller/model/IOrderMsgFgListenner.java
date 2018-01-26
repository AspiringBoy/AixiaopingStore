package com.axp.axpseller.model;

import com.axp.axpseller.models.OrderMsgDtOb;

import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public interface IOrderMsgFgListenner {
    void onSuccess(List<OrderMsgDtOb> list);

    void onError(String errMsg);
}
