package com.example.toshiba.butondeneme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ButonActivity extends Activity {

    EditText ad, soyad;
    Button button;
    TextView adgoster, soyadgoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buton);
        ad = (EditText)findViewById(R.id.ad);
        soyad = (EditText)findViewById(R.id.soyad);
        button = (Button)findViewById(R.id.button);
        adgoster = (TextView)findViewById(R.id.adgoster);
        soyadgoster = (TextView)findViewById(R.id.soyadgoster);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adgoster.setText(ad.getText().toString());
                soyadgoster.setText(soyad.getText().toString());
            }
        });

    }
}
