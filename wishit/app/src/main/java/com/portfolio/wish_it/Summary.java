package com.portfolio.wish_it;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class Summary extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_summary);

        //값을 저장할 변수 선언
        String payDay, salary;
        String StartTime, EndTime;
        String day;


        //값 전달 받기
        Intent intent = getIntent();
        payDay = intent.getExtras().getString("payDay");
        salary = intent.getExtras().getString("salary");
        day = intent.getExtras().getString("DayOfWeek");
        StartTime = intent.getExtras().getString("StartTime");
        EndTime = intent.getExtras().getString("EndTime");


        //뷰 가져오기
        TextView pDay = (TextView) findViewById(R.id.tvPayDay);
        TextView saly = (TextView) findViewById(R.id.tvSalary);
        TextView dow = (TextView) findViewById(R.id.tvDOW);
        TextView time = (TextView) findViewById(R.id.tvTime);

        pDay.setText(payDay+"일");
        saly.setText(salary+"원");
        dow.setText(day);
        time.setText(StartTime+"~"+EndTime);

    }
}
