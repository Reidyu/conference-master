package com.example.franklin.conference.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.franklin.conference.R;
import com.example.franklin.conference.ui.meeting;

import java.util.List;

/**
 * Created by Franklin on 2018/3/31.
 */

public class meetingAdapter extends RecyclerView.Adapter<meetingAdapter.ViewHolder> {

    private Context mContext;
    private List<meeting> mymeetinglist;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView meetingImage;
        TextView meetingName;

        public ViewHolder (View view){
            super(view);
            cardView = (CardView)view;
            meetingImage = (ImageView) view.findViewById(R.id.meeting_image);
            meetingName = (TextView) view.findViewById(R.id.meeting_name);
        }
    }
    public meetingAdapter(List<meeting> meetingList){
        mymeetinglist = meetingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.meeting_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        meeting meet = mymeetinglist.get(position);
        holder.meetingName.setText(meet.getName());
        Glide.with(mContext).load(meet.getImagID()).into(holder.meetingImage);

    }
    @Override
    public int getItemCount(){
        return mymeetinglist.size();
    }
}
