package s2017s40.kr.hs.mirim.remember_hi;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import s2017s40.kr.hs.mirim.remember_hi.DTO.MissionDTO;

public class AddMissionActivity extends AppCompatActivity {
    String missionTitle;
    int Hour, Minute;
    EditText EditTitle;
    Button confirmBtn, cancelBtn;
    TimePicker timePicker;

    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    private static int ONE_MINUTE = 5626;

    String Number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);
        getSupportActionBar().hide();


        EditTitle = findViewById(R.id.addMission_missionTitle_editText);
        confirmBtn = findViewById(R.id.addMission_confirm_btn);
        cancelBtn = findViewById(R.id.addMission_cancel_btn);
        timePicker = findViewById(R.id.addMission_time_timepicker);

        //DB연동위한 값
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);
        myRef = database.getInstance().getReference("User/"+Number+"/Mission");

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missionTitle = EditTitle.getText().toString();
                Hour = timePicker.getCurrentHour(); //timepicker로 선택한 시간
                Minute = timePicker.getCurrentMinute();//timepicker로 선택한 분

                String missionHour = Hour + ":" +  Minute;
                //미션 DB연동
                myRef.child(missionTitle).setValue( new MissionDTO(missionTitle, missionHour,  false));
                //알람 설정
                new AlarmHATT(getApplicationContext()).Alarm(Hour, Minute);
                Toast.makeText(AddMissionActivity.this, "미션이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                //Activity 끝내기
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm(int putH, int putM) {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(AddMissionActivity.this, BroadcastD.class);
            PendingIntent sender = PendingIntent.getBroadcast(AddMissionActivity.this, 0, intent, 0);
            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), putH, putM, 0);
            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }
}
