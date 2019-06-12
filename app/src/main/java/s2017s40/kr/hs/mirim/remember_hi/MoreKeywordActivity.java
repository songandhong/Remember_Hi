package s2017s40.kr.hs.mirim.remember_hi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.remember_hi.Adapter.viewKeywordAdapter;


public class MoreKeywordActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_keyword);
        WordsArray array = new WordsArray();

        arr = array.wordArr;

        listView = findViewById(R.id.view_moreKeyword_list);
        viewKeywordAdapter adapter = new viewKeywordAdapter();
        listView.setAdapter(adapter);

        for(int i = 0; i < arr.size(); i++){
            adapter.setItem(arr.get(i));
            adapter.notifyDataSetChanged();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent result = new Intent();
              result.putExtra("moreKeyword", arr.get(position));
              setResult(RESULT_OK, result);
              finish();
          }
      });
    }
}
