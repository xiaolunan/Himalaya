package com.renchunlin.himalaya.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.renchunlin.himalaya.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.Arrays;

public class BaseApplication extends Application {

    private static Handler sHandler = null;
    private static Context sContext = null;

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 喜马拉雅SDK——key的配置
         */
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        if (DTransferConstants.isRelease) {
            String mAppSecret = "afe063d2e6df361bc9f1fb8bb8210d67";
            mXimalaya.setAppkey("af1d317b871e0e7e2ce45872caa34d9a");
            mXimalaya.setPackid("com.humaxdigital.automotive.ximalaya");
            mXimalaya.init(this, mAppSecret);
        } else {
            String mAppSecret = "0a09d7093bff3d4947a5c4da0125972e";
            mXimalaya.setAppkey("f4d8f65918d9878e1702d49a8cdf0183");
            mXimalaya.setPackid("com.ximalaya.qunfeng");
            mXimalaya.init(this, mAppSecret);
        }

        //初始化播放器
        XmPlayerManager.getInstance(this).init();

        /**
         * Log日志工具类的配置
         */
        LogUtil.init(this.getPackageName(), false);

        sHandler = new Handler();
        sContext = getBaseContext();

        //友盟查看设备是识别码
        String[] testDeviceInfo = getTestDeviceInfo(this);
        LogUtil.i("testDeviceInfo", "设备识别码" + Arrays.toString(testDeviceInfo));

        //初始化友盟SDK
        UMConfigure.init(this, "5f6d99d180455950e49749d4", "pixel", UMConfigure.DEVICE_TYPE_PHONE, "");
        //友盟 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        //友盟日志开关
        UMConfigure.setLogEnabled(true);
    }

    public static Context getAppContext() {
        return sContext;
    }

    public static Handler getHandler() {
        return sHandler;
    }

    /**
     * //友盟查看设备是识别码
     *
     * @param context
     * @return
     */
    public static String[] getTestDeviceInfo(Context context) {
        String[] deviceInfo = new String[2];
        try {
            if (context != null) {
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
        }
        return deviceInfo;
    }
}
