package s2017s40.kr.hs.mirim.remember_hi;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import s2017s40.kr.hs.mirim.remember_hi.DTO.UserDTO;

public class CheckActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView listview;
    Button nextBtn;
    int checkCount; // count 개수

    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        // 액션바 설정 시작
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        TextView t = findViewById(R.id.actionbar_text);
        t.setText("치매 정도 확인");
        // 액션바 설정 끝


        nextBtn = findViewById(R.id.check_next_btn);
        listview = findViewById(R.id.check_list_view);
        items = new ArrayList<String>() ;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items) ;

        listview.setAdapter(adapter);

        myRef.child("checklist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    items.add(fileSnapshot.getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB연동
                //myRef.child("User").child("check").setValue();
                checkCount = listview.getCheckedItemCount();
                Toast.makeText(getApplicationContext(), checkCount+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
