package cn.leancloud.demo.hmspush;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVException;
import cn.leancloud.AVInstallation;
import cn.leancloud.AVMixPushManager;
import cn.leancloud.callback.AVCallback;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    AVMixPushManager.connectHMS(MainActivity.this);
    AVMixPushManager.turnOnHMSPush(MainActivity.this, new AVCallback<Void>() {
      @Override
      protected void internalDone0(Void aVoid, AVException avException) {
        if (null != avException) {
          System.out.println("failed to turn on HMS Push, cause: " + avException.getMessage());
        } else {
          System.out.println("succeed to turn on HMS Push.");
        }
      }
    });
    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("lcpushscheme://cn.leancloud.push/notify_detail?content=thisistest"));
    String intentUri = i.toUri(Intent.URI_INTENT_SCHEME);
    System.out.println("intentUri: " + intentUri);

    EditText contentText = findViewById(R.id.editText);
    contentText.setText("Current Device Id:\r\n" + AVInstallation.getCurrentInstallation().getInstallationId());
  }



  private void showLog(String str) {
    System.out.println(str);
  }

}
