package cn.leancloud.demo.leancloudmixpush;

import android.content.Context;

import cn.leancloud.AVHMSPushMessageReceiver;

public class MyMessageReceiver extends AVHMSPushMessageReceiver {
  @Override
  public void onPushMsg(Context var1, byte[] var2, String var3) {
    try {
      String message = new String(var2, "UTF-8");
      System.out.println("RCV: received passthrough message: " + message);
    } catch (Exception ex) {
      System.out.println("RCV: failed to parse message string. cause: " + ex.getMessage());
    }
    super.onPushMsg(var1, var2, var3);
  }
}
