package com.helloworld.uifirs222;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyView> {
    private int mItemWidth;
    public String[] mColors = {"#E90000","#FF0000","#F74242", "#FEA5A5","#D58C8C",
                                  "#AB4400","#E96D00","#F79042", "#FEC9A5","#D5A38C",
                                    "#FFB700","#E9C200","#F7BB42", "#FEF2A5","#D5C98C",
                                      "#00AB2E","#00E932","#42F76F", "#A5FEB8","#8CD596",
                                        "#AB0080","#E900B7","#F742D9", "#FEA5EF","#D58CC3",
                                            "#007BFF","#00A5E9","#42C3F7", "#A5D7FE","#8CBED5","#FFFFFF"};

    private List<Drawable> list;

    public class MyView extends RecyclerView.ViewHolder {

        public ImageView img;

        public MyView(View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.textview1);

}
    }
    public void setItemWidth(int parentWidth) {
        mItemWidth = parentWidth / 12
        ;
    }

    public RecyclerViewAdapter2(ArrayList<Drawable> horizontalList) {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);
        itemView.getLayoutParams().height = mItemWidth;
        itemView.getLayoutParams().width = mItemWidth;
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        holder.img.setImageDrawable(list.get(position));
        holder.img.getDrawable().setColorFilter(Color.parseColor(mColors[position ]), PorterDuff.Mode.SRC_IN);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}