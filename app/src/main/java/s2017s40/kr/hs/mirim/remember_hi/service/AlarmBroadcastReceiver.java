package s2017s40.kr.hs.mirim.remember_hi.service;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import s2017s40.kr.hs.mirim.remember_hi.R;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    private final static int NOTICATION_ID = 222;
    Context context;
    String Number;
    String PhoneSms = "";
    String DiarySms = "";
    String MissionSms = "";

    @Override
    public void onReceive(Context context, Intent intent) {


        this.context = context;

        firebaseConn();// DB 오류 난 부분 주석처리함
        sendDiaryMessage();
        sendMissionMessage();

        SharedPreferences auto = context.getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);



        //푸쉬알람
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

    public void firebaseConn(){

        //다이어리 DB연동
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    long nowTime = System.currentTimeMillis();
                    Date date = new Date(nowTime);
                    SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
                    String nowTimeStr = formatTime.format(date);
//                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
//                        DiaryDTO diaryDTO = fileSnapshot.getValue(DiaryDTO.class);
//                        if(nowTimeStr.equals(diaryDTO.getDiaryDate())){
//                            DiarySms = diaryDTO.getDiaryContent();
//                        }
//                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        //미션 DB연동
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
//                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
//                        MissionDTO missionDTO = fileSnapshot.getValue(MissionDTO.class);
//                        String comple = "";
//                        if(missionDTO.getMissionComple()){
//                            comple = "완료";
//                        }else {
//                            comple = "미 완료";
//                        }
//                        MissionSms += missionDTO.getMissionTitle() + "의 미션을" + comple + "하셨습니다";
//                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });



    }

}
