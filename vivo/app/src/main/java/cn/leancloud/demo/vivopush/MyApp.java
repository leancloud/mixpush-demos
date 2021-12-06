package cn.leancloud.demo.vivopush;

import android.app.Application;

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

  @Override
  public void onCreate() {
    super.onCreate();
    LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
    LeanCloud.initialize(this,LC_APP_ID,LC_APP_KEY, LC_SERVER_URL);
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
  }
}
