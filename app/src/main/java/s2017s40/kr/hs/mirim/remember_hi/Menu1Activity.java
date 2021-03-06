package s2017s40.kr.hs.mirim.remember_hi;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.remember_hi.Adapter.Menu1Adapter;
import s2017s40.kr.hs.mirim.remember_hi.DTO.DiaryDTO;


//일기 목록 액티비티
public class Menu1Activity extends AppCompatActivity {
    //recyclerview 연결 위한 준비
    private RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> myDataList;

    //버튼 연결 위한 준비
    Button writeBtn;

    //파이어베이스 연결 위한 준비
    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();
    SharedPreferences pref;

    String Number = "";
    ArrayList<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

          pref = getSharedPreferences("pref", MODE_PRIVATE);
//        Toast.makeText( getApplicationContext(), pref.getString("textsize", ""), Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout_withback);
        TextView t =findViewById(R.id.actionbar_text);
        t.setText("일기 목록");
        ImageView back = findViewById(R.id.appBackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        arr = new ArrayList<>();

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);
        myRef = database.getInstance().getReference("User/"+Number+"/Diary");

        writeBtn = findViewById(R.id.menu1_recycler_write_btn);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu1Activity.this, AddDiaryActivity.class);
                startActivity(intent);
            }
        });

        switch (pref.getString("textsize", "")) {
            case "big":
                t.setTextSize(35);
                writeBtn.setTextSize(23);
                break;
            case "small":
                t.setTextSize(25);
                writeBtn.setTextSize(13);
                break;
            default:
                t.setTextSize(30);
                writeBtn.setTextSize(18);
                break;
        }

        //recyclerview 연결 및 adapter 연동
        mRecyclerView = (RecyclerView) findViewById(R.id.menu1_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //리스트뷰 생성
        myDataList = new ArrayList<>();

        //DB연동
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    arr.clear();
                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                        //아이템 추가
                        DiaryDTO diaryDTO = fileSnapshot.getValue(DiaryDTO.class);
                        arr.add(diaryDTO.getDiaryDate());
                        myDataList.add(diaryDTO.getDiaryDate());
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                //ㅇㅇ
            }
        });

        //어탭터
        mAdapter = new Menu1Adapter(myDataList, new Menu1Adapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
                Log.e("arr", String.valueOf(arr.get(position)));
                Intent intent = new Intent(Menu1Activity.this, ViewDiaryActivity.class);
                intent.putExtra("Date",arr.get(position));
                startActivity(intent);
            }
        });
        //어댑터와 recyclerview 연결
        mRecyclerView.setAdapter(mAdapter);

    }

}
