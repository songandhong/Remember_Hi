package s2017s40.kr.hs.mirim.remember_hi;

import android.app.ActionBar;
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
import android.widget.ImageView;
import android.widget.TextView;
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
    Button confirmBtn;
    TimePicker timePicker;

    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    private static int ONE_MINUTE = 5626;

    String Number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout_withback);
        TextView t =findViewById(R.id.actionbar_text);
        t.setText("미션추가하기");
        ImageView back = findViewById(R.id.appBackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        EditTitle = findViewById(R.id.addMission_missionTitle_editText);
        confirmBtn = findViewById(R.id.addMission_confirm_btn);
        timePicker = findViewById(R.id.addMission_time_timepicker);

        //DB연동위한 값
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);
        myRef = database.getInstance().getReference("User/"+Number+"/Mission");

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missionTitle = EditTitle.getText().toString();
                if(missionTitle.equals("")){
                    Toast.makeText(AddMissionActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Hour = timePicker.getCurrentHour(); //timepicker로 선택한 시간
                Minute = timePicker.getCurrentMinute();//timepicker로 선택한 분
                String minuteString = Minute + "";
                String HourString = Hour + "";
                if(Minute < 10){
                    minuteString = "0" + Minute;
                }
                if(Hour < 10){
                    HourString = "0" + Hour;
                }
                String missionHour = HourString + ":" +  minuteString;
                //미션 DB연동
                myRef.child(missionTitle).setValue( new MissionDTO(missionTitle, missionHour,  false));

                //알람 설정
                new AlarmHATT(getApplicationContext()).Alarm(Hour, Minute);
                Toast.makeText(AddMissionActivity.this, "미션이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        SharedPreferences pref;
        pref = getSharedPreferences("pref", MODE_PRIVATE);

        switch (pref.getString("textsize", "")){
            case "big":
               EditTitle.setTextSize(25);
                confirmBtn.setTextSize(23);

                break;
            case "small":
                EditTitle.setTextSize(15);
                confirmBtn.setTextSize(13);

                break;
            default:
                EditTitle.setTextSize(20);
                confirmBtn.setTextSize(18);
                break;
        }
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
