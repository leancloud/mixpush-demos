package cn.leancloud.fcmdemo;

import android.app.NotificationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import cn.leancloud.LCFirebaseMessagingService;

//自定义 Receiver
public class FCMReceiver extends LCFirebaseMessagingService {
  @Override
  public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//    super.onMessageReceived(remoteMessage);
    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      Log.d("TAG", "Message data payload: " + remoteMessage.getData());

      // Check if message contains a notification payload.
      if (remoteMessage.getNotification() != null) {
        String title = remoteMessage.getNotification().getTitle();
        String alert = remoteMessage.getNotification().getBody();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID_01")
//                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(title)
                .setContentText(alert)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify(123, builder.build());
      }
    }
  }
}