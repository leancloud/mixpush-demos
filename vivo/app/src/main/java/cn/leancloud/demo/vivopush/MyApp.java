package cn.leancloud.demo.vivopush;

import android.app.Application;

import com.avos.avoscloud.AVMixPushManager;
import com.avos.avoscloud.AVOSCloud;

public class MyApp extends Application {
  // 请替换成您自己的 appId 和 appKey
  private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
  private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";

  @Override
  public void onCreate() {
    super.onCreate();
    AVOSCloud.setDebugLogEnabled(true);
    AVOSCloud.initialize(this,LC_APP_ID,LC_APP_KEY);
    AVMixPushManager.registerVIVOPush(this);
  }
}
