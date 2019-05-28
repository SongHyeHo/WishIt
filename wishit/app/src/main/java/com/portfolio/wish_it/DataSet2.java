package com.portfolio.wish_it;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class DataSet2 extends Activity {
    ToggleButton tbtn[] = new ToggleButton[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dataset_2);

        tbtn[0] = (ToggleButton) findViewById(R.id.togbtnMon);
        tbtn[1] = (ToggleButton) findViewById(R.id.togbtnTue);
        tbtn[2] = (ToggleButton) findViewById(R.id.togbtnWed);
        tbtn[3] = (ToggleButton) findViewById(R.id.togbtnThu);
        tbtn[4] = (ToggleButton) findViewById(R.id.togbtnFri);
        tbtn[5] = (ToggleButton) findViewById(R.id.togbtnSat);
        tbtn[6] = (ToggleButton) findViewById(R.id.togbtnSun);

        final Button btn2 = (Button) findViewById(R.id.btnNext);
        //"다음으로" 버튼 기본 비활성화
        btn2.setEnabled(false);


        //버튼 체크 체인지 이벤트 추가
        //7개나 되서 for 문으로 처리
        for(int i=0; i<7; i++) {
            tbtn[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //체크시
                    if(isChecked) {
                        buttonView.setTextColor(Color.rgb(255, 255, 255));
                        for(int j=0; j<7; j++) {
                            //하나라도 Checked시 버튼 활성화
                            if(tbtn[j].isChecked()) {
                                btn2.setEnabled(true);
                                return;
                            }
                        }
                    }
                    //체크 해제시
                    else {
                        buttonView.setTextColor(Color.rgb(201, 201, 201));
                        //모두 비활성화시 버튼 역시 비활성화
                        //비활성화인것을 확인할때마다 cnt 증가
                        //cnt가 7일 시 버튼 비활성
                        int cnt=0;
                        for(int j=0; j<7; j++) {
                            if(!tbtn[j].isChecked()) {
                                cnt++;
                            }
                        }
                        if(cnt == 7) {
                            btn2.setEnabled(false);
                            cnt = 0;
                        }
                    }
                }
            });
        }

        //데이터입력 3으로 이동
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터입력3 페이지로 이동
                startActivity(new Intent(DataSet2.this, DataSet3.class));
            }
        });
    }
}
