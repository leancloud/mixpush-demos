package cn.leancloud.demo.meizu_push;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.*;
import com.alibaba.fastjson.JSON;

import com.meizu.cloud.pushsdk.util.MzSystemUtils;

/**
 * Created by fengjunwen on 2018/1/22.
 */

public class MyApp extends Application {
  private static final String LC_APP_ID = "";
  private static final String LC_APP_KEY = "";

  private static final String MEIZU_APP = "";
  private static final String MEIZU_KEY = "";

  @Override
  public void onCreate() {
    super.onCreate();
    AVOSCloud.initialize(this,LC_APP_ID,LC_APP_KEY);
    AVOSCloud.setDebugLogEnabled(true);
    AVMixpushManager.registerFlymePush(this, MEIZU_APP, MEIZU_KEY);

    PushService.setDefaultPushCallback(this, MainActivity.class);
  }
}
