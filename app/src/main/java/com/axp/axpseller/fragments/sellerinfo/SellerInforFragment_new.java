package com.axp.axpseller.fragments.sellerinfo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.SellersInfo;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.UserUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/5.
 * 商家版资料
 */
public class SellerInforFragment_new extends BaseFragment {

    View mView;
    private PopupWindow pw;
    private TextView question_tv;
    private ImageView question_iv;
    private TextView user_name_tv;
    private LinearLayout bg_ll;
    private TextView store_name_tv;
    private TextView contact_tv;
    private TextView phone_number_tv;
    private TextView invitation_tv;
    private TextView store_address_tv;
    private TextView store_agency_tv;
    private SellersInfo data;
    private Toolbar toolbar;
    private FrameLayout framelayout;
    private TextView none_data_tv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_seller_information_new, container, false);
        ButterKnife.bind(this, mView);
        initView();
        getSellerInfo();
        return mView;
    }

    private void initView() {
        question_iv = ((ImageView) mView.findViewById(R.id.question_iv));
        user_name_tv = ((TextView) mView.findViewById(R.id.user_name_tv));
        store_name_tv = ((TextView) mView.findViewById(R.id.store_name_tv));
        contact_tv = ((TextView) mView.findViewById(R.id.contact_tv));
        phone_number_tv = ((TextView) mView.findViewById(R.id.phone_number_tv));
        invitation_tv = ((TextView) mView.findViewById(R.id.invitation_tv));
        store_address_tv = ((TextView) mView.findViewById(R.id.store_address_tv));
        store_agency_tv = ((TextView) mView.findViewById(R.id.store_agency_tv));
        bg_ll = ((LinearLayout) mView.findViewById(R.id.bg_ll));
        toolbar = ((Toolbar) mView.findViewById(R.id.tool_bar));
        framelayout = ((FrameLayout) mView.findViewById(R.id.framlayout));
        none_data_tv = ((TextView) mView.findViewById(R.id.none_data_tv));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getSellerInfo();
    }

    @OnClick({R.id.btn_logout, R.id.question_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                UserUtils.logout(getActivity());
                //SellerInfoSp.clear(getActivity());
                //ContextParameter.setSellersInfo(new SellersInfo());
                //  AppUtils.toActivity(getActivity(),ComeLoginActivity.class);
                //getActivity().finish();
                break;
            case R.id.question_iv:
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.seller_information_pop, null, false);
                pw = new PopupWindow(view, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 190, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 105, getResources().getDisplayMetrics()));
                question_tv = ((TextView) view.findViewById(R.id.seller_information_pop_tv));
                question_tv.setText(data.getLoginNameRemark());
                pw.setBackgroundDrawable(new BitmapDrawable());
                pw.setOutsideTouchable(true);
                pw.setFocusable(true);
                pw.showAsDropDown(question_iv, -507, 0);
                bg_ll.setVisibility(View.VISIBLE);
                pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        bg_ll.setVisibility(View.GONE);
                    }
                });
                break;
        }
    }

    private void getSellerInfo() {
        Request request = new Request();
//        SellersInfo sellersInfo = new SellersInfo();
//        sellersInfo.setSellerId("4816");
//        sellersInfo.setAdminuserId("705");
//        request.setData(sellersInfo);
        RXUtils.request(getActivity(), request, "getAdminInfoById", new SupportSubscriber<Response<SellersInfo>>() {

            @Override
            public void onNext(Response<SellersInfo> o) {
                data = o.getData();
                setData(o.getData());
            }

            @Override
            public void onResponseError(Response response) {
                framelayout.setVisibility(View.GONE);
                none_data_tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                framelayout.setVisibility(View.GONE);
                none_data_tv.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setData(SellersInfo sellersInfo) {
        if (sellersInfo.getLoginName()!= null && sellersInfo.getLoginName().length()>0) {
            framelayout.setVisibility(View.VISIBLE);
            none_data_tv.setVisibility(View.GONE);
            user_name_tv.setText(sellersInfo.getLoginName());
            store_name_tv.setText(sellersInfo.getSellerName());
            contact_tv.setText(sellersInfo.getContacts());
            phone_number_tv.setText(sellersInfo.getPhone());
            invitation_tv.setText(sellersInfo.getInvitecode());
            store_address_tv.setText(sellersInfo.getAddress());
            store_agency_tv.setText(sellersInfo.getPartnerName());
        }
    }

}
