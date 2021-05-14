package com.example.lotusautoconsulting;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class AddVehicleForm extends AppCompatActivity {
    SQLiteDatabase db;
    Button btnInsert;
    boolean isAllFieldsChecked = false;
    EditText Brand,Variant,Model,Purchase_date,Purchase_amount,Insurance_expiry_date;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_form);
        Intent intent = getIntent();
        final String str = intent.getStringExtra("message_key");
        EditText Register_num=(EditText) findViewById(R.id.register);
        Register_num.setText(str);
        btnInsert=(Button)findViewById(R.id.button3);
        try{
            db=openOrCreateDatabase("LotusAutoConsultingDB",SQLiteDatabase.CREATE_IF_NECESSARY,null);
            //db.execSQL("Create Table IF NOT EXISTS Vehicledetails(Reg_num text,Brand text,Variant text,Model number,Purchase_date date,Purchase_amount number,Insurance_expiry_date date,Status text)");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText emi_details=(EditText) findViewById(R.id.emi);
                String emi_details="NO";
                EditText Register_num=(EditText) findViewById(R.id.register);
                String rgn=Register_num.toString();
                Brand=(EditText)findViewById(R.id.brand);
                Variant=(EditText)findViewById(R.id.var);
                Model=(EditText)findViewById(R.id.model);
                Purchase_date=(EditText)findViewById(R.id.purdate);
                String pd=  Purchase_date.getText().toString();
                int c=pd.indexOf('/');
                Purchase_amount=(EditText)findViewById(R.id.puramount);
                Insurance_expiry_date=(EditText)findViewById(R.id.insexp);
                String ied=  Insurance_expiry_date.getText().toString();
                int d=ied.indexOf('/');
                EditText status=(EditText)findViewById(R.id.status);
                String sd=status.getText().toString();
                boolean bd=sd.equals("Available");
                boolean bd1=sd.equals("Sold");
                isAllFieldsChecked = CheckAllFields();
                if(bd1||bd) {
                    if ((isAllFieldsChecked) && (c >= 0) && (d >= 0)) {
                        ContentValues values = new ContentValues();
                        values.put("Reg_num", Register_num.getText().toString());
                        values.put("Brand", Brand.getText().toString());
                        values.put("Variant", Variant.getText().toString());
                        values.put("Model", Model.getText().toString());
                        values.put("Purchase_date", Purchase_date.getText().toString());
                        values.put("Purchase_amount", Purchase_amount.getText().toString());
                        values.put("Insurance_expiry_date", Insurance_expiry_date.getText().toString());
                        values.put("Status", status.getText().toString());
                        values.put("emi", emi_details);
                        if (db.insert("Vehicledetails", null, values) != -1) {
                            Toast.makeText(AddVehicleForm.this, "Vehicle added", 2000).show();
                            homepage();
                            //final View.OnClickListener content=this;
                            //Intent intent = new Intent((Context) content, MainActivity.class);
                            //startActivity(intent);
                            //Toast.makeText(AddVehicleForm.this,0,2000).show();
                        } else {
                            Toast.makeText(AddVehicleForm.this, "Insert Error", 2000).show();
                        }
                    } else if (Brand.length() == 0 || Variant.length() == 0 || Model.length() == 0) {
                        Toast.makeText(AddVehicleForm.this, "Fill the Field", 5000).show();
                    } else {
                        Toast.makeText(AddVehicleForm.this, "Date should be DD/MM/YYYY FORMAT", 5000).show();
                    }
                }
                else
                {
                    Toast.makeText(AddVehicleForm.this, "vehicle should be Available or Sold", 5000).show();
                }
                Register_num.setText(str);
                Brand.setText("");
                Variant.setText("");
                Model.setText("");
                Purchase_date.setText("");
                Purchase_amount.setText("");
                Insurance_expiry_date.setText("");
                status.setText("Available");

            }
        });



    }
    public boolean CheckAllFields()
    {
        if (Brand.length() == 0) {
            Brand.setError("This field is required");
            return false;
        }
        if(Variant.length()==0)
        {
            Variant.setError("This field is required");
            return false;
        }
        if(Model.length()==0)
        {
            Model.setError("This field is required");
            return false;
        }
        if(Purchase_date.length()==0) {
            Purchase_date.setError("This field is required");
            return false;
        }
        if(Insurance_expiry_date.length()==0) {
            Insurance_expiry_date.setError("This field is required");
            return false;
        }
        return true;

    }
    void homepage() {
        final Context context = this;
        Intent intent = new Intent(context,MainActivity.class);
        startActivity(intent);
    }


}
