package s2017s40.kr.hs.mirim.remember_hi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDiaryActivity extends AppCompatActivity {
    TextView Title, contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);

        Title = findViewById(R.id.viewDiary_dateTitle_textView);
        contents = findViewById(R.id.viewDiary_contents_textView);

    }
}
