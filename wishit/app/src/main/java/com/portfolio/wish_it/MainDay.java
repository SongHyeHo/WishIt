package com.portfolio.wish_it;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainDay extends Activity {
    //상수 선언
    final int TRANS_TIME = 1;
    final int TRANS_DATE = 2;
    final int TRANS_YMD = 3;
    //메서드 실행 주기 및 일하는 시간의 초단위 조절
    final int TIME_CYCLE = 10;

    //전역변수 선언
    //월급날
    String payDay;
    //월급(화폐표기 지우기 작업까지)
    Double salary;
    //요일(배열에 각각 한칸에 저장)
    String day;
    //일일급여, 초당급여, 현재 번돈(일당)
    Double dailywage, secwage, nowmoney;
    //일하는 시간, 현재까지 일한 시간
    long worktimesec, worktimenow;

    //년-월-일 날짜 폼 생성
    SimpleDateFormat yMdFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
    //전체 날짜 폼 생성
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
    //크기 비교용 폼 생성
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());

    //오늘 년월일 구해오기(시작할 때 한번만 실행됨)
    long now = System.currentTimeMillis();      //현재 날짜와 시간을 가져옴(밀리초 단위)
    Date Today = new Date(now);
    String strToday = yMdFormat.format(Today);  //strToday = "yyyy-MM-dd"

    String stime, etime;        //출, 퇴근 시간을 불러와서 저장
    Date sDate, eDate;          //저장된 출퇴근 시간을 Date형태로 변환하여 저장

    boolean todayiswork=false;  //오늘이 일하는 요일인지 저장
    int cntWorkDay = 0;         //일하는 전체 요일 수

    //현재 급여를 표시하는 뷰 및 관련 변수
    TextView TvcurrentMoney;        //현재 급여 뷰
    TimerTask timertask;            //반복 실행을 위한 TimerTask
    final Handler mHandler = new Handler(); //TimerTask의 UI변경을 위한 핸들러

    //그래프 관련 변수
    TextView Tvpercent;     //현재 급여 퍼센트 뷰
    View graph;             //그래프 뷰

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //해당 달의 날짜 관련 메소드
    public void SetDayInMonth(String arr[]) {
        Calendar c = Calendar.getInstance();
        Calendar cl = Calendar.getInstance();
        //현재 년/달/1일/마지막일
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int fday = 1;
        cl.set(year, month, fday);
        int lday = cl.getActualMaximum(Calendar.DATE);

        //일하는 요일 수 구하기
        for(int i=1; i<=lday; i++) {
            cl.set(year,month,i);
            for(int j=0; j<arr.length; j++) {
                if(cl.get(Calendar.DAY_OF_WEEK) == Integer.parseInt(arr[j])) {
                    cntWorkDay++;
                }
            }
        }

        //오늘이 일하는 날인지 구하기
        c.setTime(Today);
        for(int i = 0; i<arr.length; i++) {
            if(c.get(Calendar.DAY_OF_WEEK) == Integer.parseInt(arr[i])) {
                todayiswork = true;
            }
        }

        //일일 급여 구하기
        dailywage = salary / cntWorkDay;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        //상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //화면에 레이아웃 표시
        //if(todayiswork) {
            setContentView(R.layout.activity_main_day);
        //}
        //else
        //    setContentView(R.layout.activity_noworkday);

        //SharedPreferences 에 저장되있는 데이터 가져와서 저장
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        //월급날
        payDay = pref.getString("payDay", null);
        //월급(화폐표기 지우기 작업까지)
        salary = Double.parseDouble(pref.getString("salary", null).replaceAll(",", ""));
        //요일(배열에 각각 한칸에 저장)
        day = pref.getString("DayOfWeek", null);
        String dayofweek[] = day.split("  ");
        //요일 배열 숫자로 문자 변경
        for(int i=0; i<dayofweek.length; i++) {
            switch(dayofweek[i]) {
                case "일":
                    dayofweek[i] = "1"; break;
                case "월":
                    dayofweek[i] = "2"; break;
                case "화":
                    dayofweek[i] = "3"; break;
                case "수":
                    dayofweek[i] = "4"; break;
                case "목":
                    dayofweek[i] = "5"; break;
                case "금":
                    dayofweek[i] = "6"; break;
                case "토":
                    dayofweek[i] = "7"; break;
            }
        }

        //날짜 관련 설정
        SetDayInMonth(dayofweek);

        //출퇴근 시간 구하기
        stime = pref.getString("StartTime", null).replace("오전 ", "").replace("오후 ", "");
        etime = pref.getString("EndTime", null).replace("오전 ", "").replace("오후 ", "");
        sDate = TransFormStringtoDate(strToday + " " + stime);  //yyyy-MM-dd HH:mm form
        eDate = TransFormStringtoDate(strToday + " " + etime);  //yyyy-MM-dd HH:mm form
        //시간 비교
        //sDate가 eDate보다 뒤 일때 eDate의 Day+1
        //아니면 그냥 그대로 사용
        if(sDate.after(eDate)) {
            Calendar c = Calendar.getInstance();
            c.setTime(eDate);
            c.add(Calendar.DATE, 1);

            eDate = c.getTime();
        }
        //시간의 차이를 구해 일하는 시간 구하기(초단위)
        worktimesec = (eDate.getTime()-sDate.getTime()) / TIME_CYCLE;
        secwage = dailywage / worktimesec;
        //현재까지 번 급여 표시
        CostAndGraphTimer();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //현재 급여를 계산하는 메소드
    public void CostAndGraphTimer() {
        //급여 표시
        TvcurrentMoney = (TextView) findViewById(R.id.currentMoney);
        //급여 퍼센트
        Tvpercent = (TextView) findViewById(R.id.percent);
        //급여 그래프
        graph = (View) findViewById(R.id.view_graph);
        Date d = new Date(System.currentTimeMillis());

        //시간에 따른 변수 초기화
        if(d.getTime() > eDate.getTime()) {
            nowmoney = dailywage;
            Tvpercent.setText("100.00%");
        }
        else if(d.getTime() < sDate.getTime()) {
            nowmoney = 0.0;
        }

        timertask = new TimerTask() {
            @Override
            public void run() {
                //필요한 데이터를 보여주는 로그(그떄그떄 입력 바꿔가면서 사용)
                //Log.i("Now Money", String.valueOf((nowmoney/dailywage)*100));
                Update_Timer();
                Graph_Timer();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timertask, 0, TIME_CYCLE);
    }
    public void Update_Timer() {
        final DecimalFormat df = new DecimalFormat("#,###");
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                Date d = new Date(System.currentTimeMillis());
                if(d.getTime() - sDate.getTime() < 0) {
                    TvcurrentMoney.setText("0");
                }
                else if(d.getTime() > eDate.getTime()) {
                    TvcurrentMoney.setText(df.format(dailywage));
                    nowmoney = dailywage;
                }
                else {
                    worktimenow = (d.getTime() - sDate.getTime()) / TIME_CYCLE;
                    nowmoney = secwage * worktimenow;
                    String str = "";
                    str = df.format(Double.parseDouble(nowmoney.toString().replaceAll(",", "")));
                    TvcurrentMoney.setText(str);
                }
            }
        };
        mHandler.post(updater);
    }
    //그래프 그리기용 함수
    public void Graph_Timer() {
        final DecimalFormat df = new DecimalFormat("00.00");
        final Date d = new Date(System.currentTimeMillis());
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                //화면의 해상도(높이) 값 가져오기
                int deviceHeight = getResources().getDisplayMetrics().heightPixels;
                //퍼센트당 높이 구하기
                Double percentHeight = deviceHeight / 100.0;
                //현재 급여 퍼센트((현재 급여/일일 급여)*100)
                Double PerOfCost = (nowmoney / dailywage)*100;
                //그래프 높이 지정
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) graph.getLayoutParams();


                //지정 조건
                if((nowmoney < dailywage) && (0 < nowmoney)) {
                    Tvpercent.setText(df.format(PerOfCost)+"%");
                    lp.height = Integer.parseInt(String.valueOf(Math.round(percentHeight * PerOfCost)));
                    graph.setLayoutParams(lp);
                }
                else if(nowmoney == 0) {
                    Tvpercent.setText("00.00%");
                    lp.height = 0;
                    graph.setLayoutParams(lp);
                }
                else {
                    Tvpercent.setText("100.00%");
                    lp.height = deviceHeight;
                    graph.setLayoutParams(lp);
                }
            }
        };
        mHandler.post(updater);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //String을 Date폼으로 바꾸는 메소드
    public Date TransFormStringtoDate(String s) {
        Date date = new Date();
        try {
            date = dateFormat.parse(s);
        }
        catch(Exception err) {

        }
        return date;
    }

    //입력받는 id 값에 따라 Date를 원하는 String 폼으로 변경하는 메소드
    public String TransFormDatetoString(Date d, int id) {
        String str="";

        switch (id) {
            case TRANS_TIME:
                try {
                    str = timeFormat.format(d);
                }
                catch(Exception err) {

                }
                break;
            case TRANS_YMD:
                try {
                    str = yMdFormat.format(d);
                }
                catch(Exception err) {

                }
                break;
            case TRANS_DATE:
                try {
                    str = dateFormat.format(d);
                }
                catch(Exception err) {

                }
                break;
        }
        return str;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        //메인 화면에서 뒤로가기 못누르게 제어
        //나중에 종료를 여부를 묻는 코드 추가 해야함
     }
}
