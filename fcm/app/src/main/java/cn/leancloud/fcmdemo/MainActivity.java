package cn.leancloud.fcmdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

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
        String token = FirebaseInstanceId.getInstance().getToken();
        String msg = getString(R.string.msg_token_fmt, token);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
      }
    });
  }
}
