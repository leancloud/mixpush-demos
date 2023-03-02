package cn.leancloud.demo.xiaomi_push;

import android.app.Application;

import cn.leancloud.LCLogger;
import cn.leancloud.mi.LCMixPushManager;
import cn.leancloud.LeanCloud;

/**
 * Created by fengjunwen on 2018/1/22.
 */

public class MyApp extends Application {
  // 请替换成您自己的 appId 和 appKey
  private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
  private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";
  private static final String LC_SERVER = "https://gvv2k8pu.lc-cn-n1-shared.com";

  private static final String TDS_APPID = "HoiGvMeacbPWnv12MK";
  private static final String TDS_APPKEY = "FesqQUmlhjMWt6uNrKaV6QPtYgBYZMP9QFmTUk54";
  private static final String TDS_ServerURL = "https://hoigvmea.cloud.tds1.tapapis.cn";

  // 请替换成您自己的小米 appId 和 appKey
//  private static final String XIAOMI_APP = "2882303761520178131";
//  private static final String XIAOMI_KEY = "5122017819131";

  private static final String XIAOMI_APP = "2882303761517988199";
  private static final String XIAOMI_KEY = "5571798886199";
  @Override
  public void onCreate() {
    super.onCreate();

    LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
    LeanCloud.initialize(this,LC_APP_ID,LC_APP_KEY, LC_SERVER);
//    LeanCloud.initialize(this, TDS_APPID,TDS_APPKEY,TDS_ServerURL);

    LCMixPushManager.registerXiaomiPush(this, XIAOMI_APP, XIAOMI_KEY,"xsui_platform");
  }
}
