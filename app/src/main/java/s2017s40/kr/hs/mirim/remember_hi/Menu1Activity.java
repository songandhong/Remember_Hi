package s2017s40.kr.hs.mirim.remember_hi;

import android.content.Intent;
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

import s2017s40.kr.hs.mirim.remember_hi.DTO.DiaryDTO;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

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
            }
        });
        //어댑터와 recyclerview 연결
        mRecyclerView.setAdapter(mAdapter);
        //DB연동
        myRef.child("CheckList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    //아이템 추가
                    myDataList.add(fileSnapshot.getValue(String.class));
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
    private void writeNewPost() {
        DiaryDTO diaryDTO = new DiaryDTO();
        myRef.setValue(diaryDTO);
    }
}
