package com.axp.axpseller.model;

import android.content.Context;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.OrderMsgModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;

/**
 * Created by YY on 2017/5/5.
 */
public class ISystemMsgDataImpl implements ISystemMsgData {
    @Override
    public void getSystemMsgList(Context context, String typeId, final IOrderMsgFgListenner listenner) {
        Request<OrderMsgModel> request = new Request<>();
        OrderMsgModel orderMsgModel = new OrderMsgModel();
        orderMsgModel.setUserId(ContextParameter.getUserInfo().getUserId());
        orderMsgModel.setTypeId(typeId);
        request.setData(orderMsgModel);
        RXUtils.request(context, request, "getMsgList", new SupportSubscriber<Response<OrderMsgModel>>() {
            @Override
            public void onNext(Response<OrderMsgModel> orderMsgModelResponse) {
                listenner.onSuccess(orderMsgModelResponse.getData().getMsgList());
            }

        });
    }
}
