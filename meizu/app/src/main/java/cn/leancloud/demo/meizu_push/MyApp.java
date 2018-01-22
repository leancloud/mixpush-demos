package cn.leancloud.demo.meizu_push;

import android.app.Application;

import com.avos.avoscloud.*;

/**
 * Created by fengjunwen on 2018/1/22.
 */

public class MyApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AVOSCloud.setDebugLogEnabled(true);
    AVOSCloud.initialize(this,"x7WmVG0x63V6u8MCYM8qxKo8-gzGzoHsz","PcDNOjiEpYc0DTz2E9kb5fvu");
  }
}
