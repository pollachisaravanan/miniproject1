package com.example.lotusautoconsulting;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class Emi_view extends AppCompatActivity {
    SQLiteDatabase db;
    String name3,email3,phone3,due_date3,tot_amount3,paid_amount3,address3,no_of_emi3,pen_amount3,reg3,message="From Lotus Auto Consulting,\n\n" +
            "        Your Next Due date is   ",message1,message2="    Pending Amount is  ",message3;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_view);
        t1= (TextView) findViewById(R.id.textView5);
        Intent intent = getIntent();
        final String str = intent.getStringExtra("message_key");
        t1.setText(str);
        final Context context=this;
        try{
            db=openOrCreateDatabase("LotusAutoConsultingDB",SQLiteDatabase.CREATE_IF_NECESSARY,null);
            db.execSQL("Create Table IF NOT EXISTS Emidetails(Reg_num text,Name text,Email text,Phone_num number,Due_date date,Total_amount number,Paid_amount number,Address text,No_of_emi_paid number,pen_amount number)");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        t2= (TextView) findViewById(R.id.textView24);
        t3= (TextView) findViewById(R.id.textView25);
        t4= (TextView) findViewById(R.id.textView26);
        t5= (TextView) findViewById(R.id.textView27);
        t6= (TextView) findViewById(R.id.textView28);
        t7= (TextView) findViewById(R.id.textView29);
        t8= (TextView) findViewById(R.id.textView30);
        t9= (TextView) findViewById(R.id.textView31);
        t10= (TextView) findViewById(R.id.textView32);
        Cursor c1=db.rawQuery("SELECT * FROM Emidetails Where Reg_num='"+str+"'",null);
        c1.moveToFirst();
        while(!c1.isAfterLast())
        {
            reg3 = c1.getString(0);
            name3= c1.getString(1);
            email3= c1.getString(2);
            phone3= c1.getString(3);
            due_date3= c1.getString(4);
            tot_amount3= c1.getString(5);
            paid_amount3= c1.getString(6);
            address3= c1.getString(7);
            no_of_emi3=c1.getString(8);
            pen_amount3=c1.getString(9);
            c1.moveToNext();
        }
        t2.setText(name3);
        t3.setText(email3);
        t4.setText(phone3);
        t5.setText(address3);
        t6.setText(tot_amount3);
        t7.setText(paid_amount3);
        t8.setText(pen_amount3);
        t9.setText(no_of_emi3);
        t10.setText(due_date3);
        message1=message.concat(due_date3);
        message1=message1.concat("\n");
        message1=message1.concat("      ");
        message=message1.concat(message2);
        message3=message.concat(pen_amount3);
        button = (Button) findViewById(R.id.button8);
        if(pen_amount3.equals("0"))
        {
            button.setEnabled(false);
        }
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, Send_message.class);
                intent.putExtra("message_key",phone3);
                intent.putExtra("message_key1",message3);
                startActivity(intent);
            }
        });
    }
}
