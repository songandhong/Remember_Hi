package s2017s40.kr.hs.mirim.remember_hi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import s2017s40.kr.hs.mirim.remember_hi.Adapter.GameAdapter;

public class GamePlayActivity extends AppCompatActivity {
    GridView grid;
    GameAdapter adapter;
    int Flag=0;
    String level;
    int levelValue, values = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        getSupportActionBar().hide();

        HashSet<Integer> lists = new HashSet<>();
        final int value[];
        int ShuffleArr[];// 셔플시 사용

        Intent intent = getIntent();
        level = intent.getStringExtra("level");

        switch (level){
            case"h":levelValue=1000; values=8; break;
            case"m":levelValue=500;values=6; break;
            case"l":levelValue=10; values=4;break;
        }

        value = new int[values];
        ShuffleArr = new int[values];

        grid = findViewById(R.id.Board);
        adapter = new GameAdapter();

        while(lists.size() < values){
            lists.add((int)(Math.random()*levelValue) + 1);
        }

        List valueList = new ArrayList(lists);
        Collections.sort(valueList);
        for(int v =0; v < values; v++){
            value[v] = (Integer) valueList.get(v);
            ShuffleArr[v] = value[v];
        }

       shuffling(ShuffleArr);
        for(int j = 0; j < values; j++){
            adapter.addItem(ShuffleArr[j]);
        }
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(value[Flag] == adapter.get(position)){
                    TextView txt = view.findViewById(R.id.GameTxt);

                    txt.setBackgroundColor(getResources().getColor(R.color.mainDark));
                    Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_SHORT).show();
                    Flag++;
                    if(Flag == values){
                        Dialog();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "정답이 아닙니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }//onCreate

    public void shuffling(int arr[]){

        int w=0;
        int r;
        int temp;
        while(w < arr.length){
            r = (int)(Math.random()*arr.length);
            //만약 두 인덱스가 다르다면
            if(w!=r){
                //swap으로 값을 바꾼다.
                temp = arr[w];
                arr[w] = arr[r];
                arr[r] = temp;
                w++;
            }
        }
    } // shuffle


    public void Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("게임 끝!");
        builder.setMessage("숫자 맞추기를 성공하셨어요!");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.show();
    }


}
