package s2017s40.kr.hs.mirim.remember_hi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import s2017s40.kr.hs.mirim.remember_hi.DTO.DiaryDTO;

public class WriteDiaryActivity extends AppCompatActivity {
    Spinner weatherSpinner, emotionSpinner;
    EditText writeDiaryEdit;
    Button writeBtn;
    TextView yearTitle, monthTitle, dateTitle;
    String nowTimeStr;
    ToggleButton keyword1, keyword2, keyword3;

    //파이어베이스 연결 위한 준비
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    String Number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);
        myRef = database.getInstance().getReference("User/"+Number+"/Diary");
      
        //toggleButton
        //isChecked 로 boolean 타입 반환(상태 저장)
        keyword1 = findViewById(R.id.writeDiary_keyword1_toggle);
        keyword2 = findViewById(R.id.writeDiary_keyword2_toggle);
        keyword3 = findViewById(R.id.writeDiary_keyword3_toggle);



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
                String weatherStr = weatherSpinner.getSelectedItem().toString();
                String emotionStr =  emotionSpinner.getSelectedItem().toString();
                String editContents = writeDiaryEdit.getText().toString();
                String keyword1Str = keyword1.getTextOn().toString();
                String keyword2Str = keyword2.getTextOn().toString();
                String keyword3Str = keyword3.getTextOn().toString();
              
                if(!(keyword1.isChecked()))
                    keyword1Str = null;
                if(!(keyword2.isChecked()))
                    keyword2Str = null;
                if(!(keyword3.isChecked()))
                    keyword3Str = null;  
              
                DiaryDTO diarydto = new DiaryDTO(editContents, nowTimeStr, emotionStr, keyword1Str, keyword2Str, keyword3Str, weatherStr);
                myRef.child(nowTimeStr).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                                if(nowTimeStr.equals(fileSnapshot.getValue())) {
                                    Toast.makeText(getApplicationContext(), "이미 오늘의 일기를 작성하셨습니다.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
                myRef.child(nowTimeStr).setValue(diarydto);
                Toast.makeText(getApplicationContext(), nowTimeStr +"일기가 작성 되었습니다.", Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }
}
