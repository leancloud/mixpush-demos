package cn.leancloud.fcmdemo;

import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
  private Handler myHandler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
      return false;
    }
  });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    Button displayBtn = this.findViewById(R.id.button);
    displayBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Get token
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
              @Override
              public void onComplete(Task<String> task) {
                if (!task.isSuccessful()) {
                  Toast.makeText(MainActivity.this,
                      "Fetching FCM registration token failed: " + task.getException(),
                      Toast.LENGTH_SHORT).show();
                  return;
                }

                // Get new FCM registration token
                String token = task.getResult();

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
              }
            });
      }
    });
  }
}
