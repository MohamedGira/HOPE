package com.helloworld.uifirs222;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juned on 3/27/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {
    private int mItemWidth2;

    private List<Drawable> list;

    public class MyView extends RecyclerView.ViewHolder {

        public ImageView img2;

        public MyView(View view) {
            super(view);

            img2 = (ImageView) view.findViewById(R.id.textview2);

        }
    }
    public void setItemWidth(int parentWidth) {
        mItemWidth2 = parentWidth / 8;
    }

    public RecyclerViewAdapter(ArrayList<Drawable> horizontalList) {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item2, parent, false);
        itemView.getLayoutParams().height = mItemWidth2;
        itemView.getLayoutParams().width = mItemWidth2;
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        holder.img2.setImageDrawable(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}