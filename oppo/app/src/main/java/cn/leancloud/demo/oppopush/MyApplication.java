package cn.leancloud.demo.oppopush;

import android.app.Application;

import com.avos.avoscloud.AVMixPushManager;
import com.avos.avoscloud.AVOSCloud;

public class MyApplication extends Application {
  private String LeanCloud_APPID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz"; // your leancloud app id
  private String LeanCloud_APPKEY = "dpwAo94n81jPsHVxaWwdxJVu"; // your leancloud app key.

  private String OPPO_APPKEY = "your oppo app id";
  private String OPPO_APPSECRET = "your oppo app secret";

  @Override
  public void onCreate() {
    super.onCreate();

    // 开启调试日志
    AVOSCloud.setDebugLogEnabled(true);
    // 初始化参数依次为 this, AppId, AppKey
    AVOSCloud.initialize(this, LeanCloud_APPID, LeanCloud_APPKEY);
    AVMixPushManager.registerOppoPush(this, OPPO_APPKEY, OPPO_APPSECRET, new MyPushAdapter());
  }
}
