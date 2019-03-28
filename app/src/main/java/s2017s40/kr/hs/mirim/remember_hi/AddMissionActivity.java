package s2017s40.kr.hs.mirim.remember_hi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import s2017s40.kr.hs.mirim.remember_hi.DTO.MissionDTO;

public class AddMissionActivity extends AppCompatActivity {
    String missionTitle;
    int Hour, Minute;
    EditText EditTitle;
    Button confirmBtn, cancelBtn;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);

        EditTitle = findViewById(R.id.addMission_missionTitle_editText);
        confirmBtn = findViewById(R.id.addMission_confirm_btn);
        cancelBtn = findViewById(R.id.addMission_cancel_btn);
        timePicker = findViewById(R.id.addMission_time_timepicker);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missionTitle = EditTitle.getText().toString();
                Hour = timePicker.getCurrentHour(); //timepicker로 선택한 시간
                Minute = timePicker.getCurrentMinute();//timepicker로 선택한 분
                String missionHour = Hour + "";
                String missionMinute = Minute + "";

                MissionDTO mDTO = new MissionDTO(missionTitle, missionHour, missionMinute, 0);

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
