package cn.leancloud.fcmdemo;

import android.app.Application;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;
import cn.leancloud.push.PushService;

/**
 * Created by fengjunwen on 2018/5/28.
 */

public class MyApp extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
    AVOSCloud.setRegion(cn.leancloud.core.AVOSCloud.REGION.NorthAmerica);
    AVOSCloud.initialize(this, "glvame9g0qlj3a4o29j5xdzzrypxvvb30jt4vnvm66klph4r",
        "n79rw9ja3eo8n8au838t7pqur5mw88pnnep6ahlr99iq661a");
    PushService.setDefaultPushCallback(this, MainActivity.class);
  }
}
