package com.example.avto.myapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by avto on 3/28/2016.
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.CustomViewHolder>{
    private List<Team> teamList;
    private Context mContext;
    public TeamAdapter(List<Team> teamList,Context mContext){
        this.teamList=teamList;
        this.mContext=mContext;
    }
    @Override
    public TeamAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_layout, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TeamAdapter.CustomViewHolder holder, int position) {
        final Team myTeam = teamList.get(position);

        //Setting text view title
        Picasso.with(mContext)
                .load(myTeam.image)
                .error(R.drawable.ball)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img);
        holder.team.setText(myTeam.team_name);
        holder.pl.setText(myTeam.team_player);



    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView img;
        protected TextView team;
        protected TextView pl;
        public CustomViewHolder(View view) {
            super(view);

            this.img = (ImageView) view.findViewById(R.id.iv_team_image);
            this.team = (TextView) view.findViewById(R.id.tv_team);
            this.pl = (TextView) view.findViewById(R.id.tv_team_players);
        }
    }
}
