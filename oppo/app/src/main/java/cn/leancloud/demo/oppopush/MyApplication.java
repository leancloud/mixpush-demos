package cn.leancloud.demo.oppopush;

import android.app.Application;

import cn.leancloud.LCLogger;
import cn.leancloud.oppo.LCMixPushManager;
import cn.leancloud.LeanCloud;

public class MyApplication extends Application {
  private static final String LeanCloud_APPID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz"; // your leancloud app id
  private static final String LeanCloud_APPKEY = "dpwAo94n81jPsHVxaWwdxJVu"; // your leancloud app key.
  private static final String LeanCloud_ServerURL = "https://gvv2k8pu.lc-cn-n1-shared.com"; // your leancloud app key.

  //tds
  private static final String TDS_APPID = "HoiGvMeacbPWnv12MK";
  private static final String TDS_APPKEY = "FesqQUmlhjMWt6uNrKaV6QPtYgBYZMP9QFmTUk54";
  private static final String TDS_ServerURL = "https://hoigvmea.cloud.tds1.tapapis.cn";

  public static final String OPPO_APPKEY = "87b1d6c3a426496aab9931d94ee890c2";
  public static final String OPPO_APPSECRET = "8a443674d9ae45b49cfacfb288b50105";//""your oppo app secret";

  @Override
  public void onCreate() {
    super.onCreate();

    // 开启调试日志
    LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
    // 初始化参数依次为 this, AppId, AppKey
        LeanCloud.initialize(this, TDS_APPID,TDS_APPKEY,TDS_ServerURL);
//    LeanCloud.initialize(this, LeanCloud_APPID, LeanCloud_APPKEY,LeanCloud_ServerURL);

    LCMixPushManager.registerOppoPush(this, OPPO_APPKEY, OPPO_APPSECRET, new MyPushAdapter());
  }
}
