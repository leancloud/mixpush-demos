package cn.leancloud.demo.vivopush;

import android.content.Context;

import com.avos.avoscloud.AVVIVOPushMessageReceiver;

import java.util.logging.Logger;

public class MyPushMessageReceiver extends AVVIVOPushMessageReceiver {
  private static final Logger logger = Logger.getLogger(MyPushMessageReceiver.class.getSimpleName());

  public void onNotificationMessageClicked(Context var1, com.vivo.push.model.UPSNotificationMessage var2) {
    ;
  }
}
