package com.portfolio.wish_it;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class DataSet1 extends Activity {
    View ln1, ln2;
    EditText pday, salary;
    String result = "";
    //화폐단위 출력용 변수
    DecimalFormat df = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dataset_1);

        //PayDay 입력 뷰 가져오기
        ln1 = (View) findViewById(R.id.uline_payday);
        ln2 = (View) findViewById(R.id.uline_salary);
        pday = (EditText) findViewById(R.id.edt_payday);
        salary = (EditText) findViewById(R.id.edt_salary);

        final Button btn = (Button) findViewById(R.id.btnNext);
        btn.setEnabled(false);

        //입력 값변경 이벤트 추가
        pday.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable edit) {
                        //밑줄 색 변경
                        String s = edit.toString();
                        if (s.length() > 0) {
                            pday.setTextColor(Color.rgb(255, 184, 51));
                            ln1.setBackgroundColor(Color.rgb(255, 184, 51));
                            //버튼 활성 비활성화
                            if(salary.getText().toString().length() > 0 )
                                btn.setEnabled(true);
                        }
                        else {
                            ln1.setBackgroundColor(Color.rgb(217, 217, 217));
                            btn.setEnabled(false);
                        }
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                });

        salary.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable edit) {
                        //밑줄 색 변경
                        String s = edit.toString();
                        if (s.length() > 0) {
                            ln2.setBackgroundColor(Color.rgb(255, 184, 51));
                            //버튼 활성화 비활성화
                            if(pday.getText().toString().length() > 0)
                                btn.setEnabled(true);
                        }
                        else {
                            ln2.setBackgroundColor(Color.rgb(217, 217, 217));
                            btn.setEnabled(false);
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //화폐단위로 출력
                        if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)) {
                            result = df.format(Double.parseDouble(s.toString().replaceAll(",", "")));
                            salary.setText(result);
                            salary.setSelection(result.length());
                        }
                    }
                });

        //데이터입력 2으로 이동
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력 값 범위 제약
                if(Integer.parseInt(pday.getText().toString()) < 1 || Integer.parseInt(pday.getText().toString()) > 31) {
                    Toast.makeText(getApplicationContext(), "날짜를 확인해주세요.", Toast.LENGTH_LONG).show();
                    ln1.setBackgroundColor(Color.rgb(255, 90, 90));
                    pday.setTextColor(Color.rgb(255, 90, 90));
                    btn.setEnabled(false);
                    return;
                }

                //데이터 전달
                Intent intent1to2 = new Intent(DataSet1.this, DataSet2.class);
                intent1to2.putExtra("payDay", pday.getText().toString());   //월급날
                intent1to2.putExtra("salary", salary.getText().toString()); //월급
                //데이터입력2 페이지로 이동
                startActivity(intent1to2);
            }
        });
    }
}
