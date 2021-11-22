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



public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button b1;
    boolean isAllFieldsChecked = false;
    EditText ename1,edob1,egender1,edistrict1,eemail1,ephone1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnInsert=(Button)findViewById(R.id.button);
        try{
            db=openOrCreateDatabase("Voter_Eligiblity",SQLiteDatabase.CREATE_IF_NECESSARY,null);
            //db.execSQL("Create Table IF NOT EXISTS VoterDetails(name2 text,dob2 text,district2 text,gender2 text,email2 text,phone2 text)");
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ename1=(EditText) findViewById(R.id.ename);
             
                edob1=(EditText)findViewById(R.id.edob);
                edistrict1=(EditText)findViewById(R.id.edistrict);
                egender1=(EditText)findViewById(R.id.egender);
                eemail1=(EditText)findViewById(R.id.eemail);
                String pd=  edob1.getText().toString();
                int c=pd.indexOf('/');
				String pd1=eemail1.getText().toString();
				int d=pd.index('@');
                ephone1=(EditText)findViewById(R.id.ephone);
                isAllFieldsChecked = CheckAllFields();
				if ((isAllFieldsChecked) && (c >= 0)&&(d>=0) {
                        ContentValues values = new ContentValues();
                        values.put("name2", ename1.getText().toString());
                        values.put("dob2", edob1.toString());
                        values.put("district2", edistrict1.getText().toString());
                        values.put("gender2", egender1.getText().toString());
                        values.put("email2", eemail1.getText().toString());
                        values.put("phone2", ephone1.getText().toString());
                        if (db.insert("Vehicledetails", null, values) != -1) {
                            Toast.makeText(MainActivity.this, "Data added", 2000).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Insert Error", 2000).show();
                        }
                    } else if (ename1.length() == 0 || edistrict1.length() == 0 || egender1.length() == 0 || e) {
                        Toast.makeText(AddVehicleForm.this, "Fill the Field", 5000).show();
                    } else {
                        Toast.makeText(AddVehicleForm.this, "Date should be DD/MM/YYYY FORMAT", 5000).show();
                    }
                ename1.setText(str);
                edob1.setText("");
                edistrict1.setText("");
                egender1.setText("");
                eemail1.setText("");
                ephone1.setText("");

            }
        });



    }
    public boolean CheckAllFields()
    {
        if (ename1.length() == 0) {
            ename1.setError("This field is required");
            return false;
        }
        if(edistrict1.length()==0)
        {
            edistrict1.setError("This field is required");
            return false;
        }
        if(ephone1.length()!=10)
        {
            ephone1.setError("This field is required");
            return false;
        }
        if(egender1.length()==0) {
            egender1.setError("This field is required");
            return false;
        }
        return true;

    }


}
