package me.nikantchaudhary.taxifare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nikant on 8/19/2017.
 */


public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.etBaseRate)
    EditText etBaseRate;

    @BindView(R.id.etFareRate)
    EditText etFareRate;

    @BindView(R.id.etTimeRate)
    EditText etTimeRate;

    @BindView(R.id.btnEdit)
    Button btnEdit;

    @BindView(R.id.btnOk)
    Button btnOk;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        etBaseRate.setEnabled(false);
        etFareRate.setEnabled(false);
        etTimeRate.setEnabled(false);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etBaseRate.setEnabled(true);
                etFareRate.setEnabled(true);
                etTimeRate.setEnabled(true);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baseRate = etBaseRate.getText().toString().trim();
                String fareRate = etFareRate.getText().toString().trim();
                String timeRate = etTimeRate.getText().toString().trim();



            }
        });


    }
}
