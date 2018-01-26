package com.axp.axpseller.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.axp.axpseller.ArchitectureAppliation;
import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.URIResolve;
import com.axp.axpseller.activitys.user.ScoreRechargeSuccessActivity;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.core.SupportSubscriber;
import com.axp.axpseller.fragments.ChatFragment2;
import com.axp.axpseller.fragments.sellerinfo.SellerAssetManagementFragment;
import com.axp.axpseller.fragments.sellerinfo.SellerCenterFragment;
import com.axp.axpseller.fragments.sellerinfo.SellerMallFragment;
import com.axp.axpseller.fragments.sellerinfo.SellerOrderFragment;
import com.axp.axpseller.models.bean.QrCodeContentModel;
import com.axp.axpseller.models.bean.RongUserInfo;
import com.axp.axpseller.models.bean.SendScoreDtResModel;
import com.axp.axpseller.models.config.ClientConfig;
import com.axp.axpseller.models.eventbus_message.UpdateOrderListMessage;
import com.axp.axpseller.models.eventbus_message.UpdateShoppingCarMessage;
import com.axp.axpseller.network.Request;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.DialogUtil;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.views.custom.CustomDialog;
import com.axp.axpseller.views.custom.MyRadiobutton;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by xu on 2016/5/25.
 * 商城界面
 */

public class HomeActivity extends BaseActivity {

    /**
     * 界面切换
     */
    //  MallFragment mMallFragment = new MallFragment();
    //首页
    SellerMallFragment mMallFragment = new SellerMallFragment();

    SellerAssetManagementFragment mFansFragment = new SellerAssetManagementFragment();
    //订单
    SellerOrderFragment mOrderFragment = new SellerOrderFragment();

    //聊天
    private Fragment mChatFragment = new ChatFragment2();

    //资料
    SellerCenterFragment mSellerInfoFragment = new SellerCenterFragment();

//    MallFragment mMallFragment = new MallFragment();
//    LazyFragment mShoppingCartFragment = new LazyFragment();
//    LazyFragment mOrderFragment = new LazyFragment();
//    LazyFragment mUserInfoFragment = new LazyFragment();

    public static final String KEY_SHOW = "KEY_SHOW";
    public static String SELLER_ID;


    private int show = Constants.HOME_SHOW_MALL;

    ArrayList<Fragment> mFragments = new ArrayList<>();

    /*@BindView(R.id.htv_tab)
    HomeTabView mTabView;*/
    @BindView(R.id.chat_un_read_count)
    TextView chat_un_read_count;
    @BindView(R.id.tab_rg)
    RadioGroup tab_rg;
    @BindView(R.id.main_rb)
    MyRadiobutton main_rb;
    @BindView(R.id.order_rb)
    MyRadiobutton order_rb;
    @BindView(R.id.chat_rb)
    MyRadiobutton chat_rb;
    @BindView(R.id.personal_center_rb)
    MyRadiobutton personal_center_rb;
    private static int REQUEST_CODE_GALLERY = 5;
    private ImageView cancleIv;
    private TextView update_content, version_tv;
    private Button updateBtn;
    private AlertDialog updateDialog;
    private ConversationListFragment mConversationListFg;
    private boolean isDebug = false;
    private Conversation.ConversationType[] mConversationsTypes;
    private IUnReadMessageObserver unReadMessageObserver;
    private int postion = 0;
    private Fragment mCurrentFragment;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
//        SELLER_ID = getIntent().getExtras().getString("sellerId");
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
        L.e("onCreate");
    }

    private void init() {
        Request<RongUserInfo> request = new Request<>();
        RongUserInfo rongUserInfo = new RongUserInfo();
        rongUserInfo.setUserId(ContextParameter.getUserInfo().getAxpAdminUserId());
        request.setData(rongUserInfo);
        RXUtils.request(HomeActivity.this, request, "getRongUserInfo", new SupportSubscriber<Response<RongUserInfo>>() {
            @Override
            public void onNext(Response<RongUserInfo> rongUserInfoResponse) {
                UserInfo userInfo = new UserInfo(rongUserInfoResponse.getData().getUserId(), rongUserInfoResponse.getData().getName(), Uri.parse(rongUserInfoResponse.getData().getPortraitUri()));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
            }
        });
        if (ContextParameter.getUserInfo().getToken() != null && ContextParameter.getUserInfo().getToken().length() > 0 && ContextParameter.isLogin()) {
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                @Override
                public UserInfo getUserInfo(String s) {
                    Request<RongUserInfo> request = new Request<>();
                    RongUserInfo rongUserInfo = new RongUserInfo();
                    rongUserInfo.setUserId(s);
                    request.setData(rongUserInfo);
                    RXUtils.request(HomeActivity.this, request, "getRongUserInfo", new SupportSubscriber<Response<RongUserInfo>>() {
                        @Override
                        public void onNext(Response<RongUserInfo> rongUserInfoResponse) {
                            Log.d("雨落无痕丶", "头像: " + rongUserInfoResponse.getData().getPortraitUri());
                            UserInfo userInfo = new UserInfo(rongUserInfoResponse.getData().getUserId(), rongUserInfoResponse.getData().getName(), Uri.parse(rongUserInfoResponse.getData().getPortraitUri()));
                            RongIM.getInstance().refreshUserInfoCache(userInfo);
                        }
                    });
                    return null;
                }
            }, true);
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
            unReadMessageObserver = new IUnReadMessageObserver() {
                @Override
                public void onCountChanged(int i) {
                    if (i > 0) {
                        chat_un_read_count.setVisibility(View.VISIBLE);
                        chat_un_read_count.setText(i + "");
                    } else chat_un_read_count.setVisibility(View.GONE);
                }
            };
            RongIM.getInstance().addUnReadMessageCountChangedObserver(unReadMessageObserver, Conversation.ConversationType.PRIVATE);
        }

        //旧版底部导航(HomeTabView)
        /*mFragments.add(mMallFragment);
        //   mFragments.add(mFansFragment);
        mFragments.add(mOrderFragment);
        mFragments.add(mChatFragment);
        mFragments.add(mSellerInfoFragment);
        mTabView.bindPageChange(R.id.container, mFragments);*/

        //新版底部导航
        tab_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.main_rb:
                        postion = 1;
                        if (mMallFragment == null) {
                            mMallFragment = new SellerMallFragment();
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        if (mMallFragment.isAdded()) {
                            transaction.hide(mCurrentFragment).show(mMallFragment).commit();
                        } else {
                            transaction.hide(mCurrentFragment).add(R.id.container, mMallFragment).commit();
                        }
                        mCurrentFragment = mMallFragment;
                        break;
                    case R.id.order_rb:
                        postion = 2;
                        if (mOrderFragment == null) {
                            mOrderFragment = new SellerOrderFragment();
                        }
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        if (mOrderFragment.isAdded()) {
                            transaction1.hide(mCurrentFragment).show(mOrderFragment).commit();
                        } else {
                            transaction1.hide(mCurrentFragment).add(R.id.container, mOrderFragment).commit();
                        }
                        mCurrentFragment = mOrderFragment;
                        break;
                    case R.id.chat_rb:
                        postion = 3;
                        if (mChatFragment == null) {
                            mChatFragment = new ChatFragment2();
                        }
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        if (mChatFragment.isAdded()) {
                            transaction2.hide(mCurrentFragment).show(mChatFragment).commit();
                        } else {
                            transaction2.hide(mCurrentFragment).add(R.id.container, mChatFragment).commit();
                        }
                        mCurrentFragment = mChatFragment;
                        break;
                    case R.id.personal_center_rb:
                        postion = 4;
                        if (mSellerInfoFragment == null) {
                            mSellerInfoFragment = new SellerCenterFragment();
                        }
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        if (mSellerInfoFragment.isAdded()) {
                            transaction3.hide(mCurrentFragment).show(mSellerInfoFragment).commit();
                        } else {
                            transaction3.hide(mCurrentFragment).add(R.id.container, mSellerInfoFragment).commit();
                        }
                        mCurrentFragment = mSellerInfoFragment;
                        break;
                }
            }
        });
        mCurrentFragment = new SellerMallFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mCurrentFragment).commit();

        loadClientConfig();
    }

    @Override
    protected void onResume() {
        super.onResume();

        L.e("onResume");

        getSupportApplication().onlyActivity(HomeActivity.class);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        L.e("onNewIntent-----------------");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //读取当前应该显示哪一项
            show = bundle.getInt(KEY_SHOW, Constants.HOME_SHOW_MALL);
//            mTabView.getTabLayout().setCurrentTab(show);
            switch (show) {
                case Constants.HOME_SHOW_MALL:
                    main_rb.setChecked(true);
                    break;
                case Constants.HOME_SHOW_ODER:
                    order_rb.setChecked(true);
                    break;
                case Constants.HOME_SHOW_USER_INFO:
                    personal_center_rb.setChecked(true);
                    break;
            }
        }

        EventBus.getDefault().post(new UpdateShoppingCarMessage());
        EventBus.getDefault().post(new UpdateOrderListMessage(Constants.ORDER_STATUS_WAIT_PAY));
    }


    /**
     * 初始化客户端配置
     */
    public void loadClientConfig() {

        RXUtils.request(this, new Request(), "getClientConfig", new SupportSubscriber<Response<ClientConfig>>() {
            @Override
            public void onNext(Response<ClientConfig> clientConfig) {
                ContextParameter.setClientConfig(clientConfig.getData());

                checkVersionUpdate();
            }
        });

    }

    /**
     * 检查版本更新
     */
    public void checkVersionUpdate() {
        int currentVersion = AppUtils.getVersionCode(this);
        String hasNewVerson = ContextParameter.getClientConfig().getHasNewVerson();
        if (getSupportApplication().alertVersionUpdate) {
            //判断时间间隔
            long currentTime = System.currentTimeMillis();
            long intevalTime = currentTime - getSupportApplication().alertVersionUpdateTime;

            if (intevalTime < getSupportApplication().MAX_ALERT_VERSION_UPDATE_TIME) {
                return;
            }

        }


        if (hasNewVerson.equals("1")) {
            /*boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
            SimpleDialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    super.onPositiveActionClicked(fragment);

                    Uri uri = Uri.parse(ContextParameter.getClientConfig().getNewVersionDownload());
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    super.onNegativeActionClicked(fragment);
                }
            };

            builder.message(ContextParameter.getClientConfig().getNewVersionMessage()).title("更新提示")
                    .positiveAction("立即更新")
                    .negativeAction("再等等");

            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getSupportFragmentManager(), null);

            getSupportApplication().alertVersionUpdate = true;
            getSupportApplication().alertVersionUpdateTime = System.currentTimeMillis();*/

            showUpdateDialog();
        }
    }

    private void showUpdateDialog() {
        View updateView = LayoutInflater.from(this).inflate(R.layout.update_tip_dialog, null);
        cancleIv = (ImageView) updateView.findViewById(R.id.cancle_iv);
        update_content = (TextView) updateView.findViewById(R.id.update_content);
        version_tv = (TextView) updateView.findViewById(R.id.version_tv);
        updateBtn = ((Button) updateView.findViewById(R.id.btn_update));
        version_tv.setText(ContextParameter.getClientConfig().getNewVersion());
        List<String> newVersonContents = ContextParameter.getClientConfig().getNewVersonContents();
        StringBuilder builder = new StringBuilder();
        if (newVersonContents != null && newVersonContents.size() > 0) {
            for (int i = 0; i < newVersonContents.size(); i++) {
                if (i != newVersonContents.size() - 1) {
                    builder.append(newVersonContents.get(i) + "\n");
                } else {
                    builder.append(newVersonContents.get(i));
                }
            }
        }
        update_content.setText(builder.toString());
//        update_content.setText(ContextParameter.getClientConfig().getNewVersonContents());
        cancleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDialog.dismiss();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportApplication().alertVersionUpdate = true;
                getSupportApplication().alertVersionUpdateTime = System.currentTimeMillis();
                Uri uri = Uri.parse(ContextParameter.getClientConfig().getNewVersionDownload());
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                updateDialog.dismiss();
            }
        });
        updateDialog = new AlertDialog.Builder(this, R.style.CustomDialog).setView(updateView).create();
        changeScreenBg();
        updateDialog.show();
        updateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                restoreScreenBg();
            }
        });
    }

    private void changeScreenBg() {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = (float) 0.5;
        this.getWindow().setAttributes(params);
    }

    private void restoreScreenBg() {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = (float) 1;
        this.getWindow().setAttributes(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Log.e("QRActivity", "Cancelled scan");
            } else {
                Log.e("QRActivity", "Scanned : " + result.getContents());
                if (!result.getContents().contains("ScorePresent:")) {
                    URIResolve.resolve(this, result.getContents());
                } else {
                    receiveQrScore(result.getContents());
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);

        }

    }

    private void receiveQrScore(String contents) {
        Request<SendScoreDtResModel> request = new Request<>();
        SendScoreDtResModel model = new SendScoreDtResModel();
        QrCodeContentModel qrCodeContentModel = new Gson().fromJson(contents.substring("ScorePresent:".length()), QrCodeContentModel.class);
        model.setPresenterId(qrCodeContentModel.getPresenterId());
        model.setScoreNum(qrCodeContentModel.getScore());
        model.setTime(qrCodeContentModel.getTime());
        request.setData(model);
        RXUtils.request(this, request, "receiveQRScore", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
//                DialogUtil.showNoticDialog(HomeActivity.this, "成功收到" + qrCodeContentModel.getScore() + "积分!");
                customDialog = DialogUtil.showCustomDialog(HomeActivity.this, R.style.customDialogStyle, R.layout.dialog_notice_item, 262, 141, response.getMessage(), R.id.dialog_notice_msg, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.btn_sure:
                                customDialog.dismiss();
                                goToIntent(qrCodeContentModel.getScore());
                                break;
                        }
                    }
                },R.id.btn_sure);
            }

            @Override
            public void onResponseError(Response response) {
                DialogUtil.showNoticDialog(HomeActivity.this, ""+response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                DialogUtil.showNoticDialog(HomeActivity.this,"收取积分失败!");
            }
        });
    }

    private void goToIntent(String score) {
        Bundle bundle = new Bundle();
        bundle.putString("score", score);
        bundle.putString("title", "收取积分");
        AppUtils.toActivity(this, ScoreRechargeSuccessActivity.class, bundle);
    }

    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出爱小屏商家版", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                superFinish();
                Glide.get(this).clearMemory();
//                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe
    public void reciveMsg(String msg) {
        if (msg.equals("qr")) {
            FunctionConfig config = new FunctionConfig.Builder().setMutiSelectMaxSize(8).setEnableCamera(true).setEnableCrop(true).setEnableEdit(true).build();
            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                    Toast.makeText(HomeActivity.this, resultList.get(0).getPhotoPath(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArchitectureAppliation.fixInputMethodManagerLeak(this);
        if (unReadMessageObserver != null) {
            RongIM.getInstance().removeUnReadMessageCountChangedObserver(unReadMessageObserver);
        }
    }
}

