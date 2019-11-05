package cn.leancloud.demo.leancloudmixpush;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVMixPushManager;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    AVMixPushManager.connectHMS(this);
    AVMixPushManager.setHMSReceiveNotifyMsg(true);
    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("lcpushscheme://cn.leancloud.push/notify_detail?content=thisistest"));
    String intentUri = i.toUri(Intent.URI_INTENT_SCHEME);
    System.out.println("intentUri: " + intentUri);
  }



  private void showLog(String str) {
    System.out.println(str);
  }

}
