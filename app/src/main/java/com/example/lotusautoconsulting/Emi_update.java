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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class Emi_update extends AppCompatActivity {
    SQLiteDatabase db;
    int no_of_emi4,pending_amount1=1,paid_amount1;
    String reg3,name3,email3,phone3,due_date3,tot_amount3,paid_amount3,address3,no_of_emi3,pen_amount3,Amount1,Due_date1;
    TextView emi,pending_amount,completed,regnum1;
    Button button,button1;
    int Amount2;
    EditText t1,t2;
    String pending_amount5,no_of_emi5,paid_amount5;
    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_update);
        Intent intent = getIntent();
        final String str = intent.getStringExtra("message_key");
        final Context context = this;
        try{
            db=openOrCreateDatabase("LotusAutoConsultingDB",SQLiteDatabase.CREATE_IF_NECESSARY,null);
            //db.execSQL("Create Table Vehicle(Reg_num text,Brand text,Variant text,Model text,Purchase_date date,Purchase_amount number,Insurance_expiry_date date)");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        Cursor c1=db.rawQuery("SELECT * FROM Emidetails Where Reg_num='"+str+"'",null);
        emi=(TextView)findViewById(R.id.noofemi);
        pending_amount=(TextView)findViewById(R.id.pendingamount);
        completed=(TextView)findViewById(R.id.completed);
        regnum1=(TextView)findViewById(R.id.reg_num1);
        button=(Button)findViewById(R.id.view);
        button1=(Button)findViewById(R.id.update);
        t1 = (EditText) findViewById(R.id.editText);
        t2 = (EditText) findViewById(R.id.editText2);
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
        emi.setText(no_of_emi3);
        pending_amount.setText(pen_amount3);
        regnum1.setText(str);
        pending_amount1 = Integer.parseInt(pen_amount3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Due_date1 = t2.getText().toString();
                Amount1 = t1.getText().toString();
                int a = Due_date1.indexOf('/');
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    if (a>=0) {
                        no_of_emi4 = Integer.parseInt(no_of_emi3);
                        paid_amount1 = Integer.parseInt(paid_amount3);
                        Amount2 = Integer.parseInt(Amount1);
                        pending_amount1 = pending_amount1 - Amount2;
                        no_of_emi4 = no_of_emi4 + 1;
                        if (pending_amount1 < 0) {
                            pending_amount1 = 0;
                        }
                        paid_amount1 = paid_amount1 + Amount2;
                        pending_amount5 = Integer.toString(pending_amount1);
                        no_of_emi5 = Integer.toString(no_of_emi4);
                        paid_amount5 = Integer.toString(paid_amount1);
                        db.execSQL("UPDATE Emidetails SET Paid_amount = " + "'" + paid_amount5 + "' " + "WHERE Reg_num = " + "'" + str + "'");
                        db.execSQL("UPDATE Emidetails SET Due_date = " + "'" + Due_date1 + "' " + "WHERE Reg_num = " + "'" + str + "'");
                        db.execSQL("UPDATE Emidetails SET No_of_emi_paid = " + "'" + no_of_emi5 + "' " + "WHERE Reg_num = " + "'" + str + "'");
                        db.execSQL("UPDATE Emidetails SET pen_amount = " + "'" + pending_amount5 + "' " + "WHERE Reg_num = " + "'" + str + "'");
                        Toast.makeText(Emi_update.this, "Emi Updated", 2000).show();
                        t1.setText("");
                        t2.setText("");

                    }
                    else
                    {
                        Toast.makeText(Emi_update.this, "Date Should be DD/MM/YYYY format", 2000).show();
                    }

                }
                else
                {
                    Toast.makeText(Emi_update.this, "Fill the Field", 2000).show();
                }
            }
        });
        if(pending_amount1==0)
        {
            completed.setText("EMI COMPLETED");
            button1.setEnabled(false);
            db.execSQL("UPDATE Emidetails SET Due_date = " + "'" +"NO DUE DATE"+ "' " + "WHERE Reg_num = " + "'" + str + "'");

        }
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Emi_view.class);
                intent.putExtra("message_key", str);
                startActivity(intent);
            }
        });

    }
    public boolean CheckAllFields() {
        if (t1.length() == 0) {
            t1.setError("This field is required");
            return false;
        }
        if(t2.length()==0)
        {
            t2.setError("This field is required");
            return false;
        }
        return true;

    }
}
