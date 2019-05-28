package com.portfolio.wish_it;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class InfoPage extends Activity {
    Button btnN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_info);

        btnN = (Button) findViewById(R.id.btnNext);
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터입력1 페이지로 이동
                startActivity(new Intent(InfoPage.this, DataSet1.class));
            }
        });
    }
}
