package com.axp.axpseller.utils;

import android.app.Activity;
import android.content.Intent;

import com.axp.axpseller.activitys.order.ConfirmOrderActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.OrderList;
import com.axp.axpseller.models.bean.CreateTempOrderListBean;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.views.dialogs.LoadingDialog;

/**
 * Created by xu on 2016/8/17.
 * 订单工具类
 */
public class OrderUtils {

    /**
     * 向服务器发送生成临时当单请求
     */
    public static void createTempOrderList(Activity context, CreateTempOrderListBean createTempOrderListBean) {
        Request<CreateTempOrderListBean> request = new Request<>();
        request.setData(createTempOrderListBean);
        RXUtils.request(context, request, "createTempOrderList", new SupportSubscriber() {
            LoadingDialog loadingDialog;
            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            @Override
            public void onStop() {
                super.onStop();
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                T.showShort(context, "出错了，请重试");
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(context, response.getMessage());
            }

            @Override
            public void onNext(Object o) {
                Response<OrderList> response = (Response<OrderList>) o;
                OrderList orderList = response.getData();

                Intent intent = new Intent(context, ConfirmOrderActivity.class);
                intent.putExtra(ConfirmOrderActivity.KEY_ORDER_LIST, orderList);
                context.startActivity(intent);

            }
        });
    }

}
