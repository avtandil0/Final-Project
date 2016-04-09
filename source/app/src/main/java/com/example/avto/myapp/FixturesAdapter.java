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
 * Created by avto on 3/26/2016.
 */
public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.CustomViewHolder> {
    private List<MyFixtures> fixtureList;
    private Context mContext;
    public FixturesAdapter(List<MyFixtures> fixtureList,Context mContext){
        this.fixtureList=fixtureList;
        this.mContext=mContext;
    }
    @Override
    public FixturesAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixtures_layout, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FixturesAdapter.CustomViewHolder holder, int position) {
        final MyFixtures myFixtures = fixtureList.get(position);

        //Setting text view title
        holder.st.setText(myFixtures.status);
        holder.fr.setText(myFixtures.firstClub);
        holder.sec.setText(myFixtures.secondClub);
        holder.frclsc.setText(myFixtures.firstClubScore);
        holder.secclsc.setText(myFixtures.secondClubScore);
        holder.t.setText(myFixtures.time);

        holder.fr.setTag(holder);
        holder.sec.setTag(holder);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position = holder.getPosition();
                MyFixtures fixturesItem = fixtureList.get(position);

                Toast.makeText(mContext, (CharSequence) myFixtures.getFirstClubUrl, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,TeamAcivity.class);
                Bundle extras = new Bundle();
                extras.putString("href", myFixtures.getFirstClubUrl);
                intent.putExtras(extras);
                mContext.startActivity(intent);

            }
        };
        View.OnClickListener clickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position = holder.getPosition();
                MyFixtures fixturesItem = fixtureList.get(position);

                Toast.makeText(mContext, (CharSequence) myFixtures.getSecondClubUrl, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,TeamAcivity.class);
                Bundle extras = new Bundle();
                extras.putString("href", myFixtures.getSecondClubUrl);
                intent.putExtras(extras);
                mContext.startActivity(intent);

            }
        };
        holder.sec.setOnClickListener(clickListener1);
        holder.fr.setOnClickListener(clickListener);


    }

    @Override
    public int getItemCount() {
        return fixtureList.size();
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView st;
        protected TextView fr;
        protected TextView sec;
        protected TextView frclsc;
        protected TextView secclsc;
        protected TextView t;
        public CustomViewHolder(View view) {
            super(view);

            this.st = (TextView) view.findViewById(R.id.tv_status);
            this.fr = (TextView) view.findViewById(R.id.tv_club_one);
            this.sec = (TextView) view.findViewById(R.id.tv_club_two);
            this.frclsc = (TextView) view.findViewById(R.id.tv_score_one);
            this.secclsc = (TextView) view.findViewById(R.id.tv_score_two);
            this.t = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}
