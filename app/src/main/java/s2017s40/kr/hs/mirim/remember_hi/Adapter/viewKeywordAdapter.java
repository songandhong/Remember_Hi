package s2017s40.kr.hs.mirim.remember_hi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.remember_hi.R;

public class viewKeywordAdapter extends BaseAdapter {

    ArrayList<String> item = new ArrayList<>();

    public viewKeywordAdapter(){ }
    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.keyword_list_item, null);
        }

        TextView text = convertView.findViewById(R.id.more_keyword_txt);
        text.setText(item.get(position));

        return convertView;
    }

    public void setItem(String item){
       this.item.add(item);
    }
}
