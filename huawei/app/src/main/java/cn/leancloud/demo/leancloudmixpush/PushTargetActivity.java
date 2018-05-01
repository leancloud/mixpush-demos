package cn.leancloud.demo.leancloudmixpush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PushTargetActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_push_target);
    String content = savedInstanceState.getString("content");
    System.out.println("received content: " + content);
  }

  @Override
  protected void onResume() {
    super.onResume();
    System.out.println("onResume");
  }
}
