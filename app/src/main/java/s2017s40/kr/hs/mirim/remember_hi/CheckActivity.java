package s2017s40.kr.hs.mirim.remember_hi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
     ListView listview;

    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        

        listview = (ListView) findViewById(R.id.check_list_view);
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

    }
}
