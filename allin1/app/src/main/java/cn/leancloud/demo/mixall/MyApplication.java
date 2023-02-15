package cn.leancloud.demo.mixall;

import android.app.Application;

import cn.leancloud.LCLogger;
import cn.leancloud.LCMixPushManager;
import cn.leancloud.LeanCloud;

public class MyApplication extends Application {
    private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
    private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";
    private static final String MI_APPID = "xiaomi-app-id";
    private static final String MI_APPKEY = "xiaomi-app-secret";

    @Override
    public void onCreate() {
        super.onCreate();

        LeanCloud.initialize(this, LC_APP_ID, LC_APP_KEY, "https://gvv2k8pu.lc-cn-n1-shared.com");
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG);

        LCMixPushManager.registerHMSPush(this);
        LCMixPushManager.registerHonorPush(this);
        LCMixPushManager.registerVIVOPush(this);
        LCMixPushManager.registerXiaomiPush(this, MI_APPID, MI_APPKEY);
    }
}
