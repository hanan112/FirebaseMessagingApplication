package com.hanan_ali.firebasemessagingapplication;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper
{
    public static void dispiayNotification(Context context,String title,String body)
    {//عشان يقدر يشوف this همررله context
        //for make num 2 بكدا عملنا ال builder
        // في النوتفكيشن هنعرض ال messages في تكملتها .
        // عشان اهندل ال click ه create intent يدخلني ع activity محدد عند الضغط ع ال notification
        Intent intent=new Intent(context,ProfileActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,
                intent,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context,MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);



        // create notification manager for num 3
        NotificationManagerCompat mnotificationmgr=NotificationManagerCompat.from(context);
        mnotificationmgr.notify(1,mBuilder.build());



    }


}
