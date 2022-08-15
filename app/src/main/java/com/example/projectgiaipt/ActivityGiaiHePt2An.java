package com.example.projectgiaipt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ActivityGiaiHePt2An extends AppCompatActivity {

    ImageView imgEdtPt1, imgEdtPt2;
    Button btnHandleHePT;
    TextView txtShowPt1, txtShowPt2;

    String numberA1, numberA2, numberB1, numberB2, numberC1, numberC2;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giai_he_pt2_an);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        imgEdtPt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInputPT1();
            }
        });

        imgEdtPt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInputPT2();
            }
        });

        btnHandleHePT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Observable<String> hePt2AnObservable = Observable
                        .create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                                if (numberA1 != null & numberA2 != null && numberB1 != null && numberB2 != null & numberC1 != null && numberC2 != null){
                                    double a1 = Double.parseDouble(numberA1);
                                    double b1 = Double.parseDouble(numberB1);
                                    double c1 = Double.parseDouble(numberC1);
                                    double a2 = Double.parseDouble(numberA2);
                                    double b2 = Double.parseDouble(numberB2);
                                    double c2 = Double.parseDouble(numberC2);

                                    double y = (( c2 / b2) - ((a2 * c1) /(a1 * b2))) * (a1 * b2) / (a1 * b2 - a2 * b1);
                                    double x = ( c1 - b1 * y) / a1;
                                    String result = "Hệ phương trình có nghiệm X = "+x+" và Y = "+y;
                                    //dialogShow(result);
                                    emitter.onNext(result);
                                }else {
                                    emitter.onError(new Exception("empty"));
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                //s: data emitter
                disposable.add(hePt2AnObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(e -> {
                    dialogShow(e);
                }, error -> {
                    Toast.makeText(ActivityGiaiHePt2An.this, "R.string.toast_full_info", Toast.LENGTH_LONG).show();
                }));

            }
        });


    }

    private void dialogShow(String result){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_results);
        TextView txtResults = dialog.findViewById(R.id.txt_show_results);
        Button btnOkResult = dialog.findViewById(R.id.btn_ok_result);
        txtResults.setText(result);

        btnOkResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void dialogInputPT1(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_input_pt);

        EditText edtNumberA = dialog.findViewById(R.id.edt_number_a_hpt);
        EditText edtNumberB = dialog.findViewById(R.id.edt_number_b_hpt);
        EditText edtNumberC = dialog.findViewById(R.id.edt_number_c_hpt);
        Button btnOk = dialog.findViewById(R.id.btn_ok_pt_2an_htp);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberA1 = edtNumberA.getText().toString().trim();
                numberB1 = edtNumberB.getText().toString().trim();
                numberC1 = edtNumberC.getText().toString().trim();
                txtShowPt1.setText(numberA1+"*X + "+numberB1+"*Y = "+numberC1);

                dialog.cancel();
            }
        });
        dialog.show();

    }

    private void dialogInputPT2(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_input_pt);

        EditText edtNumberA = dialog.findViewById(R.id.edt_number_a_hpt);
        EditText edtNumberB = dialog.findViewById(R.id.edt_number_b_hpt);
        EditText edtNumberC = dialog.findViewById(R.id.edt_number_c_hpt);
        Button btnOk = dialog.findViewById(R.id.btn_ok_pt_2an_htp);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberA2 = edtNumberA.getText().toString().trim();
                numberB2 = edtNumberB.getText().toString().trim();
                numberC2 = edtNumberC.getText().toString().trim();
                txtShowPt2.setText(numberA2+"*X + "+numberB2+"*Y = "+numberC2);

                dialog.cancel();
            }
        });
        dialog.show();

    }
    private void init(){
        imgEdtPt1 = findViewById(R.id.img_edit_pt1);
        imgEdtPt2 = findViewById(R.id.img_edit_pt2);
        btnHandleHePT = findViewById(R.id.btn_giai_he_pt);
        txtShowPt1 = findViewById(R.id.txt_show_pt1);
        txtShowPt2 = findViewById(R.id.txt_show_pt2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}