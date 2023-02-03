package android.coursetrackerapp.coursetracker.UI;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.coursetrackerapp.coursetracker.R;

import androidx.core.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {
    public static int notificationID = 1;
    public static String channelID = "channel1";
    public static String notificationTitle = "title";
    public static String notificationMessage = "Message";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Notification notification = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(notificationTitle))
                .setContentText(intent.getStringExtra(notificationMessage))
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification);

    }
    private void createNotificationChannel(Context context, String channelID) {

    }
}