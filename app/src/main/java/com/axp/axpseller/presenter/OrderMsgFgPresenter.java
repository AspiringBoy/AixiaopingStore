package com.axp.axpseller.presenter;

import android.content.Context;

import com.axp.axpseller.model.IOrderMsgFgData;
import com.axp.axpseller.model.IOrderMsgFgDataImpl;
import com.axp.axpseller.model.IOrderMsgFgListenner;
import com.axp.axpseller.models.OrderMsgDtOb;
import com.axp.axpseller.view.IShowOrderMsgFg;

import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public class OrderMsgFgPresenter {
    private IShowOrderMsgFg iShowOrderMsgFg;
    private IOrderMsgFgData iOrderMsgFgData;

    public OrderMsgFgPresenter(IShowOrderMsgFg iShowOrderMsgFg) {
        this.iShowOrderMsgFg = iShowOrderMsgFg;
        this.iOrderMsgFgData = new IOrderMsgFgDataImpl();
    }

    public void showOrderMsgFgView(Context context,int typeId){
        iShowOrderMsgFg.initView();
        iShowOrderMsgFg.initData();
        iOrderMsgFgData.getOrderMsgList(context, typeId+"", new IOrderMsgFgListenner() {
            @Override
            public void onSuccess(List<OrderMsgDtOb> list) {
                iShowOrderMsgFg.showView(list);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    public void upDateMsgList(Context context,int typeId){
        iOrderMsgFgData.getOrderMsgList(context, typeId+"", new IOrderMsgFgListenner() {
            @Override
            public void onSuccess(List<OrderMsgDtOb> list) {
                iShowOrderMsgFg.showView(list);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }
}
