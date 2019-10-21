package com.hanan_ali.firebasemessagingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class ProfileActivity extends AppCompatActivity
{
    private FirebaseAuth mauth;
    public static final String NOOD_USERS="Users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mauth=FirebaseAuth.getInstance();
        LoadUsers();
        FirebaseMessaging.getInstance().subscribeToTopic("updates");

       // NotificationHelper.dispiayNotification(getApplicationContext(),"title","body"); هي local notification

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task)
                    {
                        // هنجيت ال registeration token منها
                        if (task.isSuccessful())
                        {
                            //هنفذ التكست فيو هنا
                            // هيعمل store for the token in tne firebase
                            String token=task.getResult().getToken();
                            // textView.setText("TOKEN : " +token);
                            saveToken(token);


                        }
                        else
                        {
                            // textView.setText(task.getException().getMessage());



                        }
                    }
                });


    }

    private void LoadUsers()
    {
        DatabaseReference dbUsers=FirebaseDatabase.getInstance().getReference("Users");
        dbUsers.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dsUser:dataSnapshot.getChildren())
                    {
                        
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (mauth.getCurrentUser()==null)
        {
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        }


    }

    private void saveToken(String token)
    {
        //فيها هسيف useremail,password
        String email=mauth.getCurrentUser().getEmail();

        User user=new User(email,token);
        DatabaseReference dbUsers= FirebaseDatabase.getInstance().getReference(NOOD_USERS);
        dbUsers.child(mauth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()

        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Token saved ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}

