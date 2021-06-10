package cn.leancloud.demo.vivopush;

import android.content.Context;

import cn.leancloud.LCVIVOPushMessageReceiver;
import com.vivo.push.model.UPSNotificationMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyPushMessageReceiver extends LCVIVOPushMessageReceiver {
  private static final Logger logger = Logger.getLogger(MyPushMessageReceiver.class.getSimpleName());

  public void onNotificationMessageClicked(Context var1, UPSNotificationMessage var2) {
    logger.log(Level.FINER, "received MessageClick Event. " + var2.toString());
  }
}
