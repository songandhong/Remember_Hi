package s2017s40.kr.hs.mirim.remember_hi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu4Activity extends AppCompatActivity implements View.OnClickListener{
    Button levelHigh, levelMiddlw, levelLow;
    String level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu4);

        levelHigh = findViewById(R.id.levelHigh);
        levelMiddlw = findViewById(R.id.levelMiddle);
        levelLow = findViewById(R.id.levelLow);
        levelLow.setOnClickListener(this);
        levelMiddlw.setOnClickListener(this);
        levelHigh.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.levelHigh: level = "h"; break;
            case R.id.levelMiddle: level = "m"; break;
            case R.id.levelLow :level = "l"; break;
        }

        Intent i = new Intent(Menu4Activity.this, GamePlayActivity.class);
        i.putExtra("level", level);
        startActivity(i);
        finish();
    }
}