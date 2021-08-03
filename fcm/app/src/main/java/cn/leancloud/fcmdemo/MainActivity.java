package cn.leancloud.fcmdemo;

import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import cn.leancloud.LCException;
import cn.leancloud.LCInstallation;
import cn.leancloud.callback.SaveCallback;
import cn.leancloud.convertor.ObserverBuilder;
import cn.leancloud.push.PushService;
import cn.leancloud.utils.StringUtil;

public class MainActivity extends AppCompatActivity {
  private Handler myHandler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
      return false;
    }
  });
  private final String VENDOR = "fcm";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    Button displayBtn = this.findViewById(R.id.button);
    displayBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();
        String msg = getString(R.string.msg_token_fmt, token);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        if (!StringUtil.isEmpty(token)) {
          sendRegistrationToServer(token);
        }
      }
    });
  }

  private void sendRegistrationToServer(String refreshedToken) {
    if (StringUtil.isEmpty(refreshedToken)) {
      return;
    }
    LCInstallation installation = LCInstallation.getCurrentInstallation();
    if (!VENDOR.equals(installation.getString(LCInstallation.VENDOR))) {
      installation.put(LCInstallation.VENDOR, VENDOR);
    }
    if (!refreshedToken.equals(installation.getString(LCInstallation.REGISTRATION_ID))) {
      installation.put(LCInstallation.REGISTRATION_ID, refreshedToken);
    }
    installation.saveInBackground().subscribe(
        ObserverBuilder.buildSingleObserver(new SaveCallback() {
          @Override
          public void done(LCException e) {
            if (null != e) {
              System.out.println("failed to update installation." + e);
            } else {
              System.out.println("succeed to update installation.");
            }
          }
        }));

    System.out.println("FCM registration success! registrationId=" + refreshedToken);
  }
}
