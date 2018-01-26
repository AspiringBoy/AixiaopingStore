package com.axp.axpseller.fragments.mall;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.URIResolve;
import com.axp.axpseller.activitys.LoginOptionActivity;
import com.axp.axpseller.activitys.mall.SellerListActivity;
import com.axp.axpseller.activitys.order.BackOrderListActivity;
import com.axp.axpseller.activitys.order.OrderActivity;
import com.axp.axpseller.activitys.user.BindingContactsActivity;
import com.axp.axpseller.activitys.user.ExemptActivity;
import com.axp.axpseller.activitys.user.GoodsConcernActivity;
import com.axp.axpseller.activitys.user.LockScreenActivity;
import com.axp.axpseller.activitys.user.MyAddressActivity;
import com.axp.axpseller.activitys.user.MyFansActivity;
import com.axp.axpseller.activitys.user.MyMessageActivity;
import com.axp.axpseller.activitys.user.MyScoreActivity;
import com.axp.axpseller.activitys.user.MyStoreActivity;
import com.axp.axpseller.activitys.user.PersonInformationActivity;
import com.axp.axpseller.activitys.user.SettingActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.dao.sp.UserInfoSP;
import com.axp.axpseller.models.ImageAndText;
import com.axp.axpseller.models.UserInfo;
import com.axp.axpseller.network.HTTP;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.QRCodeUtil;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.custom.SuperGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/6/6.
 * 个人中心
 */
public class PersonalCenterFragment extends BaseFragment {
    View mView;
    @BindView(R.id.iv_head_portrait)
    ImageView mHeadPortrait;//头像
    @BindView(R.id.gv_layout)
    SuperGridView mGridView;
    @BindView(R.id.tv_user_name)
    TextView mUserName;
    @BindView(R.id.tv_vip_grade)
    TextView mUserVipGrade;
    @BindView(R.id.tv_user_integral)
    TextView mUserIntegral;
    @BindView(R.id.tv_user_red_envelope_earnings)
    TextView mUserRedEvelope;
    @BindView(R.id.tv_user_cash_earnings)
    TextView mUserCash;
    @BindView(R.id.tv_user_exempt)
    TextView mUserExempt;
    @BindView(R.id.tv_my_invitation_code)
    TextView mUserInvitationCode;
    @BindView(R.id.rl_score)//积分显示
     RelativeLayout rlScre;
    @BindView(R.id.tv_my_fans)
    TextView fans;
    @BindView(R.id.ll_exempt)
    LinearLayout llExempt;
    @BindView(R.id.tv_look_at_order)
    TextView tvLookAtOrder;
    @BindView(R.id.tv_to_evaluate)
    TextView tvToEvaluate;
    @BindView(R.id.tv_acknowledged)
    TextView tvAcknowledged;
    @BindView(R.id.tv_have_evaluation)
    TextView tvHaveEvaluation;
    @BindView(R.id.tv_off_the_stocks)
    TextView tvOffTheStocks;
    @BindView(R.id.iv_user_two_dimension_code)
    ImageView ivUserTwoDimensionCode;
    @BindView(R.id.rl_binding_contacts)
    RelativeLayout rlBindingContacts;
    @BindView(R.id.iv_inviter_img)
    ImageView ivInviterImg;
    @BindView(R.id.tv_inviter_name)
    TextView tvInviterName;
    @BindView(R.id.ll_inviter)
    LinearLayout llInviter;
    private int[] drawint = new int[]{R.drawable.icon_jifen_wode_my,
            R.drawable.icon_dizi_wode_my,/* R.drawable.ixon_tixian_my,*/
            R.drawable.icon_ziliao_my, R.drawable.icon_dianpu,
            R.drawable.icon_guangzhu_my,/*R.drawable.icon_shenri,*/
            R.drawable.icon_suoping_my, R.drawable.icon_woshishangjia_my,
            R.drawable.icon_camera, R.drawable.icon_shezi_my
    };
    private String[] userString = new String[]{
            "我的积分", "我的地址"/*,"我的提现"*/,"个人资料",
            "店铺收藏", "商品关注"/*,"送礼提醒"*/, "锁屏开关",
            "我是商家", "我的监控", "我的设置"
    };
    private List<ImageAndText> data;
    private Bitmap logo;
    private String mUrl;
    private String name,pwd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_personal_center, container, false);

        ButterKnife.bind(this, mView);

        init();

        return mView;

    }

    private void init() {

        data = new ArrayList<>();

        mGridView.setFocusable(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        addData();
        if (ContextParameter.isLogin() == true) {
            getUserInfo(ContextParameter.getUserInfo().getUsername());
        } else {
            remove();
        }
        setDate();

    }
    /**
     * 获取个人中心数据
     */
    private void getUserInfo(String userId) {
        Request<UserInfo> request = new Request<UserInfo>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "getUserInfo", new SupportSubscriber<Response<UserInfo>>() {
            @Override
            public void onNext(Response<UserInfo> response) {
                UserInfoSP.setUserInfo(response.getData());
                setDate();
                if (StringUtils.isBlank(ContextParameter.getUserInfo().getSellerId())) {
                    remove();
                } else {
                    addData();
                }
            }
        });

    }

    /**
     * 给各控件设值
     */
    private void setDate() {
        if (ContextParameter.isLogin() == true) {

            rlScre.setVisibility(View.VISIBLE);

            mUserIntegral.setText("积分 " + ContextParameter.getUserInfo().getScore());
            //设置用户昵称
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getUsername())) {
                mUserName.setText("未设置");
            } else {
                mUserName.setText(ContextParameter.getUserInfo().getUsername());
            }
            //设置用户头像
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getHeadimage())) {

                mHeadPortrait.setImageResource(R.drawable.icon_defult);

            } else {

                Glide.with(getActivity()).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(mHeadPortrait) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                            mHeadPortrait.setImageDrawable(circularBitmapDrawable);
                            logo = resource;

                    }
                });
            }
            //粉丝
            fans.setText(ContextParameter.getUserInfo().getFansNumber() + "粉丝>");

            mUserRedEvelope.setText(ContextParameter.getUserInfo().getCashpoint());
            mUserCash.setText(ContextParameter.getUserInfo().getAvailableMoney());
            mUserExempt.setText(ContextParameter.getUserInfo().getFreeCount());
            //会员类型
            if(!StringUtils.isBlank(ContextParameter.getUserInfo().getVipType())) {
                if (ContextParameter.getUserInfo().getVipType().equals("0")) {
                    mUserVipGrade.setText("未开通会员");
                } else {
                    mUserVipGrade.setText(ContextParameter.getUserInfo().getVipType() + "级会员");
                }
            }
            mUserInvitationCode.setText(ContextParameter.getUserInfo().getInviteCode());
            //是否为商家
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getSellerId())) {
                remove();
            }
            //是否绑定推荐人，如果绑定则显示绑定上级头像及昵称
           if(ContextParameter.getUserInfo().getBindingInviter()==true) {
               llInviter.setVisibility(View.VISIBLE);
               rlBindingContacts.setVisibility(View.GONE);
               if (StringUtils.isBlank(ContextParameter.getUserInfo().getInviterImg())) {
                   ivInviterImg.setImageResource(R.drawable.icon_defult);
               } else {
                   Glide.with(getActivity()).load(ContextParameter.getUserInfo().getInviterImg()).asBitmap().error(R.drawable.icon_defult).placeholder(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(ivInviterImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           ivInviterImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
               }
               if(!StringUtils.isBlank(ContextParameter.getUserInfo().getInviterName())){
                   tvInviterName.setText(ContextParameter.getUserInfo().getInviterName());
               }
               }else{
                   llInviter.setVisibility(View.GONE);
                   rlBindingContacts.setVisibility(View.VISIBLE);

               }
            if(StringUtils.isBlank(ContextParameter.getUserInfo().getTdCode())){
                if(StringUtils.isBlank(ContextParameter.getUserInfo().getInviteCode())){
                    mUrl = ContextParameter.getClientConfig().getDownload()+"?invitecode="+"";
                }else {
                    mUrl = ContextParameter.getClientConfig().getDownload() + "?invitecode=" + ContextParameter.getUserInfo().getInviteCode();
                }
            }else{
                mUrl = ContextParameter.getUserInfo().getTdCode();
            }

        } else {
            rlScre.setVisibility(View.GONE);
            mUserName.setText("未登录");
            mUserVipGrade.setText("");
            mUserRedEvelope.setText("0");
            mUserCash.setText("0");
            mUserExempt.setText("0");
            mHeadPortrait.setImageResource(R.drawable.icon_defult);
            mUserInvitationCode.setText("");
            remove();
            fans.setText("");
            llInviter.setVisibility(View.GONE);
            rlBindingContacts.setVisibility(View.VISIBLE);
            ivInviterImg.setImageResource(R.drawable.icon_defult);
            mUrl = ContextParameter.getClientConfig().getDownload()+"?invitecode="+"";

        }

    }

    @OnClick({R.id.tv_look_at_order, R.id.tv_to_evaluate, R.id.tv_acknowledged, R.id.tv_have_evaluation, R.id.tv_off_the_stocks,
            R.id.tv_user_name, R.id.iv_head_portrait, R.id.iv_message, R.id.iv_two_dimension_code,
            R.id.iv_user_two_dimension_code, R.id.tv_my_fans, R.id.tv_binding_contacts, R.id.ll_exempt,
            R.id.tv_must_see_strategy,R.id.ll_red_packet,R.id.ll_link_money})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look_at_order://查看全部订单
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), OrderActivity.class);
                }
                break;
            case R.id.tv_to_evaluate://待付款
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_PAY);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            case R.id.tv_acknowledged://待确认
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            case R.id.tv_have_evaluation://待评价
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_COMMENT);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            case R.id.tv_off_the_stocks://已完成
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), BackOrderListActivity.class);
                }
                break;
            case R.id.iv_user_two_dimension_code://点击查看二维码
                showTwoDimensionCode(mUrl);
                break;
            case R.id.tv_my_fans://查看粉丝
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("number", ContextParameter.getUserInfo().getFansNumber());
                    AppUtils.toActivity(getActivity(), MyFansActivity.class, bundle);

                }
                break;
            case R.id.tv_binding_contacts://绑定联系人
                if (ContextParameter.isLogin() == false) {

                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);

                } else {

                    AppUtils.toActivity(getActivity(), BindingContactsActivity.class);

                }

                break;

            case R.id.tv_must_see_strategy://必看攻略
                URIResolve.resolve(getActivity(), HTTP.URL_SEE_STRATEGY + HTTP.formatJSONData(new Request()));

                break;
            case R.id.ll_red_packet:
                if (ContextParameter.isLogin() == false) {

                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);

                } else {

                    URIResolve.resolve(getActivity(), HTTP.URL_RED_PAPER_LIST + HTTP.formatJSONData(new Request()));

                }
                break;
            case R.id.ll_link_money:
                if (ContextParameter.isLogin() == false) {

                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);

                } else {

                    URIResolve.resolve(getActivity(), HTTP.URL_MONEY_LINK_LIST + HTTP.formatJSONData(new Request()));

                }
                break;

            case R.id.tv_user_name:

                if (ContextParameter.isLogin() == false) {

                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);

                } else {

                    AppUtils.toActivity(getActivity(), PersonInformationActivity.class);

                }

                break;

            case R.id.iv_head_portrait:

                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), PersonInformationActivity.class);
                }
                break;

            case R.id.iv_message:

                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), MyMessageActivity.class);
                }
                break;

            case R.id.iv_two_dimension_code:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    QRCodeUtil.scan(getActivity());
                }
                break;
            case R.id.ll_exempt:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), ExemptActivity.class);
                }
                break;
        }
    }


    /**
     * 适配器
     */
    private BaseAdapter userBaseAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final int Localposition = position;
            if (convertView == null) {
                convertView = getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.item_user_personal_center, parent, false);
            }
            ImageView imageview = (ImageView) convertView
                    .findViewById(R.id.imageview_item_user_center);
            TextView textView = (TextView) convertView
                    .findViewById(R.id.textview_item_grid_user_center);
            imageview.setImageResource(data.get(position).getImageId()); // 图片
            textView.setText(data.get(position).getText());

            RelativeLayout lItem = (RelativeLayout) convertView
                    .findViewById(R.id.ll_item);// 线性布局
            lItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    switch (data.get(position).getId()) {
                        case 0:
                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                AppUtils.toActivity(getActivity(), MyScoreActivity.class);
                            }

                            break;
                        case 1:
                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                AppUtils.toActivity(getActivity(), MyAddressActivity.class);
                            }
                            break;
                    /*    case 2:

                            if(ContextParameter.isLogin()==false){
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            }else {
                                AppUtils.toActivity(getActivity(), MyDataVerificationActivity.class);
                            }

                            break;*/
                        case 2:

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                AppUtils.toActivity(getActivity(), PersonInformationActivity.class);
                            }

                            break;
                        case 3:

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putInt("KEY_SELECT",1);
                                AppUtils.toActivity(getActivity(), SellerListActivity.class,bundle);
                            }

                            break;
                        case 4:

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                AppUtils.toActivity(getActivity(), GoodsConcernActivity.class);
                            }

                            break;
                       /* case 5:

                            if(ContextParameter.isLogin()==false){
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            }else {
                                AppUtils.toActivity(getActivity(), MyScoreActivity.class);
                            }

                            break;*/
                        case 5:

                            AppUtils.toActivity(getActivity(), LockScreenActivity.class);

                            break;
                        case 6:

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                    AppUtils.toActivity(getActivity(), MyStoreActivity.class);

                            }
                            break;
                        case 7:

                           // AppUtils.toActivity(getActivity(), LoginActivity.class);

                            break;
                        case 8:

                            AppUtils.toActivity(getActivity(), SettingActivity.class);

                            break;
                    }
                }
            });
            return convertView;
        }

    };

    /**
     * 删除data数据
     */
    private void remove() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == 6) {
                data.remove(data.get(6));
                break;
            }
        }
        mGridView.setAdapter(userBaseAdapter);
    }

    /**
     * 添加data数据
     */
    private void addData() {
        data.clear();
        for (int i = 0; i < userString.length; i++) {
            ImageAndText it = new ImageAndText();
            it.setId(i);
            it.setText(userString[i]);
            it.setImageId(drawint[i]);
            data.add(it);
        }
        mGridView.setAdapter(userBaseAdapter);
    }

    /**
     * 点击出现二维码
     */
    private void showTwoDimensionCode(String content) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        //得到界面视图
        View currean_View = inflater.inflate(R.layout.fragment_personal_center, null);
        //得到要弹出的界面视图
        View view = inflater2.inflate(R.layout.view_two_dimension_code_show, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_two_dimension_code);
        Bitmap bitmap = QRCodeUtil.createQRImage(content, 300, 300);
        imageView.setImageBitmap(bitmap);
        WindowManager windowManager = getActivity().getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        int heigth = windowManager.getDefaultDisplay().getHeight();
        Log.i("width", width + "");
        Log.i("height", heigth + "");
        PopupWindow popupWindow = new PopupWindow(view, (int) (width * 0.8), (int) (heigth * 0.5));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示在屏幕中央
        popupWindow.showAtLocation(currean_View, Gravity.CENTER, 0, 40);
        //popupWindow弹出后屏幕半透明
        BackgroudAlpha((float) 0.5);
        //弹出窗口关闭事件
        popupWindow.setOnDismissListener(new popupwindowdismisslistener());
    }

    //设置屏幕背景透明度
    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = getActivity().getWindow().getAttributes();
        l.alpha = alpha;
        getActivity().getWindow().setAttributes(l);
    }

    //点击其他部分popwindow消失时，屏幕恢复透明度
    class popupwindowdismisslistener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            BackgroudAlpha((float) 1);
        }
    }
}
