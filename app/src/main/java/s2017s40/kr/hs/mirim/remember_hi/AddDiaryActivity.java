package s2017s40.kr.hs.mirim.remember_hi;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Date;

import s2017s40.kr.hs.mirim.remember_hi.DTO.DiaryDTO;

public class AddDiaryActivity extends AppCompatActivity {
    Spinner weatherSpinner, emotionSpinner;
    EditText writeDiaryEdit;
    Button writeBtn;
    TextView titleText_wirte, writeDiary_weather_text, writeDiary_emotion_text;
    String nowTimeStr;
    ToggleButton keyword1, keyword2, keyword3, keyword4, keyword5, keyword6, keyword7, keyword8,keyword9;
    int randomNum[];
    TextView more_keyword;

    //파이어베이스 연결 위한 준비
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    String Number = "";

    WordsArray arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout_withback);
        TextView t =findViewById(R.id.actionbar_text);
        ImageView back = findViewById(R.id.appBackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        randomNum = new int[9];

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);
        myRef = database.getInstance().getReference("User/"+Number+"/Diary");
      
        //toggleButton
        //isChecked 로 boolean 타입 반환(상태 저장)

        keyword1 = findViewById(R.id.writeDiary_keyword1_toggle);
        keyword2 = findViewById(R.id.writeDiary_keyword2_toggle);
        keyword3 = findViewById(R.id.writeDiary_keyword3_toggle);

        keyword4 = findViewById(R.id.writeDiary_keyword4_toggle);
        keyword5 = findViewById(R.id.writeDiary_keyword5_toggle);
        keyword6 = findViewById(R.id.writeDiary_keyword6_toggle);

        keyword7 = findViewById(R.id.writeDiary_keyword7_toggle);
        keyword8 = findViewById(R.id.writeDiary_keyword8_toggle);
        keyword9 = findViewById(R.id.writeDiary_keyword9_toggle);

        more_keyword = findViewById(R.id.more_keyword);

        //일기 맨 상단의 제목
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

        String s = nowTimeStr.substring(5,7) + "월 " +  nowTimeStr.substring(nowTimeStr.length()-2, nowTimeStr.length())+ "일" + "의 일기";

        t.setText(s); // 액션바에 setText


        arr = new WordsArray();

        for(int i = 0; i< 9; i ++){
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
        keyword4.setText(arr.wordArr.get(randomNum[3]));
        keyword5.setText(arr.wordArr.get(randomNum[4]));
        keyword6.setText(arr.wordArr.get(randomNum[5]));
        keyword7.setText(arr.wordArr.get(randomNum[6]));
        keyword8.setText(arr.wordArr.get(randomNum[7]));
        keyword9.setText(arr.wordArr.get(randomNum[8]));

        keyword1.setTextOn(arr.wordArr.get(randomNum[0]));
        keyword1.setTextOff(arr.wordArr.get(randomNum[0]));
        keyword2.setTextOn(arr.wordArr.get(randomNum[1]));
        keyword2.setTextOff(arr.wordArr.get(randomNum[1]));
        keyword3.setTextOn(arr.wordArr.get(randomNum[2]));
        keyword3.setTextOff(arr.wordArr.get(randomNum[2]));

        keyword4.setTextOn(arr.wordArr.get(randomNum[3]));
        keyword4.setTextOff(arr.wordArr.get(randomNum[3]));
        keyword5.setTextOn(arr.wordArr.get(randomNum[4]));
        keyword5.setTextOff(arr.wordArr.get(randomNum[4]));
        keyword6.setTextOn(arr.wordArr.get(randomNum[5]));
        keyword6.setTextOff(arr.wordArr.get(randomNum[5]));

        keyword7.setTextOn(arr.wordArr.get(randomNum[6]));
        keyword7.setTextOff(arr.wordArr.get(randomNum[6]));
        keyword8.setTextOn(arr.wordArr.get(randomNum[7]));
        keyword8.setTextOff(arr.wordArr.get(randomNum[7]));
        keyword9.setTextOn(arr.wordArr.get(randomNum[8]));
        keyword9.setTextOff(arr.wordArr.get(randomNum[8]));


        more_keyword.setOnClickListener(new View.OnClickListener() { // 키워드 더 보기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDiaryActivity.this, MoreKeywordActivity.class);
                startActivityForResult(intent, 0);
            }
        });

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

                String keyword4Str = keyword4.getTextOn().toString();
                String keyword5Str = keyword5.getTextOn().toString();
                String keyword6Str = keyword6.getTextOn().toString();

                String keyword7Str = keyword7.getTextOn().toString();
                String keyword8Str = keyword8.getTextOn().toString();
                String keyword9Str = keyword9.getTextOn().toString();
              
                if(!(keyword1.isChecked()))
                    keyword1Str = "";
                if(!(keyword2.isChecked()))
                    keyword2Str = "";
                if(!(keyword3.isChecked()))
                    keyword3Str = "";

                if(!(keyword4.isChecked()))
                    keyword4Str = "";
                if(!(keyword5.isChecked()))
                    keyword5Str = "";
                if(!(keyword6.isChecked()))
                    keyword6Str = "";

                if(!(keyword4.isChecked()))
                    keyword7Str = "";
                if(!(keyword5.isChecked()))
                    keyword8Str = "";
                if(!(keyword6.isChecked()))
                    keyword9Str = "";


                // 9까지 추가해야함,,,
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

                keyword1.setTextSize(23);
                keyword2.setTextSize(23);
                keyword3.setTextSize(23);
                keyword4.setTextSize(23);
                keyword5.setTextSize(23);
                keyword6.setTextSize(23);
                keyword7.setTextSize(23);
                keyword8.setTextSize(23);
                keyword9.setTextSize(23);
                writeDiaryEdit.setTextSize(25);
                writeBtn.setTextSize(23);
                break;
            case "small":
                titleText_wirte.setTextSize(25);
                writeDiary_weather_text.setTextSize(20);
                writeDiary_emotion_text.setTextSize(20);

                keyword1.setTextSize(13);
                keyword2.setTextSize(13);
                keyword3.setTextSize(13);
                keyword4.setTextSize(13);
                keyword5.setTextSize(13);
                keyword6.setTextSize(13);
                keyword7.setTextSize(13);
                keyword8.setTextSize(13);
                keyword9.setTextSize(13);
                writeDiaryEdit.setTextSize(15);
                writeBtn.setTextSize(13);
                break;
            default:
                titleText_wirte.setTextSize(30);
                writeDiary_weather_text.setTextSize(25);
                writeDiary_emotion_text.setTextSize(25);

                keyword1.setTextSize(18);
                keyword2.setTextSize(18);
                keyword3.setTextSize(18);
                keyword4.setTextSize(18);
                keyword5.setTextSize(18);
                keyword6.setTextSize(18);
                keyword7.setTextSize(18);
                keyword8.setTextSize(18);
                keyword9.setTextSize(18);
                writeDiaryEdit.setTextSize(20);
                writeBtn.setTextSize(18);
                break;
        }

    } // onCreate

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(reqCode == 0){
                String set = writeDiaryEdit.getText() + data.getStringExtra("moreKeyword");
                writeDiaryEdit.setText(set);
            }else{
                Toast.makeText(getApplicationContext(), "오류가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
