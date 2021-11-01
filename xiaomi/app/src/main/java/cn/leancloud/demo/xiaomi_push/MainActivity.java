package cn.leancloud.demo.xiaomi_push;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import cn.leancloud.LCInstallation;


public class MainActivity extends AppCompatActivity {
  private TextView textView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textView= (TextView) findViewById(R.id.textView1);
    textView.setText("installationId: " + "installationId");

    String installationId = LCInstallation.getCurrentInstallation().getInstallationId();
    if (installationId != null){
      System.out.println("installationId:installationId");
      textView.setText("installationId: "+installationId);
    }else{
      textView.setText("installationId = null");
    }
  }
}
