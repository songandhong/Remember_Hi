package s2017s40.kr.hs.mirim.remember_hi;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//game 액티비티
public class Menu4Activity extends AppCompatActivity implements View.OnClickListener{
    Button levelHigh, levelMiddlw, levelLow;
    String level;
    ImageView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu4);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        TextView t =findViewById(R.id.actionbar_text);
        t.setText("게임하기");


        info = findViewById(R.id.infoGame);
        levelHigh = findViewById(R.id.levelHigh);
        levelMiddlw = findViewById(R.id.levelMiddle);
        levelLow = findViewById(R.id.levelLow);
        levelLow.setOnClickListener(this);
        levelMiddlw.setOnClickListener(this);
        levelHigh.setOnClickListener(this);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });


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

    public void Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("게임 설명");
        builder.setMessage("작은 숫자부터 차례로 터치해보세요. \n 정답이라면 숫자에 색이 칠해집니다.");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }
}