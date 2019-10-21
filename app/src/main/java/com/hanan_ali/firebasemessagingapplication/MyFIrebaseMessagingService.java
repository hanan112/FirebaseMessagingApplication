package com.hanan_ali.firebasemessagingapplication;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFIrebaseMessagingService extends FirebaseMessagingService
{
    //Firebasemessaging... بيستلم الرسائل اللي جايه
    // اول ما يستلم رساله جديده هينادي ع ال method  on messagerecieved
    //عن طريق ال remotemessage هيوصل لمحتوى الرساله


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification()!=null)
        {
            //عنطريق ال getnotification بيوصل لل title, content  الاشعار
            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();
            //كدا عاوزين نعرض ال message عن طريق ال الاشعار
            //هجيب ال displaynotification من ال main activity واعمل كلاس اسمه NotificationHelper واحطها فيه
            NotificationHelper.dispiayNotification(getApplicationContext(),title,body);
            // هعرف ال service في ال manifest

        }
    }
}
