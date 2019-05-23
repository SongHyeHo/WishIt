package com.portfolio.wish_it;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

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
