package com.example.lotusautoconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class Send_message extends AppCompatActivity {
    EditText t1, t2;
    Button b1;
    String s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Intent intent = getIntent();
        final String str = intent.getStringExtra("message_key");
        final String str1=intent.getStringExtra("message_key1");
        t1=findViewById(R.id.phone);
        t2=findViewById(R.id.message);
        b1=findViewById(R.id.send);
        t1.setText(str);
        t2.setText(str1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(Send_message.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                {
                    sendSms();
                }
                else
                {
                    ActivityCompat.requestPermissions(Send_message.this,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
    }
    public void sendSms()
    {
        String phone2=t1.getText().toString();
        String message2=t2.getText().toString();
        if(!phone2.equals("")&&!message2.equals(""))
        {
            if(phone2.length()==10) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone2, null, message2, null, null);
                Toast.makeText(getApplicationContext(), "SMS Send Successfully", Toast.LENGTH_LONG).show();
                t1.setText("");
                t2.setText("");
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Should be Valid Number", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Enter the Value",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            sendSms();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }
}
