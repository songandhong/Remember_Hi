package s2017s40.kr.hs.mirim.remember_hi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class writeDiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        Spinner weatherSpinner = findViewById(R.id.writeDiary_weather_spinner);

        String weatherStr = weatherSpinner.getSelectedItem().toString();

    }

    }
