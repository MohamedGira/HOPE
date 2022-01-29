package com.helloworld.uifirs222;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.helloworld.uifirs222.R;

import com.helloworld.uifirs222.Models.nearhospitalmodel;
import com.helloworld.uifirs222.ui.main.Hospitalfrag;

import java.util.ArrayList;

public class rewiev extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewiev);
        ListView rv = findViewById(R.id.lsitrevie);
        final ArrayList<revewmodel> rev= new ArrayList<revewmodel>();
        final rewiev.MyCustomAdapterrrr nearadpter= new MyCustomAdapterrrr(rev);
        rv.setAdapter(nearadpter);
      //  nearadpter.add(new revewmodel("this app is good","01227890987","5"));

    }

    private class MyCustomAdapterrrr extends BaseAdapter {


        ArrayList<revewmodel> nearitem = new ArrayList<revewmodel>();
        MyCustomAdapterrrr(ArrayList<revewmodel> Items){
            this.nearitem= Items;
        }

        @Override
        public int getCount() {return nearitem.size();}

        @Override
        public Object getItem(int position) { return nearitem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view1= inflater.inflate(R.layout.reviewitem,null);


            TextView textView=(TextView) view1.findViewById(R.id.reviweitself);
            TextView textView2=(TextView) view1.findViewById(R.id.rating);
            TextView textview3=   view1.findViewById(R.id.phonenumber);
            return view1;
        }
    }



}
