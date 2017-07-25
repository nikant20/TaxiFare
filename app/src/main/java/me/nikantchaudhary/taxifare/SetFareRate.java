package me.nikantchaudhary.taxifare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nikant
 */
public class SetFareRate extends AppCompatActivity {

    EditText etBaseRate, etRate, etTimeRate;
    TextView tvSubmit;
    SQLiteDatabase db;
    DBHandler dbhandler;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setfare);

        dbhandler = new DBHandler(this);
        etBaseRate = (EditText) findViewById(R.id.etBaseRate);
        etRate = (EditText) findViewById(R.id.etRate);
        etTimeRate = (EditText) findViewById(R.id.etTimeRate);
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);


        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String baseRate = etBaseRate.getText().toString();
                String rate = etRate.getText().toString();
                String timeRate = etTimeRate.getText().toString();
                SharedPreferences settings = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = settings.edit();

                    if (etBaseRate.length()!=0) {
                        edit.putString("BaseRate", baseRate);
                    } else {
                       etBaseRate.setError("Enter some Base Rate");
                    }
                    if (etRate.length()!=0) {
                        edit.putString("Rate", rate);
                    } else {
                        etRate.setError("Enter some Rate");
                    }
                    if (etTimeRate.length()!=0) {
                        edit.putString("TimeRate", timeRate);
                    } else {
                        etTimeRate.setError("Enter some Time Rate");
                    }
                if(baseRate.length()>0 && rate.length()>0 && timeRate.length()>0) {
                    edit.commit();
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(i);
                    finish();
                }
                else{toast();}
               /*long id = dbhandler.insertFareDetails(baseRate, rate);
                if (id != -1) {
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(i);
                    finish();
                }
          */
            }

            private void toast() {
                Toast.makeText(getApplicationContext(),"Kindly give valid inputs",Toast.LENGTH_SHORT);
            }

        });

    }

    /** private void insertValues() {
     String baseRate = etBaseRate.getText().toString();
     String rate = etRate.getText().toString();

     long id = dbhandler.insertFareDetails(baseRate,rate);
     if(id != -1){
     Intent i = new Intent(getApplicationContext(),MapsActivity.class);
     startActivity(i);
     finish();
     }
     **/
}

