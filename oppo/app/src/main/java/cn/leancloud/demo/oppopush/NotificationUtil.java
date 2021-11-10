package cn.leancloud.demo.oppopush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.service.notification.StatusBarNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationUtil {
    public static Notification createNotification(Context context, int notifyId, String title, String content) {
        Notification notification = null;
        Notification.Builder builder = new Notification.Builder(context);
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setChannelId("Heytap PUSH1");
        }
        builder.setContentTitle(title);
        builder.setContentText("notifyId : " + notifyId + " " + content);

        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);

        Bitmap bigPicture = null;

        int def = Notification.DEFAULT_ALL;
        def |= Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;
        builder.setDefaults(def);
//        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        notification = builder.build();

        return notification;
    }

    public static void postNotify(Context context, final int id, Notification notification) {
        if (null == notification) {
            return;
        }
        final NotificationManager notificationManager = getNotificationManager(context);
        if (null != notificationManager) {
            notificationManager.notify(id, notification);
//            LogUtil.d("postNotify--notifyId:" + id + ",notification:" + notification);
        }
    }

    public static NotificationManager getNotificationManager(Context context) {
        NotificationManager nm = null;
        try {
            if (null != context) {
                nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
        } catch (Throwable ignored) {
        }
        return nm;
    }


    public static void showNotification(Context context, String title, String content,
                                        final int id, boolean redBadge) {
//        LogUtil.d("showNotification", "notifyId : " + id + "    redBadge : " + redBadge);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(title+"id : "+id)
                .setContentText(content)
                .setAutoCancel(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            builder.setGroup("group");
//        }
        NotificationManager notificationManager = NotificationUtil.getNotificationManager(context);
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("default", "default", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(redBadge ? "default" : "no_red_badge");
            }
            notificationManager.notify(id, builder.build());
        }

//        Notification.Builder builder1 = new Notification.Builder(context)
//                .setSmallIcon(android.R.drawable.sym_def_app_icon)
//                .setAutoCancel(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            builder1.setGroup("group");
//            builder1.setGroupSummary(true);
//        }
//        NotificationManager notificationManager1 = NotificationUtil.getNotificationManager(context);
//        if (notificationManager1 != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel("default", "default", NotificationManager.IMPORTANCE_DEFAULT);
//                notificationManager1.createNotificationChannel(channel);
//                builder1.setChannelId(redBadge ? "default" : "no_red_badge");
//            }
//            notificationManager1.notify(1, builder1.build());
//        }
    }

    public static void clearRedBadge(Context context) {
        NotificationManager nm = getNotificationManager(context);
        if (nm == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        List<String> channelNameRedBadge = getRedBadgeChannel(context);
        if (channelNameRedBadge == null || channelNameRedBadge.size() <= 0) {
            return;
        }

        StatusBarNotification[] list = nm.getActiveNotifications();
        if (list == null) {
            return;
        }
        for (StatusBarNotification notification : list) {
            if (channelNameRedBadge.contains(notification.getNotification().getChannelId())) {
                nm.cancel(notification.getId());
            }
        }
    }

    private static List<String> getRedBadgeChannel(Context context) {
        List<String> channelNameList = new ArrayList<>();
        NotificationManager nm = getNotificationManager(context);
        if (nm == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return channelNameList;
        }

        List<NotificationChannel> list = nm.getNotificationChannels();
        if (list == null) {
            return channelNameList;
        }

        for (NotificationChannel notificationChannel : list) {
            if (notificationChannel.canShowBadge()) {
                channelNameList.add(notificationChannel.getId());
            }
        }

        return channelNameList;
    }

    public static boolean hasRedMessage(Context context) {
        NotificationManager nm = getNotificationManager(context);
        if (nm == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return false;
        }

        List<String> channelNameRedBadge = getRedBadgeChannel(context);
        if (channelNameRedBadge == null || channelNameRedBadge.size() <= 0) {
            return false;
        }

        StatusBarNotification[] list = nm.getActiveNotifications();
        if (list == null) {
            return false;
        }
        for (StatusBarNotification notification : list) {
            if (channelNameRedBadge.contains(notification.getNotification().getChannelId())) {
                return true;
            }
        }

        return false;
    }
}