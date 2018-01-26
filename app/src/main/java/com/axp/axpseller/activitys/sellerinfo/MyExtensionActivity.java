package com.axp.axpseller.activitys.sellerinfo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axp.axpseller.R;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.models.bean.GoodManageResModel;
import com.axp.axpseller.models.bean.GoodsManageModel;
import com.axp.axpseller.models.bean.MyextensionModel;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.view.IBaseListenner;
import com.axp.axpseller.views.adapters.MyExtensionLvAdapter;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyExtensionActivity extends AppCompatActivity implements IBaseListenner, View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolBarTitle;
    @BindView(R.id.my_extension_lv)
    PullToRefreshListView myExtensionLv;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @BindView(R.id.screen_bg)
    RelativeLayout screen_bg;
    @BindView(R.id.sanjiao_iv)
    ImageView sanjiao_iv;
    private int page = 1;
    private int pageSize;
    private LoadingDialog loadingDialog;
    private int askType;
    private List<GoodsManageModel> mLvList = new ArrayList<>();
    private MyExtensionLvAdapter mAdapter;
    private int activityId = 0;
    private View chooseTypeView;
    private LinearLayout allExtensionLl,allCityProductLl,normalCouponLl,activeCouponLl,scoreLl,secKillLl,pintuanLl;
    private PopupWindow mChooseTypePw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_extension);
        ButterKnife.bind(this);
        initData();
        askNetData();
    }

    private void askNetData() {
        Request<MyextensionModel> request = new Request<>();
        MyextensionModel model = new MyextensionModel();
        //商品审核
        model.setActivityId(activityId);
        model.setPageIndex(page);
        request.setData(model);
        RXUtils.request(this, request, "getGodosListByStatus", new SupportSubscriber<Response<GoodManageResModel>>() {

            @Override
            public void onNext(Response<GoodManageResModel> goodsManageModelResponse) {
                pageSize = goodsManageModelResponse.getData().getPageSize();
                if (askType == 0) {
                    mLvList.clear();
                }
                mLvList.addAll(goodsManageModelResponse.getData().getDetailList());
                if (mLvList.size() == 0) {
                    myExtensionLv.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);
                }else {
                    myExtensionLv.setVisibility(View.VISIBLE);
                    emptyLayout.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }
                myExtensionLv.onRefreshComplete();
            }

            @Override
            public void onCompleted() {
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
        });
    }

    private void initData() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        mAdapter = new MyExtensionLvAdapter(this, mLvList);
        myExtensionLv.setAdapter(mAdapter);
        myExtensionLv.setMode(PullToRefreshBase.Mode.BOTH);
        myExtensionLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                askType = 0;
                page = 1;
                askNetData();
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                askType = 1;
                page++;
                if (page <= pageSize) {
                    askNetData();
                }else {
                    myExtensionLv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myExtensionLv.onRefreshComplete();
                            Toast.makeText(MyExtensionActivity.this, "没有更多数据了哦!", Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);
                }
            }
        });
        mAdapter.setUpdateListenner(this);
    }

    @OnClick({R.id.back_iv,R.id.choose_type_ll,R.id.post_extension_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //回退
            case R.id.back_iv:
                finish();
                break;
            //选择类别
            case R.id.choose_type_ll:
                showTypePw();
                break;
            //全部推广
            case R.id.all_extension_ll:
                toolBarTitle.setText("全部推广");
                mChooseTypePw.dismiss();
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                activityId = 0;
                page = 1;
                askType = 0;
                askNetData();
                break;
            //全国特产
            case R.id.all_city_product_ll:
                toolBarTitle.setText("全国特产");
                mChooseTypePw.dismiss();
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                activityId = 1;
                page = 1;
                askType = 0;
                askNetData();
                break;
            //普通优惠券
            case R.id.normal_coupon_ll:
                toolBarTitle.setText("普通优惠券");
                mChooseTypePw.dismiss();
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                activityId = 4;
                page = 1;
                askType = 0;
                askNetData();
                break;
            //活动优惠券
            case R.id.active_coupon_ll:
                toolBarTitle.setText("活动优惠券");
                mChooseTypePw.dismiss();
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                activityId = 5;
                page = 1;
                askType = 0;
                askNetData();
                break;
            //积分活动
            case R.id.score_ll:
                toolBarTitle.setText("积分活动");
                mChooseTypePw.dismiss();
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                activityId = 3;
                page = 1;
                askType = 0;
                askNetData();
                break;
            //秒杀活动
            case R.id.sec_kill_ll:
                toolBarTitle.setText("秒杀活动");
                mChooseTypePw.dismiss();
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                activityId = 6;
                page = 1;
                askType = 0;
                askNetData();
                break;
            //拼团活动
            case R.id.pintuan_ll:
                toolBarTitle.setText("拼团活动");
                mChooseTypePw.dismiss();
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                activityId = 7;
                page = 1;
                askType = 0;
                askNetData();
                break;
            case R.id.post_extension_btn:
                AppUtils.toActivity(this, GoodsManageActivity.class);
                break;
        }
    }

    private void showTypePw() {
        screen_bg.setVisibility(View.VISIBLE);
        sanjiao_iv.setImageResource(R.drawable.icon_sanjiao1);
        if (mChooseTypePw == null) {
            chooseTypeView = LayoutInflater.from(this).inflate(R.layout.choose_type_pw_item,null);
            allExtensionLl = ((LinearLayout) chooseTypeView.findViewById(R.id.all_extension_ll));
            allCityProductLl = ((LinearLayout) chooseTypeView.findViewById(R.id.all_city_product_ll));
            normalCouponLl = ((LinearLayout) chooseTypeView.findViewById(R.id.normal_coupon_ll));
            activeCouponLl = ((LinearLayout) chooseTypeView.findViewById(R.id.active_coupon_ll));
            scoreLl = ((LinearLayout) chooseTypeView.findViewById(R.id.score_ll));
            secKillLl = ((LinearLayout) chooseTypeView.findViewById(R.id.sec_kill_ll));
            pintuanLl = ((LinearLayout) chooseTypeView.findViewById(R.id.pintuan_ll));
            allExtensionLl.setOnClickListener(this);
            allCityProductLl.setOnClickListener(this);
            normalCouponLl.setOnClickListener(this);
            activeCouponLl.setOnClickListener(this);
            scoreLl.setOnClickListener(this);
            secKillLl.setOnClickListener(this);
            pintuanLl.setOnClickListener(this);
            mChooseTypePw = new PopupWindow(chooseTypeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mChooseTypePw.setBackgroundDrawable(new ColorDrawable());
            mChooseTypePw.setFocusable(true);
            mChooseTypePw.setOutsideTouchable(true);
            mChooseTypePw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    screen_bg.setVisibility(View.GONE);
                    sanjiao_iv.setImageResource(R.drawable.icon_sanjiao);
                }
            });
        }
        mChooseTypePw.showAsDropDown(toolbarLayout);
    }

    @Override
    public void updateData() {
        askType = 0;
        page = 1;
        askNetData();
    }
}
