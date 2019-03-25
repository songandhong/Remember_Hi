package s2017s40.kr.hs.mirim.remember_hi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMissionActivity extends AppCompatActivity {
    String missionTitle;
    EditText EditTitle;
    Button confirmBtn, cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);

        EditTitle = findViewById(R.id.addMission_missionTitle_editText);
        confirmBtn = findViewById(R.id.addMission_confirm_btn);
        cancelBtn = findViewById(R.id.addMission_cancel_btn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missionTitle = EditTitle.getText().toString();

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
