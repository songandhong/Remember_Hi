package s2017s40.kr.hs.mirim.remember_hi.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import s2017s40.kr.hs.mirim.remember_hi.Menu2Activity;
import s2017s40.kr.hs.mirim.remember_hi.R;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private final static int NOTICATION_ID = 222;
    Context context;
    String PhoneSms = "";
    String DiarySms = "";
    String MissionSms = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        // DB 연동(DB 오류나서 일단 연결하지 않음)
        this.context = context;
        sendDiaryMessage();
        sendMissionMessage();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification_channel_id")
                .setSmallIcon(R.drawable.ic_love_p_64px) //알람 아이콘
                .setContentTitle("메시지 전송 완료")  //알람 제목
                .setContentText("보호자에게 문자 메시지를 전송했어요!") //알람 내용
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); //알람 중요도

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTICATION_ID, builder.build()); //알람 생성
    }

    public void sendDiaryMessage(){
        try {
            if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context.getApplicationContext(), android.Manifest.permission.SEND_SMS )
                    != PackageManager.PERMISSION_GRANTED) {
            }
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(PhoneSms, null, DiarySms, null, null);

            Toast.makeText(context, "오늘의 다이어리 보내기 성공", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(context, "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            Log.e("error", String.valueOf(e));
            e.printStackTrace();
        }
    }

    public void sendMissionMessage(){
        try {
            if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context.getApplicationContext(), android.Manifest.permission.SEND_SMS )
                    != PackageManager.PERMISSION_GRANTED) {
            }
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(PhoneSms, null, MissionSms, null, null);

            Toast.makeText(context, "오늘의 미션 보내기 성공", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(context, "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            Log.e("error", String.valueOf(e));
            e.printStackTrace();
        }

    }


}
