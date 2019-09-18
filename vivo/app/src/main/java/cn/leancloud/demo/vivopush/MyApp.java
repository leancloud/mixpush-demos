package cn.leancloud.demo.vivopush;

import android.app.Application;

import cn.leancloud.AVLogger;
import cn.leancloud.callback.AVCallback;
import cn.leancloud.AVException;
import cn.leancloud.AVMixPushManager;
import cn.leancloud.AVOSCloud;

public class MyApp extends Application {
  // 请替换成您自己的 appId 和 appKey
  private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
  private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";

  @Override
  public void onCreate() {
    super.onCreate();
    AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
    AVOSCloud.initialize(this,LC_APP_ID,LC_APP_KEY);
    AVMixPushManager.registerVIVOPush(this);
    AVMixPushManager.turnOnVIVOPush(new AVCallback<Boolean>() {
      @Override
      protected void internalDone0(Boolean aBoolean, AVException e) {
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
