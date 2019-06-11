package s2017s40.kr.hs.mirim.remember_hi.service;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.JobService;

public class NotificationJobFireBaseService extends JobService {

    @Override
    public boolean onStartJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
        Log.d("NotificationJobService", "onStartJob");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /**
         * Intent 플래그
         *    FLAG_ONE_SHOT : 한번만 사용하고 다음에 이 PendingIntent가 불려지면 Fail을 함
         *    FLAG_NO_CREATE : PendingIntent를 생성하지 않음. PendingIntent가 실행중인것을 체크를 함
         *    FLAG_CANCEL_CURRENT : 실행중인 PendingIntent가 있다면 기존 인텐트를 취소하고 새로만듬
         *    FLAG_UPDATE_CURRENT : 실행중인 PendingIntent가 있다면  Extra Data만 교체함
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*60*60*24, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*60*60*24, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*60*60*24, pendingIntent);
        }
        return false;
    }

    @Override
    public boolean onStopJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}
