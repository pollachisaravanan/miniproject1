package com.example.lotusautoconsulting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Vehiclestatinfo extends AppCompatActivity {
    TextView receiver_msg,reg_num,brand,variant,model,pur_date,pur_amount,ins_exp,status,emi;
    Button button;
    SQLiteDatabase db;
    String reg,b,v,m,pd,pa,ins,st,emi1;
    Button del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclestatinfo);
        receiver_msg = (TextView) findViewById(R.id.vehicle_num);
        Intent intent = getIntent();
        final String str = intent.getStringExtra("message_key");
        final Context context = this;
        receiver_msg.setText(str);
        try{
            db=openOrCreateDatabase("LotusAutoConsultingDB",SQLiteDatabase.CREATE_IF_NECESSARY,null);
            //db.execSQL("Create Table Vehicle(Reg_num text,Brand text,Variant text,Model text,Purchase_date date,Purchase_amount number,Insurance_expiry_date date)");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        Cursor c1=db.rawQuery("SELECT * FROM Vehicledetails Where Reg_num='"+str+"'",null);
        reg_num = (TextView) findViewById(R.id.reg_no);
        brand = (TextView) findViewById(R.id.brand);
        variant = (TextView) findViewById(R.id.var);
        model = (TextView) findViewById(R.id.mod);
        pur_date = (TextView) findViewById(R.id.pur_d);
        pur_amount = (TextView) findViewById(R.id.pur_a);
        ins_exp = (TextView) findViewById(R.id.ins_d);
        status = (TextView) findViewById(R.id.status1);
        emi=(TextView)findViewById(R.id.emistat);
        c1.moveToFirst();
        while(!c1.isAfterLast())
        {
            reg = c1.getString(0);
            b = c1.getString(1);
            v = c1.getString(2);
            m = c1.getString(3);
            pd = c1.getString(4);
            pa = c1.getString(5);
            ins = c1.getString(6);
            st = c1.getString(7);
            emi1=c1.getString(8);
            //Toast.makeText(MainActivity.this,c.getString(0)+ " "+c.getString(1),1000).show();
            reg_num.setText(reg);
            brand.setText(b);
            variant.setText(v);
            model.setText(m);
            pur_date.setText(pd);
            pur_amount.setText(pa);
            ins_exp.setText(ins);
            status.setText(st);
            emi.setText(emi1);
            c1.moveToNext();
        }
        del = (Button) findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Vehiclestatinfo.this);
                builder.setMessage("Are u Sure Want to Delete?It will Delete all the Emi and Vehicle Details")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.execSQL("DELETE FROM Vehicledetails WHERE Reg_num = "+"'"+str+"'");
                                db.execSQL("DELETE FROM Emidetails WHERE Reg_num = "+"'"+str+"'");
                                Toast.makeText(Vehiclestatinfo.this, "Vehicle Deleted", 2000).show();
                                del.setEnabled(false);
                                button.setEnabled(false);
                            }
                        })
                        .setNegativeButton("NO",null);
                AlertDialog alert=builder.create();
                alert.show();
                //final View.OnClickListener content=this;
                //Intent intent = new Intent((Context) content, MainActivity.class);
                //startActivity(intent);

            }
        });
        button = (Button) findViewById(R.id.emi);
        if(emi1.equals("NO"))
        {
            button.setEnabled(false);
        }
        else
        {
            button.setEnabled(true);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Emi_update.class);
                intent.putExtra("message_key", str);
                startActivity(intent);
                //EditText editText = (EditText) findViewById(R.id.editText);
                //String message = editText.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);
            }
        });

    }
}