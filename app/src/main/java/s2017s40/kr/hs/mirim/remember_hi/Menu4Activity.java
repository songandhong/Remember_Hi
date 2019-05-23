package s2017s40.kr.hs.mirim.remember_hi;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
    TextView levelTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu4);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.game_custom_appbar);

        TextView t = findViewById(R.id.gameactionbar_text);

        levelTxt = findViewById(R.id.menu4_level_text);
        info = findViewById(R.id.menu4_info_img);
        levelHigh = findViewById(R.id.menu4_levelHigh_btn);
        levelMiddlw = findViewById(R.id.menu4_levelMiddle_btn);
        levelLow = findViewById(R.id.menu4_levelLow_btn);
        levelLow.setOnClickListener(this);
        levelMiddlw.setOnClickListener(this);
        levelHigh.setOnClickListener(this);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });

        SharedPreferences pref;
        pref = getSharedPreferences("pref", MODE_PRIVATE);

        switch (pref.getString("textsize", "")){
            case "big":
                t.setTextSize(35);
                levelHigh.setTextSize(23);
                levelLow.setTextSize(23);
                levelMiddlw.setTextSize(23);
                levelTxt.setTextSize(30);

                break;
            case "small":
                t.setTextSize(25);
                levelHigh.setTextSize(13);
                levelLow.setTextSize(13);
                levelMiddlw.setTextSize(13);
                levelTxt.setTextSize(20);

                break;
            default:
                t.setTextSize(30);
                levelHigh.setTextSize(18);
                levelLow.setTextSize(18);
                levelMiddlw.setTextSize(18);
                levelTxt.setTextSize(25);

                break;
        }



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu4_levelHigh_btn: level = "h"; break;
            case R.id.menu4_levelMiddle_btn: level = "m"; break;
            case R.id.menu4_levelLow_btn :level = "l"; break;
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