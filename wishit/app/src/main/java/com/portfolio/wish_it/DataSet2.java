package com.portfolio.wish_it;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

        //버튼 체크 체인지 이벤트 추가
        //7개나 되서 for 문으로 처리
        for(int i=0; i<7; i++) {
            tbtn[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //체크시
                    if(isChecked)
                        buttonView.setTextColor(Color.rgb(255,255,255));
                    //체크 해제시
                    else
                        buttonView.setTextColor(Color.rgb(201,201,201));
                }
            });
        }
    }
}
