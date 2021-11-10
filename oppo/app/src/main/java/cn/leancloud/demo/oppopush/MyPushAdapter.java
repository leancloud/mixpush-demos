package cn.leancloud.demo.oppopush;

import cn.leancloud.LCOPPOPushAdapter;

public class MyPushAdapter extends LCOPPOPushAdapter {
  @Override
  public void onGetPushStatus(int var1, int var2) {
    System.out.println("get push status: responseCode=" + var1 + ", status=" + var2);
  }

  @Override
  public void onGetNotificationStatus(int var1, int var2) {
    System.out.println("get notification status: responseCode=" + var1 + ", status=" + var2);
  }

  @Override
  public void onSetPushTime(int var1, String var2) {
    System.out.println("set push time: responseCode=" + var1 + ", result=" + var2);
  }
}
