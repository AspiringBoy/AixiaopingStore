package com.axp.axpseller.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.axp.axpseller.activitys.LoginActivity;
import com.axp.axpseller.dao.sp.SellerInfoSp;
import com.axp.axpseller.models.SellerInfo;
import com.axp.axpseller.models.SellersInfo;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.R;
import com.axp.axpseller.activitys.LoginOptionActivity;
import com.axp.axpseller.dao.sp.UserInfoSP;
import com.axp.axpseller.models.UserInfo;

/**
 * Created by xu on 2016/6/2.
 * 用户的各种操作
 */
public class UserUtils {

    /**
     * 登出
     * 退出当前账号
     *
     * @param context
     */
    public static void logout(Context context) {
        Bundle bundle = new Bundle();
//        bundle.putString(LoginOptionActivity.KEY_LAUNCHER_FRAGMENT, LoginFragment.class.getName());
        SellerInfoSp.clear(context);
        ContextParameter.setUserInfo(new UserInfo());
        AppUtils.toActivity(context, LoginOptionActivity.class, Intent.ACTION_VIEW, Intent.FLAG_ACTIVITY_CLEAR_TOP, bundle);
    }

    /**
     * 账号在其他设备中登录
     * 单点登录
     *
     * @param context
     */
    public static void otherLogin(Context context) {
        Bundle bundle = new Bundle();
//        bundle.putString(LoginOptionActivity.KEY_LAUNCHER_FRAGMENT, LoginFragment.class.getName());
        bundle.putBoolean(LoginOptionActivity.KEY_OTHER_LOGIN, true);
        AppUtils.toActivity(context, LoginOptionActivity.class, Intent.ACTION_VIEW, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK, bundle);
        UserInfoSP.clear(context);
        ContextParameter.setUserInfo(new UserInfo());
    }

    /**
     * 处理登录，没登录的情况下，跳转到登录
     *
     * @param context
     */
    public static boolean handlerLogin(FragmentActivity context) {

        if (!ContextParameter.isLogin()) {

            boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
            SimpleDialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    super.onPositiveActionClicked(fragment);

                    login(context);
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    super.onNegativeActionClicked(fragment);
                }
            };


            builder.message("进行下面的操作需要登录哦~").title("登录提示")
                    .positiveAction("去登录")
                    .negativeAction("再等等");

            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(context.getSupportFragmentManager(), null);


        } else {
            return true;
        }

        return false;

    }

    /**
     * 登录
     */
    public static void login(Context context) {
        Bundle bundle = new Bundle();
//        bundle.putString(LoginOptionActivity.KEY_LAUNCHER_FRAGMENT, LoginFragment.class.getName());
//        bundle.putString(LoginOptionActivity.KEY_LAUNCHER_FRAGMENT, LoginFragment.class.getName());
        AppUtils.toActivity(context, LoginOptionActivity.class, Intent.ACTION_VIEW, Intent.FLAG_ACTIVITY_NEW_TASK, bundle);
        UserInfoSP.clear(context);
    }

}
