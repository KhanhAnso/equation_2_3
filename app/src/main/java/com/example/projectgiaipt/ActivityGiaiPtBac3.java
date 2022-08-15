package com.example.projectgiaipt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ActivityGiaiPtBac3 extends AppCompatActivity {

    private EditText edtNumberA, edtNumberB, edtNumberC, edtNumberD;
    private Button btnHandlePt;
    public static final String TAG = ActivityGiaiPtBac3.class.getName();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giai_pt_bac3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        btnHandlePt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double numberA = Integer.parseInt(edtNumberA.getText().toString().trim());
                double numberB = Double.parseDouble(edtNumberB.getText().toString().trim());
                double numberC = Double.parseDouble(edtNumberC.getText().toString().trim());
                double numberD = Double.parseDouble(edtNumberD.getText().toString().trim());

                Observable<String> ubicEquationsObservable = Observable
                        .create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

                                if (edtNumberA.toString().trim() != null && edtNumberB.toString().trim() != null && edtNumberC.toString().trim() != null
                                && edtNumberD.toString().trim() != null){
                                    try {
                                        double denta = Math.pow(numberB,2) - 3 * numberA * numberC;
                                        Log.d(TAG,"denta "+denta);
                                        double k = ((9 * numberA * numberB * numberC) - 2 * Math.pow(numberB, 3) - 27 * Math.pow(numberA, 2) * numberD) / (2 * Math.pow(Math.sqrt(Math.abs(denta)),3));
                                        if (denta > 0){
                                            if (Math.abs(k) <= 1){
                                                Log.d(TAG,"A1");
                                                double x1 = (2 * Math.sqrt(denta) * Math.cos(Math.acos(k)/3) - numberB) / (3 * numberA);
                                                double x2 = (2 * Math.sqrt(denta) * Math.cos((Math.acos(k)/3) - 2 * Math.PI/3) - numberB) / (3 * numberA);
                                                double x3 = (2 * Math.sqrt(denta) * Math.cos((Math.acos(k)/3) + 2 * Math.PI/3) - numberB) / (3 * numberA);
                                                emitter.onNext("Phương trình có 3 nghiệm x1 = "+x1+", x2 = "+x2+" và x3 = "+x3);
                                            }
                                            else if(Math.abs(k) > 1){
                                                Log.d(TAG,"A2");
                                                double x = (Math.sqrt(denta) * Math.abs(k) / 3 * numberA * k ) * (Math.cbrt(Math.abs(k) + Math.sqrt(Math.pow(k,2) - 1)) + Math.cbrt(Math.abs(k) - Math.sqrt(Math.pow(k,2) - 1))) - (numberB / 3 * numberA);
                                                emitter.onNext("|k| > 1 Phương trình có 1 nghiệm duy nhất x = "+x);
                                            }
                                        }else if(denta == 0){
                                            Log.d(TAG,"A3");
                                            double x = (-numberB + Math.cbrt(Math.pow(numberB, 3) - 27 * Math.pow(numberA,2) * numberD)) / (3 * numberA);
                                            emitter.onNext("Phương trình có nghiệm bội x = "+x);
                                        }else if(denta < 0){
                                            double x = (Math.sqrt(Math.abs(denta)) / 3 * numberA ) * (Math.cbrt(Math.abs(k) + Math.sqrt(Math.pow(k,2) + 1)) + Math.cbrt(Math.abs(k) - Math.sqrt(Math.pow(k,2) + 1))) - (numberB / 3 * numberA);
                                            emitter.onNext("Phương trình có 1 nghiệm duy nhất x = "+x);
                                        }

                                    }catch (Exception e){
                                        emitter.onNext("NaN");
                                    }

                                }

                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                ubicEquationsObservable.subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG,s);
                        dialogShow(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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


    private void init(){
        edtNumberA = findViewById(R.id.edt_number_a);
        edtNumberB = findViewById(R.id.edt_number_b);
        edtNumberC = findViewById(R.id.edt_number_c);
        edtNumberD = findViewById(R.id.edt_number_d);
        btnHandlePt = findViewById(R.id.btn_giai_ptBac3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}