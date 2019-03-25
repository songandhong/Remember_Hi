package s2017s40.kr.hs.mirim.remember_hi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    String Number = "";
    ArrayList<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);
        myRef = database.getInstance().getReference("User/"+Number+"/Diary");

        writeBtn = findViewById(R.id.menu1_recycler_write_btn);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu1Activity.this, WriteDiaryActivity.class);
                startActivity(intent);
            }
        });

        //recyclerview 연결 및 adapter 연동
        mRecyclerView = (RecyclerView) findViewById(R.id.menu1_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //리스트뷰 생성
        myDataList = new ArrayList<>();
        //어탭터
        mAdapter = new MainAdapter(myDataList, new MainAdapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
                //클릭 이벤트 처리 -> DB연동할꺼!!~
                Intent intent = new Intent(Menu1Activity.this, ViewDiaryActivity.class);
                intent.putExtra("Date",arr.get(position));
                startActivity(intent);
            }
        });

        //DB연동
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    arr = new ArrayList<>();
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
            }
        });
        //어댑터와 recyclerview 연결
        mRecyclerView.setAdapter(mAdapter);
    }
}
