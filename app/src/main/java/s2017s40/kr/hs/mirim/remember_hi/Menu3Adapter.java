package s2017s40.kr.hs.mirim.remember_hi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Menu3Adapter extends RecyclerView.Adapter<Menu3Adapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private Menu3Adapter.ClickCallback callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView)view.findViewById(R.id.item_menu3_list_text);
        }
    }
    public Menu3Adapter(ArrayList<String> myDataset, Menu3Adapter.ClickCallback clickCallback) {
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
    public void onBindViewHolder(Menu3Adapter.ViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset.get(position));
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