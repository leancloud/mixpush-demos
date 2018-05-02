package cn.leancloud.demo.leancloudmixpush;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVMixPushManager;

/**
 * Created by fengjunwen on 2018/1/30.
 */

public class MyApp extends Application {
  private static final String LC_APP_ID = "ohqhxu3mgoj2eyj6ed02yliytmbes3mwhha8ylnc215h0bgk";
  private static final String LC_APP_KEY = "6j8fuggqkbc5m86b8mp4pf2no170i5m7vmax5iypmi72wldc";

  @Override
  public void onCreate() {
    super.onCreate();
    AVOSCloud.setDebugLogEnabled(true);

    AVOSCloud.initialize(this, LC_APP_ID, LC_APP_KEY);
    AVMixPushManager.registerHMSPush(this);
  }

}
