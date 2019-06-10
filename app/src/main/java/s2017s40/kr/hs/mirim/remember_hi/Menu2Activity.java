package s2017s40.kr.hs.mirim.remember_hi;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import s2017s40.kr.hs.mirim.remember_hi.DTO.DiaryDTO;
import s2017s40.kr.hs.mirim.remember_hi.DTO.MissionDTO;
import s2017s40.kr.hs.mirim.remember_hi.service.JobSchedulerStart;

//문자전송 액티비팉
public class Menu2Activity extends AppCompatActivity {
    TextView textPhoneNo, textViewPhoneNum, diary_status, mission_status;
    LinearLayout diaryChk, missionChk;
    CheckBox checkAutoMessage;
    boolean diary_send = false, mission_send = false; // 미션을 보낼지 안보낼지 판단하는 boolean 변수

    Button sendBtn;

    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();
    SharedPreferences pref;

    String Number = "";

    String PhoneSms = "";
    String DiarySms = "";
    String MissionSms = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout_withback);
        TextView t =findViewById(R.id.actionbar_text);
        t.setText("문자 전송하기");
        ImageView back = findViewById(R.id.appBackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textPhoneNo =  findViewById(R.id.menu2_phone_num_text);
        textViewPhoneNum = findViewById(R.id.menu2_phone_title_text);
        diary_status = findViewById(R.id.todaydiary_status_menu2);
        mission_status = findViewById(R.id.todaymission_status_menu2);
        sendBtn = findViewById(R.id.send_today_btn);
        checkAutoMessage = findViewById(R.id.auto_message);


        diaryChk = findViewById(R.id.today_diary_check);
        missionChk = findViewById(R.id.today_mission_check);

        diaryChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("온클릭 실행", "to");
                if(diary_send) {
                    diary_send = false;
                    diaryChk.setBackgroundColor(getResources().getColor(R.color.white));
                }
                else {
                    diary_send = true;
                    diaryChk.setBackgroundColor(getResources().getColor(R.color.lightPurple));
                }
            }
        });

        missionChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mission_send) {
                    mission_send = false;
                    missionChk.setBackgroundColor(getResources().getColor(R.color.white));
                }

                else {
                    mission_send = true;
                    missionChk.setBackgroundColor(getResources().getColor(R.color.lightPurple));
                }
            }
        });

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);

        //DB연동
        myRef.child("User").child(Number).child("info/phoneNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try{
                    PhoneSms = dataSnapshot.getValue().toString(); // null pointer
                    textPhoneNo.setText(PhoneSms);

                }catch (Exception e){
                    Log.e("error!", e.getStackTrace().toString());
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

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

        pref = getSharedPreferences("pref", MODE_PRIVATE);

        switch (pref.getString("textsize", "")){
            case "big":
                t.setTextSize(35);
                textPhoneNo.setTextSize(25);
                textViewPhoneNum.setTextSize(30);
                sendBtn.setTextSize(23);
                mission_status.setTextSize(25);
                diary_status.setTextSize(25);
                break;

            case "small":
                t.setTextSize(25);
                textPhoneNo.setTextSize(15);
                textViewPhoneNum.setTextSize(20);
                sendBtn.setTextSize(13);
                mission_status.setTextSize(15);
                diary_status.setTextSize(15);
                break;

            default:
                t.setTextSize(30);
                textPhoneNo.setTextSize(20);
                textViewPhoneNum.setTextSize(25);
                sendBtn.setTextSize(18);
                mission_status.setTextSize(20);
                diary_status.setTextSize(20);
                break;
        }

        sendBtn.setOnClickListener(new View.OnClickListener() { // 문자 보내기 버튼
            @Override
            public void onClick(View v) {
                if(mission_send){ // mission 보내기가 활성화 되어있는 경우
                    sendMissionMessage();
                }
                if(diary_send){ // diary 보내기가 활성화 되어있는 경우
                    sendDiaryMessage();
                }
            }
        });

        Boolean b = pref.getBoolean("autoMessage", false);
        checkAutoMessage.setChecked(b);
        if(checkAutoMessage.isChecked()){
            //자동으로 메시지 전송
            JobSchedulerStart.start(this);
        }

        checkAutoMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoMessageSet(checkAutoMessage.isChecked());
            }
        });



    }//onCreate



    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify()
    {
        if (ActivityCompat.checkSelfPermission(Menu2Activity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Menu2Activity.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.SEND_SMS},1);
        } else {
        }
    }


    public void sendDiaryMessage(){
        try {
            if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(Menu2Activity.this, android.Manifest.permission.SEND_SMS )
                    != PackageManager.PERMISSION_GRANTED) {
                checkVerify();
            }
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(PhoneSms, null, DiarySms, null, null);

            Toast.makeText(getApplicationContext(), "오늘의 다이어리 보내기 성공", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            Log.e("error", String.valueOf(e));
            e.printStackTrace();
        }
    }

    public void sendMissionMessage(){
        try {
            if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(Menu2Activity.this, android.Manifest.permission.SEND_SMS )
                    != PackageManager.PERMISSION_GRANTED) {
                checkVerify();
            }
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(PhoneSms, null, MissionSms, null, null);

            Toast.makeText(getApplicationContext(), "오늘의 미션 보내기 성공", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            Log.e("error", String.valueOf(e));
            e.printStackTrace();
        }

    }

    public void autoMessageSet(Boolean isChecked){
        SharedPreferences.Editor editor = pref.edit();
        if(isChecked){
            editor.putBoolean("autoMessage", true);
        }else{
            editor.putBoolean("autoMessage", false);
        }
        editor.commit();
    }
}
