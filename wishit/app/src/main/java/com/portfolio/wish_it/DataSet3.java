package com.portfolio.wish_it;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DataSet3 extends Activity {
    //시간 설정 Dialog 변수 선언
    public int hour, minute;
    public int mHour, mMinute;

    TextView tvStart, tvEnd;

    public DataSet3() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dataset_3);

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
                        hour = hourOfDay;
                        minute = min;
                        String time = ampm+hour+":";

                        if(minute < 10) {
                            time += "0"+minute;
                        }
                        else
                            time += minute;

                        //TextView에 값 삽입
                        tvStart.setText(time);
                        //색변경
                        tvStart.setTextColor(Color.rgb(255, 184, 51));
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
                        hour = hourOfDay;
                        minute = min;
                        String time = ampm+hour+":";

                        if(minute < 10) {
                            time += "0"+minute;
                        }
                        else
                            time += minute;

                        //TextView에 값 삽입
                        tvEnd.setText(time);
                        //색변경
                        tvEnd.setTextColor(Color.rgb(255, 184, 51));
                    }
                }, 18, 0, false);

                dialog.show();
            }
        });
    }

}
