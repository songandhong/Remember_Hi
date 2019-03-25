package s2017s40.kr.hs.mirim.remember_hi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import s2017s40.kr.hs.mirim.remember_hi.DTO.DiaryDTO;

public class WriteDiaryActivity extends AppCompatActivity {
    Spinner weatherSpinner, emotionSpinner;
    EditText writeDiaryEdit;
    Button writeBtn;
    TextView yearTitle, monthTitle, dateTitle;
    String nowTimeStr;

    //파이어베이스 연결 위한 준비
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        //일기 맨 상단의 제목
        yearTitle = findViewById(R.id.writeDiary_Year_text);
        monthTitle = findViewById(R.id.writeDiary_Month_text);
        dateTitle = findViewById(R.id.writeDiary_Date_text);

        //글쓰기 버튼
        writeBtn = findViewById(R.id.writeDiary_write_btn);

        //일기 입력하는 EditText
        writeDiaryEdit = findViewById(R.id.writeDiary_content_edit);

        //스피너
        weatherSpinner = findViewById(R.id.writeDiary_weather_spinner);
        emotionSpinner = findViewById(R.id.writeDiary_emotion_spinner);

        long nowTime = System.currentTimeMillis();

        Date date = new Date(nowTime);
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
        nowTimeStr = formatTime.format(date);

        // 일단 날짜 형식 "yyyy-MM-dd로 줄게,,
        // 타입이 스트링이길래  ㅜㅜ ㅜ 형식이나 타입 바꿔야하면 얘기해조

        yearTitle.setText(nowTimeStr.substring(0,4) + "년 ");
        monthTitle.setText(nowTimeStr.substring(5,7) + "월 ");
        dateTitle.setText(nowTimeStr.substring(nowTimeStr.length()-2, nowTimeStr.length())+ "일");

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //글쓰기 버튼을 누르면 스피너의 값을
                //가져와서 String에 저장
                String weatherStr = "오늘의 날씨는 " + weatherSpinner.getSelectedItem().toString();
                String emotionStr = "내 기분은 " + emotionSpinner.getSelectedItem().toString();
                String editContents = writeDiaryEdit.getText().toString();

                DiaryDTO drDto = new DiaryDTO(editContents, nowTimeStr, emotionStr,
                        "key?", "key2", "key3", weatherStr);

                //drDto 요거 디비에 보내면 됨

                finish();

            }
        });
    }
}
