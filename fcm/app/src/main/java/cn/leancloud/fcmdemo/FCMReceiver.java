package cn.leancloud.fcmdemo;


import android.app.Notification;
import android.app.NotificationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMReceiver extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    // TODO(developer): Handle FCM messages here.
    Log.d("TAG", "From: " + remoteMessage.getFrom());
    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      System.out.println("TAG:" + remoteMessage.getData().toString());
      Log.d("TAG", "Message data payload: " + remoteMessage.getData());
      // Check if message contains a notification payload.
      if (remoteMessage.getNotification() != null) {
        Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getTitle());
        Log.d("TAG", "Message Notification Title: " + remoteMessage.getNotification().getBody());
        Log.d("TAG", "Message Notification ChannelId: " + remoteMessage.getNotification().getChannelId());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, remoteMessage.getNotification().getChannelId())
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify(123, builder.build());

      }
    }
  }
}