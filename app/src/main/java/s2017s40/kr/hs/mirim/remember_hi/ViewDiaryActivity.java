package s2017s40.kr.hs.mirim.remember_hi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDiaryActivity extends AppCompatActivity {
    TextView Title, contents;

    //파이어베이스 연결 위한 준비
    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();
    String Number = "";
    String Date = "";
    DiaryDTO diaryDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);
        myRef = database.getInstance().getReference("User/"+Number+"/Diary");

        Title = findViewById(R.id.viewDiary_dateTitle_textView);
        contents = findViewById(R.id.viewDiary_contents_textView);

        Intent intent = getIntent();
        Date = intent.getExtras().getString("Date");

        Log.e("date" , Date);

        myRef.child(Date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                diaryDTO =  dataSnapshot.getValue(DiaryDTO.class);
                Title.setText(diaryDTO.getDiaryDate());
                contents.setText("오늘의 날씨는 " + diaryDTO.getDiaryWeather() +
                        "오늘의 기분은 " + diaryDTO.getDiaryFeel()+
                        "오늘의 키워드 3개는 " + diaryDTO.getDiaryKey1() + ", "+ diaryDTO.getDiaryKey2() + ", "+ diaryDTO.getDiaryKey3() + "이고, "
                        + "오늘의 메모는 "+ diaryDTO.getDiaryContent() + "이다.");
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }
}
