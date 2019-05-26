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
    ImageView info, leftPoint, rightPoint, gameImage;
    TextView levelTxt, GamePlayLevel;
    int levelIndex;
    String levelStrs[] = {"쉬움", "보통", "어려움"};
    String levelStrResult=levelStrs[0];
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu4);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.game_custom_appbar);
        TextView t = findViewById(R.id.gameactionbar_text);

        ImageView back = findViewById(R.id.appBackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gameImage = findViewById(R.id.GamePreimg);
        levelTxt = findViewById(R.id.menu4_level_text);
        info = findViewById(R.id.menu4_info_img);
        leftPoint = findViewById(R.id.leftPoint);
        rightPoint = findViewById(R.id.rightPoint);
        GamePlayLevel = findViewById(R.id.game_levelTxt);
        startBtn = findViewById(R.id.startGameBtn);

        leftPoint.setOnClickListener(this);
        rightPoint.setOnClickListener(this);
        startBtn.setOnClickListener(this);

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
                levelTxt.setTextSize(30);
                break;
            case "small":
                t.setTextSize(25);
                levelTxt.setTextSize(20);

                break;
            default:
                t.setTextSize(30);
                levelTxt.setTextSize(25);

                break;
        }

    }// onCreate


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rightPoint:
                if(levelIndex == 2){
                    levelIndex = -1;
                }
                levelIndex++;
                levelStrResult = levelStrs[levelIndex];
                GamePlayLevel.setText(levelStrResult);
                setImage(levelIndex);
                break;
            case R.id.leftPoint:
                if(levelIndex == 0){
                    levelIndex = 3;
                }
                levelIndex--;
                levelStrResult = levelStrs[levelIndex];
                GamePlayLevel.setText(levelStrResult);
                setImage(levelIndex);
                break;
            case R.id.startGameBtn:
                Intent i = new Intent(Menu4Activity.this, GamePlayActivity.class);
                i.putExtra("level", levelStrResult);
                startActivity(i);
                break;
        }

    }// onClick

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
    }//Dialog
    public void setImage(int index){
        switch (index){
            case 0:gameImage.setImageDrawable(getDrawable(R.drawable.game1)); break;
            case 1:gameImage.setImageDrawable(getDrawable(R.drawable.game2)); break;
            case 2:gameImage.setImageDrawable(getDrawable(R.drawable.game3)); break;
        }

    }


} // class