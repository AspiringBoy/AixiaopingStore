package com.axp.axpseller.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.axp.axpseller.Application;
import com.axp.axpseller.ArchitectureAppliation;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.dao.serialize.ZoneClientSerialize;
import com.axp.axpseller.dao.sp.LocalConfigSP;
import com.axp.axpseller.managers.LocationManager;
import com.axp.axpseller.managers.LocationService;
import com.axp.axpseller.models.Location;
import com.axp.axpseller.models.Zone;
import com.axp.axpseller.models.ZoneList;
import com.axp.axpseller.models.bean.ZoneListBean;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.L;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/4/28.
 * 第一次启动的主界面
 */
public class LauncherActivity extends BaseActivity {

    public static final int MIN_STOP_TIME = 2000;
    public long startTime;
    @BindView(R.id.layout)
    RelativeLayout layout;
    LocationService locationService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);



    }

    @Override
    protected void onResume() {
        super.onResume();

        Application application = (Application) getApplication();
        if (!application.alreadyInit) {
          //原来的默认图  layout.setBackgroundResource(R.drawable.splash_adver);
            layout.setBackgroundResource(R.drawable.loading);
            startTime = System.currentTimeMillis();
            application.initApplication();
            init();
        } else {
            start();
        }
    }

    private void init() {
       // loadGlideAdvert();
        Subscriber<Object> subscriber = new Subscriber<Object>() {
            @Override
            public void onCompleted() {

                long endTime = System.currentTimeMillis();
                long interval = endTime - startTime;

                if (interval <= MIN_STOP_TIME) {
                    start();
                } else {
                    new Handler().postAtTime(new Runnable() {
                        @Override
                        public void run() {
                            start();
                        }
                    }, (MIN_STOP_TIME - interval));
                }


            }

            @Override
            public void onError(Throwable e) {
                L.e("error", "error", e);
                onCompleted();
            }

            @Override
            public void onNext(Object o) {

            }
        };

        Observable.just(null).observeOn(Schedulers.computation())
                //网路数据初始化
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {

                        long start = System.currentTimeMillis();
                        locationService = ((Application) getApplication()).locationService;
                        //初始化定位
                        Location location = LocationManager.syncGetLocation();
                       if(location.getCity().equals("广州市")&&location.getDistrict().equals("萝岗区")){
                           location.setDistrict("黄埔区");
                       }
                   /*     LatLng latLng = new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
                        // 将GPS设备采集的原始GPS坐标转换成百度坐标
                        CoordinateConverter converter = new CoordinateConverter();
                        converter.from(CoordinateConverter.CoordType.GPS);
                        // latLng 待转换坐标
                        converter.coord(latLng);
                        LatLng desLatLng = converter.convert();
                        location.setLatitude(String.valueOf(desLatLng.latitude));
                        location.setLongitude(String.valueOf(desLatLng.longitude));
                        //将转换后的坐标添加到集合
                        L.e("未处理过的精度"+location.getLatitude());
                        L.e("未处理过的维度"+location.getLongitude());*/
                        ContextParameter.setCurrentLocation(location);

                        //初始化城市列表
                        long count = getSupportApplication().getDaoSession().getZoneDao().count();
                        if (count == 0) {
                            //对泛型做支持
                            Type objectType = new TypeToken<Response<ZoneList>>() {
                            }.getType();
                            Gson gson = new Gson();
                            String json = null;

                            try {

                                InputStream inputStream = getAssets().open("txt/city.txt");

                                byte[] bytes = new byte[inputStream.available()];

                                inputStream.read(bytes, 0, bytes.length);

                                json = new String(bytes, "UTF-8");

                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Response<ZoneList> response = gson.fromJson(json, objectType);

                            //配置版本
                            ZoneListBean config = new ZoneListBean();
                            config.setVersion(response.getData().getVersion());
                            ZoneClientSerialize.setClientConfig(ArchitectureAppliation.getAppliation(), config);

                            getSupportApplication().getDaoSession().getZoneDao().insertInTx(response.getData().getDataList());
                        }

                        handlerZone();

                        //初始化zone

                        long end = System.currentTimeMillis();
                        L.e("初始化耗时：" + (end - start));

                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);


    }

    public void start() {

        //判断版本是否升级，成立则显示引导页
        if (!ContextParameter.getAppVersion().equals(ContextParameter.getLocalConfig().getFirstLauncherVersion())) {
            AppUtils.toActivity(this, GuideActivity.class);
            ContextParameter.getLocalConfig().setFirstLauncherVersion(ContextParameter.getAppVersion());
            LocalConfigSP.setLocalConfig(ContextParameter.getLocalConfig());
            finish();
            return;
        }
        if (ContextParameter.isLogin() == false) {
            AppUtils.toActivity(this, LoginOptionActivity.class);
            finish();
        } else {
            AppUtils.toActivity(this, HomeActivity.class);
            finish();
        }
//        AppUtils.toActivity(this, HomeActivity.class);
//        finish();

//        if(ContextParameter.isLogin()){
//            AppUtils.toActivity(this, UnlockView.class);
//        } else {
//
//        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    /**
     * 处理城市
     */
    public void handlerZone() {
        Zone district = getSupportApplication().getDaoSession().getZoneDao()
                .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity() ,ContextParameter.getCurrentLocation().getDistrict());
        ContextParameter.setCurrentZone(district);
    }

}
