package s2017s40.kr.hs.mirim.remember_hi;

import android.app.ActionBar;
import android.app.Activity;
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
    TextView yearTitle, monthTitle, dateTitle, titleText_wirte, writeDiary_weather_text, writeDiary_emotion_text;
    String nowTimeStr;
    ToggleButton keyword1, keyword2, keyword3;
    int randomNum[];

    //파이어베이스 연결 위한 준비
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    String Number = "";

    WordsArray arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        getSupportActionBar().hide();

        randomNum = new int[3];

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
        titleText_wirte = findViewById(R.id.writeDiary_content_edit);

        writeDiary_weather_text = findViewById(R.id.writeDiary_weather_text);
        writeDiary_emotion_text = findViewById(R.id.writeDiary_emotion_text);

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

        arr = new WordsArray();

        for(int i = 0; i< 3; i ++){
            randomNum[i] = (int) (Math.random() * arr.wordArr.size());
            for(int j = 0; j < i; j++){
                if(randomNum[i] == randomNum[j]){
                    i--;
                }
            }
        }

        keyword1.setText(arr.wordArr.get(randomNum[0]));
        keyword2.setText(arr.wordArr.get(randomNum[1]));
        keyword3.setText(arr.wordArr.get(randomNum[2]));

        keyword1.setTextOn(arr.wordArr.get(randomNum[0]));
        keyword1.setTextOff(arr.wordArr.get(randomNum[0]));
        keyword2.setTextOn(arr.wordArr.get(randomNum[1]));
        keyword2.setTextOff(arr.wordArr.get(randomNum[1]));
        keyword3.setTextOn(arr.wordArr.get(randomNum[2]));
        keyword3.setTextOff(arr.wordArr.get(randomNum[2]));

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
                    keyword1Str = "";
                if(!(keyword2.isChecked()))
                    keyword2Str = "";
                if(!(keyword3.isChecked()))
                    keyword3Str = "";
              
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

        SharedPreferences pref;
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        switch (pref.getString("textsize", "")){
            case "big":
                titleText_wirte.setTextSize(35);
                writeDiary_weather_text.setTextSize(30);
                writeDiary_emotion_text.setTextSize(30);

                yearTitle.setTextSize(35);
                monthTitle.setTextSize(35);
                dateTitle.setTextSize(35);
                keyword1.setTextSize(23);
                keyword2.setTextSize(23);
                keyword3.setTextSize(23);
                writeDiaryEdit.setTextSize(25);
                writeBtn.setTextSize(23);
                break;
            case "small":
                titleText_wirte.setTextSize(25);
                writeDiary_weather_text.setTextSize(20);
                writeDiary_emotion_text.setTextSize(20);

                yearTitle.setTextSize(25);
                monthTitle.setTextSize(25);
                dateTitle.setTextSize(25);
                keyword1.setTextSize(13);
                keyword2.setTextSize(13);
                keyword3.setTextSize(13);
                writeDiaryEdit.setTextSize(15);
                writeBtn.setTextSize(13);
                break;
            default:
                titleText_wirte.setTextSize(30);
                writeDiary_weather_text.setTextSize(25);
                writeDiary_emotion_text.setTextSize(25);

                yearTitle.setTextSize(30);
                monthTitle.setTextSize(30);
                dateTitle.setTextSize(30);
                keyword1.setTextSize(18);
                keyword2.setTextSize(18);
                keyword3.setTextSize(18);
                writeDiaryEdit.setTextSize(20);
                writeBtn.setTextSize(18);
                break;
        }



    }
}
