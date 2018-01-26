package com.axp.axpseller.dao.sp;

import android.content.Context;

import com.axp.axpseller.ArchitectureAppliation;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.models.SellersInfo;
import com.axp.axpseller.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dong on 2016/12/8.
 */
public class SellerInfoSp {

    /** 当前登录的用户信息 */
    public static final String FILE_SELLER_INFO = "FILE_SELLER_INFO";

    /** 当前登录的商家ID */
    public static final String KEY_SELLER_INFO_USER_ID = "KEY_SELLER_INFO_USER_ID";

    /** 当前登录的用户ID */
    public static final String KEY_ASMINUSERID_USER_ID = "KEY_ASMINUSERID_USER_ID";

    public static void setSellersInfo(SellersInfo sellersInfo){
        Map<String,String> values = new HashMap<>();
        values.put(KEY_SELLER_INFO_USER_ID,sellersInfo.getSellerId());
        values.put(KEY_ASMINUSERID_USER_ID,sellersInfo.getAdminuserId());
        ContextParameter.setSellersInfo(sellersInfo);

        SPUtils.clear(ArchitectureAppliation.getAppliation().getApplicationContext(), FILE_SELLER_INFO);
        SPUtils.puts(ArchitectureAppliation.getAppliation().getApplicationContext(), FILE_SELLER_INFO, values);
    }
    /**
     * 清空
     * @param context
     */
    public static void clear(Context context){
        SPUtils.clear(context, FILE_SELLER_INFO);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static SellersInfo getSellersInfo(){
        SellersInfo sellerInfo = new SellersInfo();

        Map<String, ?> values = SPUtils.getAll(ArchitectureAppliation.getAppliation().getApplicationContext(), FILE_SELLER_INFO);
        sellerInfo.setSellerId(SPUtils.getValueByMapToString(values,KEY_SELLER_INFO_USER_ID, null));
        sellerInfo.setAdminuserId(SPUtils.getValueByMapToString(values,KEY_ASMINUSERID_USER_ID, null));


        return  sellerInfo;
    }
}
