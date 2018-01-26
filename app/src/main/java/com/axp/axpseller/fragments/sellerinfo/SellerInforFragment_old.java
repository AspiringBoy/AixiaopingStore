package com.axp.axpseller.fragments.sellerinfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.SellersInfo;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.UserUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/5.
 * 商家版资料
 */
public class SellerInforFragment_old extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head_portrait)
    ImageView ivHeadPortrait;
    @BindView(R.id.tv_headimg)
    TextView tvHeadimg;
    @BindView(R.id.ll_head_portrait)
    LinearLayout llHeadPortrait;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.ll_sell_user_name)
    LinearLayout llSellUserName;
    @BindView(R.id.tv_seller_invitecode)
    TextView tvSellerInvitecode;
    @BindView(R.id.ll_seller_invitecode)
    LinearLayout llSellerInvitecode;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.tv_change_password)
    TextView tvChangePassword;
    @BindView(R.id.ll_change_password)
    LinearLayout llChangePassword;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.ll_business)
    LinearLayout llBusiness;
    @BindView(R.id.tv_loc)
    TextView tvLoc;
    @BindView(R.id.ll_loc)
    LinearLayout llLoc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_information_old, container, false);

        ButterKnife.bind(this, mView);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        getSellerInfo();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSellerInfo();
    }

    @OnClick(R.id.btn_logout)
    public void onClick() {
        UserUtils.logout(getActivity());
        //SellerInfoSp.clear(getActivity());
        //ContextParameter.setSellersInfo(new SellersInfo());
      //  AppUtils.toActivity(getActivity(),ComeLoginActivity.class);
        //getActivity().finish();
    }

    private void getSellerInfo(){
        RXUtils.request(getActivity(), new Request(), "getAdminInfoById", new SupportSubscriber<Response<SellersInfo>>() {
            @Override
            public void onNext(Response<SellersInfo> o) {
                setData(o.getData());
            }
        });
    }
    private void setData(SellersInfo sellersInfo){
        tvName.setText(sellersInfo.getSellerName());
        //设置用户头像
        if (StringUtils.isBlank(sellersInfo.getSellerLogo())) {

            ivHeadPortrait.setImageResource(R.drawable.icon_defult);

        } else {

            Glide.with(getActivity()).load(sellersInfo.getSellerLogo()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(ivHeadPortrait) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivHeadPortrait.setImageDrawable(circularBitmapDrawable);

                }
            });
        }
        tvPhone.setText(sellersInfo.getPhone());
        tvAddress.setText(sellersInfo.getAddress());
        tvUsername.setText(sellersInfo.getContacts());
        tvSellerInvitecode.setText(sellersInfo.getInvitecode());
        tvChangePassword.setText(sellersInfo.getReContacts());
        tvBusiness.setText(sellersInfo.getReAddress());

    }
}
