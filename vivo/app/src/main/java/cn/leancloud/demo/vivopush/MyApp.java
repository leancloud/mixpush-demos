package cn.leancloud.demo.vivopush;

import android.app.Application;

import cn.leancloud.LCInstallation;
import cn.leancloud.LCLogger;
import cn.leancloud.callback.LCCallback;
import cn.leancloud.LCException;
import cn.leancloud.vivo.LCMixPushManager;
import cn.leancloud.LeanCloud;


public class MyApp extends Application {
  // 请替换成您自己的 appId 和 appKey
  private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
  private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";
  private static final String LC_SERVER_URL = "https://gvv2k8pu.lc-cn-n1-shared.com";

  private static final String TDS_APPID = "HoiGvMeacbPWnv12MK";
  private static final String TDS_APPKEY = "FesqQUmlhjMWt6uNrKaV6QPtYgBYZMP9QFmTUk54";
  private static final String TDS_ServerURL = "https://hoigvmea.cloud.tds1.tapapis.cn";

  @Override
  public void onCreate() {
    super.onCreate();
    LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
    LeanCloud.initialize(this,LC_APP_ID,LC_APP_KEY, LC_SERVER_URL);
//    LeanCloud.initialize(this, TDS_APPID,TDS_APPKEY,TDS_ServerURL);

    //使用默认 profile
    LCMixPushManager.registerVIVOPush(this);
    LCMixPushManager.turnOnVIVOPush(new LCCallback<Boolean>() {
      @Override
      protected void internalDone0(Boolean aBoolean, LCException e) {
        if (null != e) {
          System.out.println("failed to turn on VIVO push. cause:");
          e.printStackTrace();
        } else {
          System.out.println("succeed to turn on VIVO push.");
        }
      }
    });

    String installationId = LCInstallation.getCurrentInstallation().getInstallationId();
    System.out.println("installationId：" + installationId );
  }
}
