package cn.leancloud.demo.vivopush;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import cn.leancloud.LCVIVOPushMessageReceiver;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.model.UnvarnishedMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyPushMessageReceiver extends LCVIVOPushMessageReceiver {
  private static final Logger logger = Logger.getLogger(MyPushMessageReceiver.class.getSimpleName());

  @Override
  public void onTransmissionMessage(Context context, UnvarnishedMessage unvarnishedMessage) {
    super.onTransmissionMessage(context, unvarnishedMessage);
    Toast.makeText(context, " 收到透传通知： " + unvarnishedMessage.getMessage(),
            Toast.LENGTH_LONG).show();
    Log.d("OpenClientPushMessageReceiverImpl", " onTransmissionMessage= "
            + unvarnishedMessage.getMessage());
  }

  public void onNotificationMessageClicked(Context context, UPSNotificationMessage unvarnishedMessage) {
    super.onNotificationMessageClicked(context, unvarnishedMessage);
    Toast.makeText(context, " 收到通知点击回调： " + unvarnishedMessage.toString(),
            Toast.LENGTH_LONG).show();
    Log.d("OpenClientPushMessageReceiverImpl", " onTransmissionMessage= "
            + unvarnishedMessage.toString());
  }
}
