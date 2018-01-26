package com.axp.axpseller.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.HomeActivity;
import com.axp.axpseller.activitys.LoginOptionActivity;
import com.axp.axpseller.core.BaseFragment;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.dao.sp.UserInfoSP;
import com.axp.axpseller.models.UserInfo;
import com.axp.axpseller.models.bean.RongUserInfo;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.NetworkUtils;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.T;
import com.axp.axpseller.views.dialogs.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by xu on 2016/5/24.
 * 登录使用的Fragment
 */
public class LoginFragment extends BaseFragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;

    View mView;
    LoadingDialog loadingDialog;
    LoginOptionActivity mActivity;
    @BindView(R.id.iv_see)
    ImageView ivSee;
    boolean eyeOpen = false ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_login, container, false);
        loadingDialog = new LoadingDialog(getActivity());
        ButterKnife.bind(this, mView);

        mActivity = (LoginOptionActivity) getActivity();
        ivSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( eyeOpen ){
                    //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用
                    tvPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivSee.setImageResource( R.drawable.icon_yj_click );
                    eyeOpen = false ;
                }else {
                    //明文
                    tvPwd.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    ivSee.setImageResource( R.drawable.icon_yj );
                    eyeOpen = true ;
                }
            }
        });
        return mView;
    }

    @OnClick({R.id.tv_forget_password, R.id.tv_free_register, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                mActivity.forgetPassWord(true);
                break;
            case R.id.tv_free_register:
                mActivity.register(true);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    /**
     * 开始登录
     */
    private void login() {


        /*Subscriber<UserInfo> subscriber = new Subscriber<UserInfo>() {

            @Override
            public void onCompleted() {
                loadingDialog.hide(); //运行结束后执行
            }

            @Override
            public void onError(Throwable e) {  //发生错误后执行
                Log.e("error", "错误", e);
                loadingDialog.hide();
                SnackBar snackBar = new SnackBar(getActivity(), "未知错误");
                snackBar.show();
            }

            @Override
            public void onNext(UserInfo userInfo) {  //执行完成后执行
                getActivity().finish();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                //     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };

        UserInfo userInfo = new UserInfo();
        userInfo.setName(tvName.getText().toString());
        userInfo.setPwd(tvPwd.getText().toString());

        loadingDialog.show();
        Observable.just(userInfo)
                .filter((UserInfo u) -> {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        T.showShort(getActivity(), R.string.alert_no_network);
                        return false;
                    }
                    return true;
                })  //有网络的情况下继续执行
                .filter(u -> validate(u))  //验证数据是否正确
                .map(userInfo1 -> {
                    loadingDialog.show();
                    return userInfo1;
                })
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map(u -> { //开始登录请求数据

                    try {
                        Request<UserInfo> request = new Request<UserInfo>();
                        request.setData(u);

                        Response<UserInfo> response = HTTP.getAPI().login(HTTP.formatJSONData(request)).execute().body();
                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map(new Func1<Response<UserInfo>, Response<UserInfo>>() {  //开始加载个人信息
                    @Override
                    public Response<UserInfo> call(Response<UserInfo> userInfoResponse) {
                        try {
                            Request request = new Request();
                            request.setUserId(userInfoResponse.getData().getUserId());
                            Response<UserInfo> response = HTTP.getAPI().getUserInfo(HTTP.formatJSONData(request)).execute().body();
                            return response;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map(new Func1<Response<UserInfo>, UserInfo>() {  //将个人信息缓存
                    @Override
                    public UserInfo call(Response<UserInfo> userInfoResponse) {
                        UserInfoSP.setUserInfo(userInfoResponse.getData());
                        return userInfoResponse.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);*/
        if (!NetworkUtils.isConnected(getActivity())) {
            T.showShort(getActivity(), R.string.alert_no_network);
            return;
        }
        Request<UserInfo> request = new Request<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setName(tvName.getText().toString());
        userInfo.setPwd(tvPwd.getText().toString());
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "login", new SupportSubscriber<Response<UserInfo>>() {

            @Override
            public void onNext(Response<UserInfo> userInfo) {
                UserInfoSP.setUserInfo(userInfo.getData());
                Request<RongUserInfo> request = new Request<>();
                RongUserInfo rongUserInfo = new RongUserInfo();
                rongUserInfo.setUserId(ContextParameter.getUserInfo().getAxpAdminUserId());
                request.setData(rongUserInfo);
                RXUtils.request(getActivity(), request, "getRongUserInfo", new SupportSubscriber<Response<RongUserInfo>>() {
                    @Override
                    public void onNext(Response<RongUserInfo> rongUserInfoResponse) {
                        io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(rongUserInfoResponse.getData().getUserId(), rongUserInfoResponse.getData().getName(), Uri.parse(rongUserInfoResponse.getData().getPortraitUri()));
                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                    }
                });
                RongIM.connect(ContextParameter.getUserInfo().getToken(), new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {
                        Log.d("雨落无痕丶", "Rong_token错误 ");
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d("雨落无痕丶", "onSuccess: 融云连接成功:" + s);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
                getActivity().finish();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("sellerId",userInfo.getData().getSellerId());
//                intent.putExtras(bundle);
                //     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(),response.getMessage());
            }

            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(getActivity());
                dialog.show();
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        tvName.setText("");
        tvPwd.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadingDialog.dismiss();
    }

    /**
     * 验证用户输入
     *
     * @param userInfo
     * @return
     */
    private boolean validate(UserInfo userInfo) {

       /* if (StringUtils.isBlank(userInfo.getName()) || StringUtils.isBlank(userInfo.getPwd())) {
            T.showShort(getActivity(), R.string.alert_name_and_pwd_notnull);
            return false;
        } else if (!StringUtils.validataPhoneNumber(userInfo.getName())) {
            T.showShort(getActivity(), R.string.alert_illegal_phone_number);
            return false;
        }*/

        return true;
    }
}
