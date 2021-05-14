package com.example.lotusautoconsulting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button btnCheck;
    EditText search;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCheck=(Button)findViewById(R.id.button);
        try{
            db=openOrCreateDatabase("LotusAutoConsultingDB",SQLiteDatabase.CREATE_IF_NECESSARY,null);
            db.execSQL("Create Table IF NOT EXISTS Vehicledetails(Reg_num text,Brand text,Variant text,Model text,Purchase_date date,Purchase_amount number,Insurance_expiry_date date,Status text,emi text)");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        search=(EditText) findViewById(R.id.searchtext);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = search.getText().toString();
                s = s.replace(" ", "");
                char[] ch = new char[s.length()];
                for(int i=0;i<s.length();i++)
                {
                    ch[i] = s.charAt(i);
                    if(ch[i]>=97&&ch[i]<=122)
                    {
                        ch[i]=Character.toUpperCase(ch[i]);
                    }
                }
                s=String.valueOf(ch);
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    Cursor c1 = db.rawQuery("SELECT * FROM Vehicledetails Where Reg_num='" + s + "'", null);
                    Cursor c2 = db.rawQuery("SELECT * FROM Vehicledetails Where Brand='" + s + "'", null);
                    Cursor c3 = db.rawQuery("SELECT * FROM Vehicledetails Where Variant='" + s + "'", null);
                    Cursor c4 = db.rawQuery("SELECT * FROM Vehicledetails Where Model='" + s + "'", null);
                    Cursor c5 = db.rawQuery("SELECT Status FROM Vehicledetails Where Reg_num='" + s + "'", null);
                    System.out.println(c1.getCount());
                    if (c1.getCount() > 0 || c2.getCount() > 0 || c3.getCount() > 0 || c4.getCount() > 0) {
                        c5.moveToFirst();
                        String stat = c5.getString(0);

                        System.out.println(stat);
                        if (stat.equals("Available")) {
                            addAnotherListener();
                        } else {
                            addStatListener();
                        }
                    } else
                        addListener();
                    c1.close();
                    c2.close();
                    c3.close();
                    c4.close();
                    c5.close();
                }
                else
                {
                    Toast toast=Toast.makeText(getApplicationContext(),"Should Not be Empty",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        });

    }
    public boolean CheckAllFields()
    {
        if(search.length()==0)
        {
            search.setError("This is a required field");
            return false;
        }
        return true;
    }

    void addListener() {
        final Context context = this;
                Intent intent = new Intent(context, AddVehicle.class);
                EditText editText = (EditText) findViewById(R.id.searchtext);
                String message = editText.getText().toString();
                message=message.replace(" ","");
                char[] ch = new char[message.length()];
                for(int i=0;i<message.length();i++)
                {
                   ch[i] = message.charAt(i);
                   if(ch[i]>=97&&ch[i]<=122)
                   {
                       ch[i]=Character.toUpperCase(ch[i]);
                   }
                }
                message=String.valueOf(ch);
                intent.putExtra("message_key", message);
                startActivity(intent);
    }
    void addAnotherListener() {
        final Context context = this;
                Intent intent = new Intent(context, Vehicleinfo.class);
                EditText editText = (EditText) findViewById(R.id.searchtext);
                String message = editText.getText().toString();
                message=message.replace(" ","");
                char[] ch = new char[message.length()];
                for(int i=0;i<message.length();i++)
                 {
                     ch[i] = message.charAt(i);
                      if(ch[i]>=97&&ch[i]<=122)
                      {
                            ch[i]=Character.toUpperCase(ch[i]);
                      }
                 }
                message=String.valueOf(ch);
                intent.putExtra("message_key", message);
                startActivity(intent);
    }
    void addStatListener() {
        final Context context = this;
        Intent intent = new Intent(context, Vehiclestatinfo.class);
        EditText editText = (EditText) findViewById(R.id.searchtext);
        String message = editText.getText().toString();
        message=message.replace(" ","");
        char[] ch = new char[message.length()];
        for(int i=0;i<message.length();i++)
        {
            ch[i] = message.charAt(i);
            if(ch[i]>=97&&ch[i]<=122)
            {
                ch[i]=Character.toUpperCase(ch[i]);
            }
        }
        message=String.valueOf(ch);
        intent.putExtra("message_key", message);
        startActivity(intent);
    }
}