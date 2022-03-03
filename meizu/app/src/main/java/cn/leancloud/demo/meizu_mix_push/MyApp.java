package cn.leancloud.demo.meizu_mix_push;


import android.app.Application;
import cn.leancloud.LCLogger;
import cn.leancloud.flyme.LCMixPushManager;
import cn.leancloud.LeanCloud;

public class MyApp extends Application {
    private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
    private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";
    private static final String LC_APP_URL = "https://gvv2k8pu.lc-cn-n1-shared.com";


    private static final String TDS_APPID = "HoiGvMeacbPWnv12MK";
    private static final String TDS_APPKEY = "FesqQUmlhjMWt6uNrKaV6QPtYgBYZMP9QFmTUk54";
    private static final String TDS_ServerURL = "https://hoigvmea.cloud.tds1.tapapis.cn";

    private static final String MEIZU_APP = "146951";
    private static final String MEIZU_KEY = "304015a4ef864d05a56e863b7582eeb8";

    @Override
    public void onCreate() {
        super.onCreate();
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
        LeanCloud.initialize(this,LC_APP_ID,LC_APP_KEY, LC_APP_URL);
//        LeanCloud.initialize(this, TDS_APPID,TDS_APPKEY,TDS_ServerURL);

        boolean registResult = LCMixPushManager.registerFlymePush(this, MEIZU_APP, MEIZU_KEY);
        System.out.println("register result;" + registResult);
    }
}
