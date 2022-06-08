package com.example.myapplication.Notification;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class Notification extends AppCompatActivity {
    EditText tv1, tv2, tv3;
Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",tv1.getText().toString(),
                  //      tv2.getText().toString(),getApplicationContext(),Notification.this);
                //notificationsSender.SendNotifications();
            }
        });
    }
}