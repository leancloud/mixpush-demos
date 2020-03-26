package cn.leancloud.demo.hmspush;

import android.app.Application;

import cn.leancloud.AVLogger;
import cn.leancloud.AVMixPushManager;
import cn.leancloud.AVOSCloud;
import cn.leancloud.demo.hmspush.R;
import cn.leancloud.push.PushService;

/**
 * Created by fengjunwen on 2018/1/30.
 */

public class MyApp extends Application {
  private static final String LC_APP_ID = "Gvv2k8PugDTmYOCfuK8tiWd8-gzGzoHsz";
  private static final String LC_APP_KEY = "dpwAo94n81jPsHVxaWwdxJVu";

  @Override
  public void onCreate() {
    super.onCreate();

    //开启调试日志
    AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);

    AVOSCloud.initialize(this, LC_APP_ID, LC_APP_KEY, "https://gvv2k8pu.lc-cn-n1-shared.com");
    AVMixPushManager.registerHMSPush(this);
    PushService.setDefaultPushCallback(this, MainActivity.class);
    PushService.setNotificationIcon(R.drawable.c_buoycircle_icon);
  }

}
