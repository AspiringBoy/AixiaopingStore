package com.axp.axpseller.views.seller;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.axp.axpseller.Constants;
import com.axp.axpseller.R;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.models.bean.PostGoodResModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.custom.CustomDialog;
import com.axp.axpseller.views.custom.GlideImageLoader;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.youth.banner.Banner;

/**
 * Created by YY on 2017/12/13.
 */
public class AllCityExtensionView implements View.OnClickListener {

    private final Activity mActivity;
    private TextView goodTypeTv, goodNameTv, goodPriceTv, sendTypeTv;
    private Banner topBanner;
    private CheckBox realGoodCb, fastSendCb, afterCarelessCb;
    private Button postBtn;
    private CustomDialog customDialog;
    private GoodsManageModel manageModel;

    public AllCityExtensionView(Activity activity) {
        mActivity = activity;
        topBanner = (Banner) activity.findViewById(R.id.top_banner);
        goodTypeTv = (TextView) activity.findViewById(R.id.good_type_tv);
        goodNameTv = (TextView) activity.findViewById(R.id.good_name_tv);
        goodPriceTv = (TextView) activity.findViewById(R.id.good_price_tv);
        sendTypeTv = (TextView) activity.findViewById(R.id.send_type_tv);
        realGoodCb = (CheckBox) activity.findViewById(R.id.real_good_cb);
        fastSendCb = (CheckBox) activity.findViewById(R.id.fast_send_cb);
        afterCarelessCb = (CheckBox) activity.findViewById(R.id.after_careless_cb);
        postBtn = (Button) activity.findViewById(R.id.post_extension_btn);
        postBtn.setOnClickListener(this);
    }

    public void setData(GoodsManageModel manageModel) {
        this.manageModel = manageModel;
        topBanner.setImageLoader(new GlideImageLoader());
        topBanner.isAutoPlay(false);
        topBanner.setImages(manageModel.getCoverPics());
        topBanner.start();
        goodTypeTv.setText(manageModel.getCateName());
        goodNameTv.setText(manageModel.getGoodsName());
        goodPriceTv.setText(manageModel.getPrice());
        String transportationType = manageModel.getTransportationType();
        switch (Integer.parseInt(transportationType)) {
            case 1:
                sendTypeTv.setText("包邮");
                break;
            case 2:
                sendTypeTv.setText("不包邮" + "    邮费:" + manageModel.getTransportationPrice());
                break;
            case 3:
                sendTypeTv.setText("上门自取");
                break;
            case 4:
                sendTypeTv.setText("到店消费");
                break;
        }
        for (Integer integer : manageModel.getRightsProtect()) {
            switch (integer) {
                case 1:
                    realGoodCb.setChecked(true);
                    break;
                case 2:
                    fastSendCb.setChecked(true);
                    break;
                case 3:
                    afterCarelessCb.setChecked(true);
                    break;
            }
        }

    }

    @Override
    public void onClick(View view) {
        customDialog = DialogUtil.showCustomDialog(mActivity, R.style.customDialogStyle, R.layout.all_city_extension_dialog_item, 300, 360, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_cal:
                        customDialog.dismiss();
                        break;
                    case R.id.btn_sure:
                        customDialog.dismiss();
                        postExtension();
                        break;
                }
            }
        }, R.id.btn_cal, R.id.btn_sure);
    }

    private void postExtension() {
        Request<PostGoodResModel> request = new Request<>();
        PostGoodResModel model = new PostGoodResModel();
        model.setType(Constants.EXTENSION_ALL_CITY_TYPE);
        model.setGoodsOrder(manageModel.getGoodsOrder());
        request.setData(model);
        RXUtils.request(mActivity, request, "applyGoods", new SupportSubscriber<Response<PostGoodResModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(mActivity);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response<PostGoodResModel> postGoodResModelResponse) {
                if (postGoodResModelResponse.getStatus() == 1) {
                    Toast.makeText(mActivity, "" + postGoodResModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    mActivity.finish();
                }
            }

            @Override
            public void onResponseError(Response response) {
                Toast.makeText(mActivity, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }
}
