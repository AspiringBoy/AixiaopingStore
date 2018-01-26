package com.axp.axpseller.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.activitys.sellerinfo.SellerRegistSuccessfulActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.dao.sp.UserInfoSP;
import com.axp.axpseller.models.UserInfo;
import com.axp.axpseller.network.HTTP;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.network.StatusCode;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.NetworkUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.utils.T;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2016/6/1.
 * 实现手机短信验证
 * 注册功能
 */
public class RegisterFragment extends BaseFragment {
    @BindView(R.id.edt_regist_phone)
    EditText edtPhoneNumber;
    @BindView(R.id.edt_captcha)
    EditText edtCaptcha;
    @BindView(R.id.edt_regist_password)
    EditText edtPassWord;
  /*  @BindView(R.id.edt_invitation_code)
    EditText edtInvitationCode;
    @BindView(R.id.cb_consent_agreement)
    CheckBox agreement;*/
    @BindView(R.id.btn_send_captcha)
    TextView btnSendCaptcha;

    View mView;
   /* @BindView(R.id.tool_bar)
    Toolbar toolBar;*/

    private String phoneNumber, captcha, password, invationcode;
    private TimeCount time;
    private SimpleDateFormat formatMD5 = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_regist, container, false);

        ButterKnife.bind(this, mView);
        time = new TimeCount(60000, 1000);
      /*  toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });*/
        return mView;

    }

    @OnClick({R.id.btn_send_captcha, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_captcha:
                phoneNumber = edtPhoneNumber.getText().toString();
                if (!StringUtils.isBlank(phoneNumber)) {
                    if (StringUtils.validataPhoneNumber(phoneNumber) == false) {
                        T.showShort(getActivity(), "请输入正确的手机号码");
                    } else {
                        getCaptcha();
                        time.start();
                    }
                } else {
                    T.showShort(getActivity(), "请输入手机号码");
                }
                break;
            case R.id.btn_register:
                phoneNumber = edtPhoneNumber.getText().toString();
                captcha = edtCaptcha.getText().toString();
                password = edtPassWord.getText().toString();

                if (!StringUtils.isBlank(phoneNumber) && !StringUtils.isBlank(captcha) && !StringUtils.isBlank(password)) {
                        if(password.length()<6||password.length()>16){
                            T.showShort(getActivity(), "密码的长度不能小于六位或者大于十六位");
                        }else {
                            register();
                        }

                } else {
                    T.showShort(getActivity(), "请完善你的信息");
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        edtCaptcha.setText("");
        edtPassWord.setText("");
        edtPhoneNumber.setText("");
        time.cancel();
    }

    /**
     * 获得验证码
     */
    private void getCaptcha() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(edtPhoneNumber.getText().toString());
        Observable.just(userInfo)
                .filter((UserInfo u) -> {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        T.showShort(getActivity(), R.string.alert_no_network);
                        return false;
                    }
                    return true;
                }).observeOn(Schedulers.computation())
                .map((UserInfo u) -> { //开始注册请求数据

                    try {
                        Request<UserInfo> request = new Request<UserInfo>();
                        request.setData(u);

                        Response<UserInfo> response = HTTP.getAPI().sendCaptcha(HTTP.formatJSONData(request)).execute().body();
                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<UserInfo>>() {
            @Override
            public void call(Response<UserInfo> userInfoResponse) {
                if(userInfoResponse!=null) {
                    if (userInfoResponse.getStatus() == 1) {
                        T.showShort(getActivity(), userInfoResponse.getMessage());

                    }
                }
            }
        });


    }

    /**
     * 注册
     */
    private void register() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(edtPhoneNumber.getText().toString());
        userInfo.setPwd(edtPassWord.getText().toString());
        userInfo.setCaptcha(edtCaptcha.getText().toString());
       // userInfo.setInviteCode(edtInvitationCode.getText().toString());
        Observable.just(userInfo)
                .filter((UserInfo u) -> {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        T.showShort(getActivity(), R.string.alert_no_network);
                        return false;
                    }
                    return true;
                })  //有网络的情况下继续执行
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map((UserInfo u) -> { //开始注册请求数据

                    try {
                        Request<UserInfo> request = new Request<UserInfo>();
                        request.setData(u);

                        Response<UserInfo> response = HTTP.getAPI().register(HTTP.formatJSONData(request)).execute().body();
                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })//注册成功
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Response<UserInfo>, Response<UserInfo>>() {
                    @Override
                    public Response<UserInfo> call(Response<UserInfo> userInfoResponse) {
                        return userInfoResponse;
                    }
                })
                .filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map((Response<UserInfo> u) -> { //开始登录请求数据

                    try {
                        Request<UserInfo> request = new Request<UserInfo>();
                        request.setData(u.getData());

                        Response<UserInfo> response = HTTP.getAPI().login(HTTP.formatJSONData(request)).execute().body();
                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .observeOn(Schedulers.computation())
                .map(new Func1<Response<UserInfo>, Response<UserInfo>>() {  //开始加载个人信息
                    @Override
                    public Response<UserInfo> call(Response<UserInfo> userInfoResponse) {
                        try {
                            Request request = new Request();
                            request.setUserId(userInfoResponse.getData().getUserId());
                            request.setAxp(md5(md5("axp"+userInfoResponse.getData().getUserId())+formatMD5.format(System.currentTimeMillis())));
                            Response<UserInfo> response = HTTP.getAPI().getBaseInfo(HTTP.formatJSONData(request)).execute().body();
                            return response;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .map(new Func1<Response<UserInfo>, UserInfo>() {  //将个人信息缓存
                    @Override
                    public UserInfo call(Response<UserInfo> userInfoResponse) {
                        UserInfoSP.setUserInfo(userInfoResponse.getData());
                        return userInfoResponse.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfo>() {           //结束操作
                    @Override
                    public void call(UserInfo userInfo) {
                        Bundle bundle = new Bundle();
                        bundle.putString("inviteCode", userInfo.getInviteCode());
                        AppUtils.toActivity(getActivity(), SellerRegistSuccessfulActivity.class, bundle);
                    }
                });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btnSendCaptcha.setText(R.string.get_auth_code);
            btnSendCaptcha.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btnSendCaptcha.setClickable(false);//防止重复点击
            btnSendCaptcha.setText(millisUntilFinished / 1000 + " s");

        }
    }

    private static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
