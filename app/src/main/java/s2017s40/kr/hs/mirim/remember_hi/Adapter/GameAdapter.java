package s2017s40.kr.hs.mirim.remember_hi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.remember_hi.R;

public class GameAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Integer> list = new ArrayList<Integer>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_grid_game_menu4, null);
        }

        TextView textView = convertView.findViewById(R.id.GameTxt);
        textView.setText(list.get(position).toString());
        return convertView;
    }

    public void addItem(Integer i){
        list.add(i);
    }
    public int get(int position){
        return list.get(position);
    }

}

