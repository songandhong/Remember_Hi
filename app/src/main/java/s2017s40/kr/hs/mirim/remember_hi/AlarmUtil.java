package s2017s40.kr.hs.mirim.remember_hi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import s2017s40.kr.hs.mirim.remember_hi.BroadcastD;

public class AlarmUtil {
    private static final long FIVE_TO_HOUR = 1000 * 60 * 5; // 5분

    // 알람 추가 메소드
    public static void setAlarm(Context context, Calendar cal, int requestCode){
        AlarmManager fiveToHourAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent fiveIntent = new Intent(context, BroadcastD.class);

        // FLAG_CANCEL_CURRENT : 이전에 생성한 PendingIntent 는 취소하고 새롭게 만든다
        PendingIntent fiveSender = PendingIntent.getBroadcast(context, requestCode, fiveIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long localTime = System.currentTimeMillis();
        long targetFiveTime = cal.getTimeInMillis();

        // AlarmManager.INTERVAL_DAY : 24시간
        if(localTime > targetFiveTime){
            targetFiveTime += AlarmManager.INTERVAL_DAY;
        }

        if((targetFiveTime - localTime) < FIVE_TO_HOUR){
            targetFiveTime += AlarmManager.INTERVAL_DAY;
        }

        fiveToHourAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, (targetFiveTime-FIVE_TO_HOUR), AlarmManager.INTERVAL_DAY, fiveSender);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd kk:mm:ss");
        String setTargetFiveTime = format.format(new Date(targetFiveTime-FIVE_TO_HOUR));

        Log.d("NotiTEST", "FiveToHour : " + setTargetFiveTime);
    }

    //알람 해제 메소드
    public static void releaseAlarm(Context context, int requestCode){
        AlarmManager fiveToHourAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent fiveIntent = new Intent(context, BroadcastD.class);

        PendingIntent fiveSender = PendingIntent.getBroadcast(context, requestCode, fiveIntent, 0);

        fiveToHourAlarmManager.cancel(fiveSender);

        Log.d("NotiTEST", "AlarmUtil Canel");
    }
}
