package com.ict.delivirko.FirebaseUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.ict.delivirko.Activities.HomeRestaurantActivity;
import com.ict.delivirko.Activities.PilotTravelActivity;
import com.ict.delivirko.Activities.TravelMapsActivity;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.ObjectClickListener;
import com.ict.delivirko.Module.OrderNotification;
import com.ict.delivirko.R;

import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private String date = "";
    OrderNotification orderNotification;
    static ObjectClickListener objectClickListener;


    public static void setOnItemClickListener(ObjectClickListener clickListener) {
        objectClickListener = clickListener;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("MyFirebaseIIDService2", remoteMessage.getNotification().getTitle());
        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Gson gson = new Gson();
            orderNotification = gson.fromJson(object.toString(), OrderNotification.class);
            ApplicationController.getInstance().SetOrderNotification(orderNotification);
            Log.e("JSON_OBJECT", object.toString());
            objectClickListener.onItemClickListener(orderNotification);


        } catch (Exception e) {

        }

        String title = "";
        if (remoteMessage.getNotification().getTitle() != null) {
            title = remoteMessage.getNotification().getTitle();
        }

        String message = "";
        if (remoteMessage.getNotification().getBody() != null) {
            message = remoteMessage.getNotification().getBody();
        }


        sendNotification(title, message, "1");

    }

    private void sendNotification(String msg, String body, String type) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        String StatusId = ApplicationController.getInstance().getOrderNotification().getStatus_id();
        Log.e("JSON_OBJECT3333", ApplicationController.getInstance().getOrderNotification().toString());
        Intent intent;
        if (StatusId.equals("4") || StatusId.equals("3") || StatusId.equals("2") || StatusId.equals("6")) {
            intent = new Intent(getApplicationContext(), TravelMapsActivity.class);

        } else if (StatusId.equals("7")) {
            Log.e("statusOrder_7", "hi");
            intent = new Intent(getApplicationContext(), PilotTravelActivity.class);
            intent.putExtra("isStart", true);

        } else if (StatusId.equals("5")) {
            intent = new Intent(getApplicationContext(), HomeRestaurantActivity.class);
            ApplicationController.getInstance().deleteTempOrder();
            ApplicationController.getInstance().deleteOrderNotification();
        } else {
            intent = new Intent(getApplicationContext(), HomeRestaurantActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();
        intent.putExtra("orderNo", Integer.valueOf(orderNotification.getOrder_id()));
        intent.putExtra("Status_id", Integer.valueOf(orderNotification.getStatus_id()));
        bundle.putSerializable("orderNotification", orderNotification);
        intent.putExtras(bundle);

        PendingIntent contentIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                intent, 0);

        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(msg)
                        .setStyle(new Notification.BigTextStyle()
                                .bigText(body))
//                        .setVibrate(new long[]{100, 500})
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setAutoCancel(true)
                        .setContentText(body);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


    }

}
