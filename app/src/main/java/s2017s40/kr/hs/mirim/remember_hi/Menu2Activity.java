package s2017s40.kr.hs.mirim.remember_hi;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import s2017s40.kr.hs.mirim.remember_hi.DTO.DiaryDTO;
import s2017s40.kr.hs.mirim.remember_hi.DTO.MissionDTO;

//문자전송 액티비팉
public class Menu2Activity extends AppCompatActivity {
    Button buttonSendDiary, buttonSendMission;
    TextView textPhoneNo, textViewPhoneNum, textViewSMS;

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
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        TextView t =findViewById(R.id.actionbar_text);
        t.setText("문자 전송하기");

        textViewSMS = findViewById(R.id.menu2_btn_title_text);
        buttonSendDiary = (Button) findViewById(R.id.menu2_diary_btn);
        buttonSendMission = (Button) findViewById(R.id.menu2_mission_btn);
        textPhoneNo =  findViewById(R.id.menu2_phone_num_text);
        textViewPhoneNum = findViewById(R.id.menu2_phone_title_text);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);

        //DB연동
        myRef.child("User").child(Number).child("info/phoneNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PhoneSms = dataSnapshot.getValue().toString();
                textPhoneNo.setText(PhoneSms);
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
                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                        DiaryDTO diaryDTO = fileSnapshot.getValue(DiaryDTO.class);
                        if(nowTimeStr.equals(diaryDTO.getDiaryDate())){
                            DiarySms = diaryDTO.getDiaryContent();
                        }
                    }
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
                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                        MissionDTO missionDTO = fileSnapshot.getValue(MissionDTO.class);
                        String comple = "";
                        if(missionDTO.getMissionComple()){
                            comple = "완료";
                        }else {
                            comple = "미 완료";
                        }
                        MissionSms += missionDTO.getMissionTitle() + "의 미션을" + comple + "하셨습니다";
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        //다이어리 보내기 버튼 이벤트
        buttonSendDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        //미션 보내기 버튼 이벤트
        buttonSendMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        //글씨 크기 변동
        pref = getSharedPreferences("pref", MODE_PRIVATE);

        switch (pref.getString("textsize", "")){
            case "big":
                t.setTextSize(35);
                textViewSMS.setTextSize(30);
                textPhoneNo.setTextSize(25);
                textViewPhoneNum.setTextSize(30);
                buttonSendDiary.setTextSize(23);
                buttonSendMission.setTextSize(23);
                break;
            case "small":
                t.setTextSize(25);
                textViewSMS.setTextSize(20);
                textPhoneNo.setTextSize(15);
                textViewPhoneNum.setTextSize(20);
                buttonSendDiary.setTextSize(13);
                buttonSendMission.setTextSize(13);
                break;
            default:
                t.setTextSize(30);
                textViewSMS.setTextSize(25);
                textPhoneNo.setTextSize(20);
                textViewPhoneNum.setTextSize(25);
                buttonSendDiary.setTextSize(18);
                buttonSendMission.setTextSize(18);
                break;
        }


    }
    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify()
    {
        if (ActivityCompat.checkSelfPermission(Menu2Activity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Menu2Activity.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.SEND_SMS},1);
        } else {
        }
    }
}
