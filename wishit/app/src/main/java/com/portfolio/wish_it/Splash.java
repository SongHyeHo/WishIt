package com.portfolio.wish_it;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 2000);
    }

    private class splashHandler implements Runnable {
        public void run() {
            //저장값 가져오기(첫실행 여부)
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            //이미 실행되었으면 값이 있으므로 바로 메인으로 이동
            if(pref.getBoolean("IsNotFirst", false)) {
                startActivity(new Intent(getApplication(), MainDay.class));
            }
            //첫 실행이므로 데이터가 필요하여 입력 창으로 이동
            else
                //로딩 후 InfoPage 로 이동
                startActivity(new Intent(getApplication(), InfoPage.class));

            //로딩 페이지 Activity 스택에서 제외
            Splash.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        //플래쉬 화면에서 뒤로가기 못누르게 제어
    }
}
