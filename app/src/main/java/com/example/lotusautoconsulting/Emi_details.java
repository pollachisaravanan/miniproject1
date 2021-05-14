package com.example.lotusautoconsulting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Emi_details extends AppCompatActivity {
    SQLiteDatabase db;
    Button button;
    boolean isAllFieldsChecked = false;
    EditText Name1,Email1,Phone1,Address1,Tot_amount1,Due_date1;
    TextView header1;
    String paid_amount="0";
    String no_of_emi="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_details);
        header1= (TextView) findViewById(R.id.header);
        Intent intent = getIntent();
        final String str = intent.getStringExtra("message_key");
        header1.setText(str);

        try{
            db=openOrCreateDatabase("LotusAutoConsultingDB",SQLiteDatabase.CREATE_IF_NECESSARY,null);
            db.execSQL("Create Table IF NOT EXISTS Emidetails(Reg_num text,Name text,Email text,Phone_num number,Due_date date,Total_amount number,Paid_amount number,Address text,No_of_emi_paid number,pen_amount number)");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        button=(Button)findViewById(R.id.addemi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name1=(EditText)findViewById(R.id.Name2);
                Email1=(EditText)findViewById(R.id.email2);
                String em1=Email1.getText().toString();
                int b=em1.indexOf('@');
                Phone1=(EditText)findViewById(R.id.phone2);
                Address1=(EditText)findViewById(R.id.address2);
                Tot_amount1=(EditText)findViewById(R.id.totamount2);
                Due_date1=(EditText)findViewById(R.id.duedate2);
                String d1=Due_date1.getText().toString();
                int c=d1.indexOf('/');
                isAllFieldsChecked= CheckAllFields();
                String sd=Phone1.getText().toString();
                boolean a;
                if(sd.length()==10)
                {
                    a=true;
                }
                else
                {
                    a=false;
                }
                if(c>0) {
                    if (isAllFieldsChecked && a && b > 0) {
                        ContentValues values = new ContentValues();
                        values.put("Reg_num", str);
                        values.put("Name", Name1.getText().toString());
                        values.put("Email", em1);
                        values.put("Phone_num", sd);
                        values.put("Due_date", Due_date1.getText().toString());
                        values.put("Total_amount", Tot_amount1.getText().toString());
                        values.put("Paid_amount", paid_amount);
                        values.put("Address", Address1.getText().toString());
                        values.put("No_of_emi_paid", no_of_emi);
                        values.put("pen_amount", Tot_amount1.getText().toString());
                        if (db.insert("Emidetails", null, values) != -1) {
                            Toast.makeText(Emi_details.this, "Emi added", 2000).show();
                            homepage();
                        } else {
                            Toast.makeText(Emi_details.this, "Insert Error", 2000).show();
                        }
                    } else {
                        Toast.makeText(Emi_details.this, "Valid Email Address and Phone Number", 2000).show();
                    }
                }
                else
                {
                    Toast.makeText(Emi_details.this, "Date should be DD/MM/YYYY FORMAT", 5000).show();
                }
                Name1.setText("");
                Email1.setText("");
                Phone1.setText("");
                Address1.setText("");
                Tot_amount1.setText("");
                Due_date1.setText("");

            }
        });

    }
    public boolean CheckAllFields() {
        if (Name1.length() == 0) {
            Name1.setError("This field is required");
            return false;
        }
        if(Email1.length()==0)
        {
            Email1.setError("This field is required");
            return false;
        }
        if(Phone1.length()==0)
        {
            Phone1.setError("This field is required");
            return false;
        }
        //if(Address1.length()==0) {
            //Address1.setError("This field is required");
            //return false;
        //}
        if(Tot_amount1.length()==0) {
            Tot_amount1.setError("This field is required");
            return false;
        }
        if(Due_date1.length()==0) {
            Due_date1.setError("This field is required");
            return false;
        }
        return true;

    }
    void homepage(){
        final Context context = this;
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
