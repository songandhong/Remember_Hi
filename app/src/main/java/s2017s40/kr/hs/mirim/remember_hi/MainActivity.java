package s2017s40.kr.hs.mirim.remember_hi;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import s2017s40.kr.hs.mirim.remember_hi.Adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private ArrayList<String> myDataList;
    String Number = "";
    Spinner txtsize;
    TextView linkTxt, item_main_list_text, welcome;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView t;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        t = findViewById(R.id.actionbar_text);

        LinearLayout goto_site = findViewById(R.id.main_site_layout);
        welcome = findViewById(R.id.main_welcome_text);
        linkTxt = findViewById(R.id.main_site_text);
        item_main_list_text = findViewById(R.id.item_main_list_text);

        setTxtsize();

        txtsize = findViewById(R.id.main_textsize_spinner);
        txtsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String p;
                if(txtsize.getItemAtPosition(position).toString().equals("큰텍스트")){
                    p = "big";
                }else if(txtsize.getItemAtPosition(position).toString().equals("작은텍스트")){
                    p = "small";
                }else if(txtsize.getItemAtPosition(position).toString().equals("보통텍스트")){
                    p = "middle";
                }else{
                    return;
                }
                savePreferences(p);
                setTxtsize();
                Toast.makeText(getApplicationContext(), getPreferences(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);

        //DB연동
        myRef.child("User").child(Number).child("info/name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = String.valueOf(dataSnapshot.getValue());
                welcome.setText("나의 이름은 " + a + "입니다.");
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        goto_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.nid.or.kr/main/main.aspx";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

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
                    case 3: intent = new Intent(MainActivity.this, Menu4Activity.class);  break;
                }
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        myDataList.add("일기");
        myDataList.add("문자");
        myDataList.add("미션");
        myDataList.add("게임");
    }

    public void setTxtsize(){
                switch (getPreferences()) {
            case "big":
                t.setTextSize(35);
                welcome.setTextSize(35);
                linkTxt.setTextSize(30);
                break;
            case "small":
                t.setTextSize(25);
                welcome.setTextSize(25);
                linkTxt.setTextSize(20);
                break;
            default:
                t.setTextSize(30);
                welcome.setTextSize(30);
                linkTxt.setTextSize(25);
                break;
        }
    }


    //값 가져오기
    public String getPreferences(){
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("textsize", "");
    }

    // 값 저장하기
    public void savePreferences(String textSize){
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if(!(pref.getString("textsize", "").equals(""))){
            removePreferences();
        }
        editor.putString("textsize", textSize);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    public void removePreferences(){
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        editor.remove("textsize");
        editor.commit();
    }

    private PendingIntent getPendingIntent(Intent intent)
    {
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }


}
