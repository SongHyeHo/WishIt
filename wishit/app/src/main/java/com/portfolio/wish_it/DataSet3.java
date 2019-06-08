package com.portfolio.wish_it;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DataSet3 extends Activity {
    //시간 설정 Dialog 변수 선언
    public int shour, sminute;
    public int ehour, eminute;
    String stime, etime;

    TextView tvStart, tvEnd;

    //시작시간 설정 및 종료시간이 설정되었는지를 표시해줄 변수
    boolean starton=false, endon=false;

    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dataset_3);
        btn3 = (Button) findViewById(R.id.btnNext);
        //버튼 비활성화
        btn3.setEnabled(false);

        //시작시간 설정
        tvStart = (TextView) findViewById(R.id.txtStarttime);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(DataSet3.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                        String ampm;
                        //오전 오후 텍스트 입력
                        if(hourOfDay < 12) {
                            ampm = "오전 ";
                            //두자리수로 맞춰주기용 0 끼워넣기
                            if(hourOfDay < 10) {
                                ampm +="0";
                            }
                        }
                        else
                            ampm = "오후 ";

                        //두자리수 맞춰주기용 0 끼워넣기
                        shour = hourOfDay;
                        sminute = min;
                        stime = ampm+shour+":";

                        if(sminute < 10) {
                            stime += "0"+sminute;
                        }
                        else
                            stime += sminute;

                        //TextView에 값 삽입
                        tvStart.setText(stime);
                        //색변경
                        tvStart.setTextColor(Color.rgb(255, 184, 51));
                        //버튼 활성화
                        starton = true;
                        if(starton && endon)
                            btn3.setEnabled(true);
                    }
                }, 9, 0, false);
                dialog.show();
            }
        });

        //종료시간 설정
        tvEnd = (TextView) findViewById(R.id.txtEndtime);
        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(DataSet3.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                        String ampm;
                        //오전 오후 텍스트 입력
                        if(hourOfDay < 12) {
                            ampm = "오전 ";
                            //두자리수로 맞춰주기용 0 끼워넣기
                            if(hourOfDay < 10) {
                                ampm +="0";
                            }
                        }
                        else
                            ampm = "오후 ";

                        //두자리수 맞춰주기용 0 끼워넣기
                        ehour = hourOfDay;
                        eminute = min;
                        etime = ampm+ehour+":";

                        if(eminute < 10) {
                            etime += "0"+eminute;
                        }
                        else
                            etime += eminute;

                        //TextView에 값 삽입
                        tvEnd.setText(etime);
                        //색변경
                        tvEnd.setTextColor(Color.rgb(255, 184, 51));
                        //버튼 활성화
                        endon = true;
                        if(starton && endon)
                            btn3.setEnabled(true);
                    }
                }, 18, 0, false);

                dialog.show();
            }
        });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //시간 제약(같은 시간을 입력하면 안됨 || 시간은 같으면서 시작 분보다 끝나는 분이 크면안됨)
                    if((shour == ehour && sminute == eminute) || (shour==ehour && sminute > eminute)) {
                        Toast.makeText(getApplicationContext(), "시작/종료시간을 확인해주세요.", Toast.LENGTH_LONG).show();
                        btn3.setEnabled(false);
                    }

                    //전 엑티비티로부터 값 불러오기
                    Intent intent = getIntent();

                    //데이터이동용 Intent
                    Intent intent3toS = new Intent(DataSet3.this, Summary.class);
                    intent3toS.putExtra("payDay", intent.getExtras().getString("payDay"));  //월급날
                    intent3toS.putExtra("salary", intent.getExtras().getString("salary"));  //월급
                    intent3toS.putExtra("DayOfWeek", intent.getExtras().getString("DayOfWeek"));    //요일 배열
                    intent3toS.putExtra("StartTime", stime);    //출근시간
                    intent3toS.putExtra("EndTime", etime);      //퇴근시간

                    startActivity(intent3toS);
                }
            });
    }

}
