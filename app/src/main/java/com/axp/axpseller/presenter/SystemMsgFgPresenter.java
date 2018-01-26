package com.axp.axpseller.presenter;

import android.content.Context;

import com.axp.axpseller.model.IOrderMsgFgListenner;
import com.axp.axpseller.model.ISystemMsgData;
import com.axp.axpseller.model.ISystemMsgDataImpl;
import com.axp.axpseller.models.OrderMsgDtOb;
import com.axp.axpseller.view.IShowSystemMsgFg;

import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public class SystemMsgFgPresenter {
    private IShowSystemMsgFg iShowSystemMsgFg;
    private ISystemMsgData iSystemMsgData;

    public SystemMsgFgPresenter(IShowSystemMsgFg iShowSystemMsgFg) {
        this.iShowSystemMsgFg = iShowSystemMsgFg;
        this.iSystemMsgData = new ISystemMsgDataImpl();
    }

    public void showSystemMsgFgView(Context context, String typeId) {
        iShowSystemMsgFg.initView();
        iShowSystemMsgFg.initData();
        iSystemMsgData.getSystemMsgList(context, typeId, new IOrderMsgFgListenner() {
            @Override
            public void onSuccess(List<OrderMsgDtOb> list) {
                iShowSystemMsgFg.showView(list);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    public void upDateMsgList(Context context, String typeId){
        iSystemMsgData.getSystemMsgList(context, typeId, new IOrderMsgFgListenner() {
            @Override
            public void onSuccess(List<OrderMsgDtOb> list) {
                iShowSystemMsgFg.showView(list);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }
}
