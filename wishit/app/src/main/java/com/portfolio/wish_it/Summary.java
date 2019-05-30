package com.portfolio.wish_it;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Summary extends Activity {
    //값을 저장할 전역 변수 선언
    String payDay, salary;
    String StartTime, EndTime;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_summary);

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



        //다음 페이지로 이동
        Button next = (Button) findViewById(R.id.btnNext);
        Button cancel = (Button) findViewById(R.id.btnCancel);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //값 저장
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                //editor에 put
                editor.putString("payDay", payDay); //월급날   ex) 10
                editor.putString("salary", salary); //월급    ex) 1,000,000
                editor.putString("DayOfWeek", day); //요일    ex) 월 화 수
                editor.putString("StartTime", StartTime);   //출근시간  ex) 오전 09:00
                editor.putString("EndTime", EndTime);       //퇴근시간  ex) 오후 18:00
                editor.putBoolean("IsNotFirst", true); //첫실행이 아님을 저장
                editor.commit();    //완료

                startActivity(new Intent(Summary.this, StartMain.class));
            }
        });

        //취소
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Summary.this, DataSet1.class));
            }
        });
    }
}
