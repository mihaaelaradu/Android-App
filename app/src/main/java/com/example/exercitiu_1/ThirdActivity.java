package com.example.exercitiu_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        TextView tvv = findViewById(R.id.textView2);
        //tvv.setText("haha");
        Intent i = getIntent();
        Aspirator asp  = (Aspirator) i.getSerializableExtra(getString(R.string.id));
        tvv.setText(asp.toString());

        Button buton = findViewById(R.id.button3);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}