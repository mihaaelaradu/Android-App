package com.example.exercitiu_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText edModel = findViewById(R.id.edModel);
        final EditText edPret = findViewById(R.id.edPret);
        final Button buton = findViewById(R.id.button2);
        AspiratorDB aspiratorDB = AspiratorDB.getInstance(getApplicationContext());

        Intent i = getIntent();

        if (i.hasExtra("aspiratorEditare")) {
            Aspirator aspirator = (Aspirator) i.getSerializableExtra("aspiratorEditare");
            edModel.setText(aspirator.getModel());
            edPret.setText(""+aspirator.getPret());
        }


        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = getIntent();
                if (i.hasExtra("aspiratorNou")) {
                    String model = edModel.getText().toString();
                    float pret = Float.parseFloat(edPret.getText().toString());
                    Aspirator aspirator = new Aspirator(model, pret);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("obiectNou", aspirator);
                    startActivity(intent);
                }
                else if (i.hasExtra("aspiratorEditare")) {
                    ///editez
                    String model = edModel.getText().toString();
                    float pret = Float.parseFloat(edPret.getText().toString());

                    Aspirator aspirator = (Aspirator) i.getSerializableExtra("aspiratorEditare");
                    aspirator.setModel(model);
                    aspirator.setPret(pret);
                    aspiratorDB.getAutobuzDao().updateAspirator(aspirator);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("obiectEditat", aspirator);
                    startActivity(intent);
                }
            }
        });
    }
}