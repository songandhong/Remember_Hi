package s2017s40.kr.hs.mirim.remember_hi.Adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.remember_hi.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private ClickCallback callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView)view.findViewById(R.id.item_main_list_text);
            mImageView = view.findViewById(R.id.item_main_image_image);
        }
    }
    public MainAdapter(ArrayList<String> myDataset, ClickCallback clickCallback) {
        mDataset = myDataset;
        this.callback = clickCallback;
    }
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_main, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset.get(position));
        switch (position){
            case 0: holder.mImageView.setImageResource(R.drawable.icon3); break;
            case 1: holder.mImageView.setImageResource(R.drawable.icon4); break;
            case 2: holder.mImageView.setImageResource(R.drawable.icon1); break;
            case 3: holder.mImageView.setImageResource(R.drawable.icon2); break;
        }
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


