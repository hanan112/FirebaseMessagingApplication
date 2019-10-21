package com.hanan_ali.firebasemessagingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity
{
    // 1-notification channel
    //2-notification builder
    //3-notification manager
    //these for num 1
    public static final String CHANNEL_ID="simlified coding";
    private static final String CHANNEL_NAME="simlified_coding ";
    private static final String CHANNEL_DESC="simlified_coding notification";
   // private TextView textView;
    private EditText editTextemail,editTextpassword;
     private Button signupbutton;
     private ProgressBar progressbar;
     private FirebaseAuth mauth;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView=findViewById(R.id.text_view_token);
        editTextemail=findViewById(R.id.edit_text_email);
        editTextpassword=findViewById(R.id.edit_text_password);
        signupbutton=findViewById(R.id.SIgnup_btn);
        progressbar=findViewById(R.id.progressbar);
        progressbar.setVisibility(View.INVISIBLE);

        mauth= FirebaseAuth.getInstance();
        signupbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateUser();

            }
        });



        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task)
                    {
                        // هنجيت ال registeration token منها
                        if (task.isSuccessful())
                            {
                                //هنفذ التكست فيو هنا
                                String token=task.getResult().getToken();
                               // textView.setText("TOKEN : " +token);


                            }
                        else
                            {
                               // textView.setText(task.getException().getMessage());


                            }
                    }
                });*/


    }

    private void CreateUser()
    {
        final String email=editTextemail.getText().toString().trim();
        final String password=editTextpassword.getText().toString().trim();
        if (email.isEmpty())
        {
            editTextemail.setError("Email required");
            editTextemail.requestFocus();

        }
        if (password.isEmpty())
        {
            editTextpassword.setError("password required");
            editTextpassword.requestFocus();

        }
        if (password.length()<6)
        {
            editTextpassword.setError("password should be more than six char long ");
            editTextpassword.requestFocus();

        }
        progressbar.setVisibility(View.VISIBLE);
        mauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            StartProfileActivity();

                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                userLogin(email,password);
                                
                            }
                            else 
                            {
                                progressbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }


                    }
                });




    }

    private void userLogin(String email, String password)
    {
        mauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()

                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            StartProfileActivity();
                        }
                        else
                        {
                            progressbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (mauth.getCurrentUser()!=null)
        {
            StartProfileActivity();
        }


    }

    private void StartProfileActivity()
    {
        Intent intent=new Intent(this,ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

   /* private Void StartProfileActivity()
    {
        Intent intent=new Intent(this,ProfileActivity.class);

    }*/

    // method اللي بيها هعرض ال notification عن طريق ال button

}
