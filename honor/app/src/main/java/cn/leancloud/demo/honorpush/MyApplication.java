package cn.leancloud.demo.honorpush;

import android.app.Application;

import cn.leancloud.LCLogger;
import cn.leancloud.LeanCloud;
import cn.leancloud.honor.LCMixPushManager;

public class MyApplication extends Application {
    private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
    private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";

    @Override
    public void onCreate() {
        super.onCreate();

        LeanCloud.initialize(this, LC_APP_ID, LC_APP_KEY, "https://gvv2k8pu.lc-cn-n1-shared.com");
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
        LCMixPushManager.registerHonorPush(this);
    }
}
