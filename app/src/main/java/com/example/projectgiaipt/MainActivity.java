package com.example.projectgiaipt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnGiaiPtBac3, btnGiaiHePt2An;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGiaiHePt2An = findViewById(R.id.btn_giai_he_pt_2an);
        btnGiaiPtBac3 = findViewById(R.id.btn_giai_pt_bac3);

        btnGiaiPtBac3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityGiaiPtBac3.class));
            }
        });

        btnGiaiHePt2An.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityGiaiHePt2An.class));
            }
        });

    }
}