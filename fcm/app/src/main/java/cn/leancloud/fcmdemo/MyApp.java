package cn.leancloud.fcmdemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

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
//    AVOSCloud.setRegion(cn.leancloud.core.AVOSCloud.REGION.NorthAmerica);
    AVOSCloud.initialize(this, "glvame9g0qlj3a4o29j5xdzzrypxvvb30jt4vnvm66klph4r",
            "n79rw9ja3eo8n8au838t7pqur5mw88pnnep6ahlr99iq661a");
    PushService.setDefaultPushCallback(this, MainActivity.class);
    createNotificationChannel();

  }

  private void createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "AnyName";
      String description = "AnyDescription";
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel channel = new NotificationChannel("CHANNEL_ID_01", name, importance);
      channel.setDescription(description);
      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }
}
