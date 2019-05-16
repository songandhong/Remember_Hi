package s2017s40.kr.hs.mirim.remember_hi.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import s2017s40.kr.hs.mirim.remember_hi.MissionDTO;
import s2017s40.kr.hs.mirim.remember_hi.R;

public class Menu3Adapter extends RecyclerView.Adapter<Menu3Adapter.ViewHolder> {
    private ArrayList<MissionDTO> mDataset;
    private Menu3Adapter.ClickCallback callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView TitleText, TimeText;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            TitleText = (TextView)view.findViewById(R.id.item_menu3_title_text);
            TimeText = (TextView)view.findViewById(R.id.item_menu3_time_text);
        }
    }
    public Menu3Adapter(ArrayList<MissionDTO> myDataset, Menu3Adapter.ClickCallback clickCallback) {
        mDataset = myDataset;
        this.callback = clickCallback;
    }
    @Override
    public Menu3Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_menu3, parent, false);
        Menu3Adapter.ViewHolder vh = new Menu3Adapter.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final Menu3Adapter.ViewHolder holder, final int position) {
        final MissionDTO model = mDataset.get(position);
        holder.mView.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        holder.TitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setMissionComple(!model.isSelected());
                holder.mView.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                if(!model.isSelected()) {
                    model.setMissionComple(false);
                }
                else if(model.isSelected()){
                    model.setMissionComple(true);
                }
            }
        });
        holder.TitleText.setText(mDataset.get(position).getMissionTitle());
        holder.TimeText.setText(mDataset.get(position).getmissionAlarm());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public interface ClickCallback {
        void onItemClick(int position);
    }
}