package com.example.avto.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by avto on 3/23/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>{
    private List<Liga> ligaList;
    private Context mContext;

    public RecyclerAdapter(Context context, List<Liga> ligaList) {
        this.ligaList = ligaList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.liga_layout, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Liga liga = ligaList.get(i);

        //Setting text view title
        customViewHolder.textView.setText(liga.name);

        customViewHolder.textView.setTag(customViewHolder);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position = holder.getPosition();
                Liga ligaItem = ligaList.get(position);

                Toast.makeText(mContext,  liga.name, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,FixturesActivity.class);
                Bundle extras = new Bundle();
                extras.putString("href", liga.href);
                intent.putExtras(extras);
                mContext.startActivity(intent);

            }
        };
        customViewHolder.textView.setOnClickListener(clickListener);

    }

    @Override
    public int getItemCount() {
        return (null != ligaList ? ligaList.size() : 0);
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView;
        public String href;
        public CustomViewHolder(View view) {
            super(view);

            this.textView = (TextView) view.findViewById(R.id.tv_liga_name);
        }
    }
}
