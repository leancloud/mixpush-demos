package cn.leancloud.demo.oppopush;

import android.app.Application;

import cn.leancloud.LCLogger;
import cn.leancloud.oppo.LCMixPushManager;
import cn.leancloud.LeanCloud;

public class MyApplication extends Application {
  private String LeanCloud_APPID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz"; // your leancloud app id
  private String LeanCloud_APPKEY = "dpwAo94n81jPsHVxaWwdxJVu"; // your leancloud app key.

  private String OPPO_APPKEY = "your oppo app id";
  private String OPPO_APPSECRET = "your oppo app secret";

  @Override
  public void onCreate() {
    super.onCreate();

    // 开启调试日志
    LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
    // 初始化参数依次为 this, AppId, AppKey
    LeanCloud.initialize(this, LeanCloud_APPID, LeanCloud_APPKEY, "https://gvv2k8pu.lc-cn-n1-shared.com");
    LCMixPushManager.registerOppoPush(this, OPPO_APPKEY, OPPO_APPSECRET, new MyPushAdapter());
  }
}
