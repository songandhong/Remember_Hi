package s2017s40.kr.hs.mirim.remember_hi;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import java.util.Calendar;

import s2017s40.kr.hs.mirim.remember_hi.Adapter.MainAdapter;
import s2017s40.kr.hs.mirim.remember_hi.Adapter.Menu3Adapter;


public class Menu3Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MissionDTO> myDataList;

    Button writeBtn;

    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    String Number = "";
    ArrayList<String> arr;

    private static int ONE_MINUTE = 5626;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3);

        arr = new ArrayList<>();

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);
        myRef = database.getInstance().getReference("User/"+Number+"/Mission");


        writeBtn = findViewById(R.id.menu3_recycler_write_btn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu3Activity.this, AddMissionActivity.class);
                startActivity(intent);
            }
        });

        myRef.child(Number);

        mRecyclerView = (RecyclerView) findViewById(R.id.menu3_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataList = new ArrayList<>();

        //DB연동
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                        //아이템 추가
                        MissionDTO missionDTO = fileSnapshot.getValue(MissionDTO.class);
                        arr.add(missionDTO.getMissionTitle());
                        myDataList.add(missionDTO);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        //어댑터 연결
        mAdapter = new Menu3Adapter(myDataList, new Menu3Adapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
                //클릭 이벤트 처리
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

}
