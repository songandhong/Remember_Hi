package s2017s40.kr.hs.mirim.remember_hi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

import s2017s40.kr.hs.mirim.remember_hi.Adapter.MainAdapter;

public class MainActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private ArrayList<String> myDataList;
    String Number = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataList = new ArrayList<>();
        mAdapter = new MainAdapter(myDataList, new MainAdapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                switch (position){
                    case 0: intent = new Intent(MainActivity.this, Menu1Activity.class);  break;
                    case 1: intent = new Intent(MainActivity.this, Menu2Activity.class);  break;
                    case 2: intent = new Intent(MainActivity.this, Menu3Activity.class);  break;
                    case 3:  break;
                }
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        myDataList.add("일기");
        myDataList.add("문자");
        myDataList.add("미션");
        myDataList.add("치매");

    }

}
