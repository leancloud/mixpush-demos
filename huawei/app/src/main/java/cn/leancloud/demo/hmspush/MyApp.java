package cn.leancloud.demo.hmspush;

import android.app.Application;

import cn.leancloud.LCLogger;
import cn.leancloud.hms.LCMixPushManager;
import cn.leancloud.LeanCloud;
import cn.leancloud.demo.hmspush.R;
import cn.leancloud.push.PushService;

/**
 * Created by fengjunwen on 2018/1/30.
 */

public class MyApp extends Application {
  private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
  private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";
  private static final String LC_APP_URL = "https://gvv2k8pu.lc-cn-n1-shared.com";

  private static final String TDS_APPID = "HoiGvMeacbPWnv12MK";
  private static final String TDS_APPKEY = "FesqQUmlhjMWt6uNrKaV6QPtYgBYZMP9QFmTUk54";
  private static final String TDS_ServerURL = "https://hoigvmea.cloud.tds1.tapapis.cn";
  @Override
  public void onCreate() {
    super.onCreate();

    //开启调试日志
    LeanCloud.setLogLevel(LCLogger.Level.DEBUG);

    LeanCloud.initialize(this, LC_APP_ID, LC_APP_KEY, LC_APP_URL);
//    LeanCloud.initialize(this, TDS_APPID,TDS_APPKEY,TDS_ServerURL);

    LCMixPushManager.registerHMSPush(this);
    PushService.setDefaultPushCallback(this, MainActivity.class);
    PushService.setNotificationIcon(R.drawable.c_buoycircle_icon);
  }
}
