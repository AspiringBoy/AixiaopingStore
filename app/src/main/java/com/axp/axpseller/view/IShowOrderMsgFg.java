package com.axp.axpseller.view;

import com.axp.axpseller.models.OrderMsgDtOb;

import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public interface IShowOrderMsgFg extends IShowBaseFg{
    void showView(List<OrderMsgDtOb> list);
}
