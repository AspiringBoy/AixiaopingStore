package com.axp.axpseller;

import android.graphics.Color;
import android.util.Log;

import com.axp.axpseller.managers.LocationService;
import com.axp.axpseller.model.CustomizeMessage;
import com.axp.axpseller.model.CustomizeMessageItemProvider;
import com.axp.axpseller.utils.MyImageLoader;
import com.axp.axpseller.utils.PicassoImageLoader;
import com.axp.axpseller.utils.ScreenSizeUtil;
import com.baidu.mapapi.SDKInitializer;
import com.igexin.sdk.PushManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.rey.material.app.ThemeManager;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.PlatformConfig;

import net.aixiaoping.unlock.views.UnlockView;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by xu on 2016/5/23.
 */
public class Application extends ArchitectureAppliation {

    private static Application mInstance;
    public boolean alreadyInit = false;

    private UnlockView unlockView;
    public LocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        RongIM.init(this,Constants.RONGIM_APP_KEY);
        //注册自定义消息
        RongIM.registerMessageType(CustomizeMessage.class);
        //注册自定义消息模块
        RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider());
        RongIM.getInstance().setReadReceiptConversationTypeList(Conversation.ConversationType.PRIVATE);
        LeakCanary.install(this);
    }

    /**
     * 初始化整个应用程序
     */
    public void initApplication(){

        if(!alreadyInit){
            super.init();

            // SDK初始化，第三方程序启动时，都要进行SDK初始化工作
            Log.d("GetuiSdkDemo", "initializing sdk...");
            PushManager.getInstance().initialize(this.getApplicationContext());
            locationService = new LocationService(getApplicationContext());
            PlatformConfig.setQQZone(Constants.QQ_APP_ID,Constants.QQ_APP_KEY);
            PlatformConfig.setWeixin(Constants.WEXIN_APP_ID,Constants.WEXIN_APP_APPSECRET);
            ThemeManager.init(this, 2, 0, null);

            setUnlockView(new UnlockView(this));

      /*      Intent intent = new Intent(getApplicationContext(), AthanasiaService.class);
            startService(intent);

            //启动一个服务，长期在后台隔半个小时刷新一次
            Intent refushIntent = new Intent(this, RefushService.class);
            startService(refushIntent);*/

            //配置主题
            // ThemeConfig.CYAN
            ThemeConfig theme = new ThemeConfig.Builder()
                    .setTitleBarBgColor(Color.parseColor("#F96B47"))
                    .setFabNornalColor(Color.parseColor("#F96B47"))
                    .setFabPressedColor(Color.parseColor("#F96B47"))
                    .setCheckSelectedColor(Color.parseColor("#F96B47"))
                    .setCropControlColor(Color.parseColor("#F96B47"))
                    .build();

            //配置功能
            FunctionConfig functionConfig = new FunctionConfig.Builder()
                    .setEnableCamera(true)
                    .setEnableEdit(true)
                    .setEnableCrop(true)
                    .setEnableRotate(true)
                    .setCropSquare(true)
                    .setEnablePreview(true)
                    .build();

            //配置imageloader
            ImageLoader imageloader = new MyImageLoader();
            //设置核心配置信息
            CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                    .setDebug(BuildConfig.DEBUG)
                    .setFunctionConfig(functionConfig)
                    .build();
            GalleryFinal.init(coreConfig);

            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
            imagePicker.setMultiMode(false);
            imagePicker.setShowCamera(true);  //显示拍照按钮
            imagePicker.setCrop(true);        //允许裁剪（单选才有效）
            imagePicker.setSaveRectangle(true); //是否按矩形区域保存
            imagePicker.setSelectLimit(9);    //选中数量限制
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
            imagePicker.setFocusWidth(ScreenSizeUtil.getScreenSize(this)[0]);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(ScreenSizeUtil.getScreenSize(this)[0]);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(640);//保存文件的宽度。单位像素
            imagePicker.setOutPutY(640);//保存文件的高度。单位像素

            alreadyInit = true;
        }
    }

    public void initInstance() {
        if (mInstance == null) {
            mInstance = this;
        }
    }
    public static Application getInstance() {
        return mInstance;
    }
    public UnlockView getUnlockView() {
        return unlockView;
    }

    public void setUnlockView(UnlockView unlockView) {
        this.unlockView = unlockView;
    }
}
