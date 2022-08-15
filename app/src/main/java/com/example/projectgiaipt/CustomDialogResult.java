package com.example.projectgiaipt;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomDialogResult extends Dialog {

    public Context context;
    public TextView txtShowResult;
    public Button btnOkResults;
    public String result;

    public CustomDialogResult(@NonNull Context context, String result) {
        super(context);
        this.context = context;
        this.result = result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_results);

        btnOkResults = findViewById(R.id.btn_ok_result);
        txtShowResult = findViewById(R.id.txt_show_results);

        txtShowResult.setText(result);
        btnOkResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOkResult();
            }
        });
    }
    private void buttonOkResult(){
        this.dismiss();
    }
}
