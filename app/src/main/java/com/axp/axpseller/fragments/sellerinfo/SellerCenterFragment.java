package com.axp.axpseller.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.MsgSettingActivity;
import com.axp.axpseller.activitys.sellerinfo.SellerInforActivity;
import com.axp.axpseller.activitys.user.FeedbackActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.UserInfo;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/2/10.
 */
public class SellerCenterFragment extends BaseFragment {

    View mView;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_center, container, false);

        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!StringUtils.isBlank(ContextParameter.getUserInfo().getUserId())) {
            getUserInfo(ContextParameter.getUserInfo().getUserId());
        }else {
            setData(null);
        }
    }

    private void setData(UserInfo u) {
        //接口中无响应数据，暂时设置成默认值
        /*if (StringUtils.isBlank(u.getHeadimage())) {

            ivHead.setImageResource(R.drawable.tx);

        } else {

            Glide.with(getActivity()).load(u.getHeadimage()).asBitmap().placeholder(R.drawable.tx).error(R.drawable.tx).centerCrop().into(new BitmapImageViewTarget(ivHead) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivHead.setImageDrawable(circularBitmapDrawable);

                }
            });
        }
        if (StringUtils.isBlank(u.getUsername())) {
            tvSellerName.setText("未设置");
        } else {
            tvSellerName.setText(u.getUsername());
        }*/

        ivHead.setImageResource(R.drawable.tx);
    }

    @OnClick({R.id.tv_my_info, R.id.tv_agency, R.id.tv_feeback, R.id.ll_loginout,R.id.tv_msg_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_info:
                AppUtils.toActivity(getActivity(), SellerInforActivity.class);
                break;
            case R.id.tv_agency:

                break;
            case R.id.tv_feeback:
                AppUtils.toActivity(getActivity(), FeedbackActivity.class);
                break;
            case R.id.ll_loginout:
                UserUtils.logout(getActivity());
                break;
            case R.id.tv_msg_setting:
                AppUtils.toActivity(getActivity(), MsgSettingActivity.class);
                break;
        }
    }
    /**
     * 获取个人中心数据
     */
    private void getUserInfo(String userId) {
        Request<UserInfo> request = new Request<UserInfo>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "getBaseInfo", new SupportSubscriber<Response<UserInfo>>() {
            @Override
            public void onNext(Response<UserInfo> response) {
//                UserInfoSP.setUserInfo(response.getData());
                setData(response.getData());
            }
        });

    }
}
