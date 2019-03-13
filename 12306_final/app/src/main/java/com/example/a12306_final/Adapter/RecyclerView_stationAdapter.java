package com.example.a12306_final.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a12306_final.R;

import java.util.List;

/**
 * Created by Strayer on 2017/11/29.
 */
/*
public class RecyclerView_stationAdapter extends RecyclerView.Adapter<RecyclerView_stationAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mList;

    public RecyclerView_stationAdapter(station_selection mainActivity, List<String> list) {
        this.mContext = mainActivity;
        this.mList = list;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
           // textView = (TextView) itemView.findViewById(R.id.textView_station);
        }
    }


@Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
    ViewHolder holder = new ViewHolder(view);
    return holder;
}


    public void onBindViewHolder(ViewHolder holder, int position) {
        //给空间赋值

        String station = mList.get(position);
        holder.textView.setText(station);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}*/

public class RecyclerView_stationAdapter extends RecyclerView.Adapter<RecyclerView_stationAdapter.ListViewHolder>{

    private Context mContext;
    private List<String> mList;

    public RecyclerView_stationAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.station_recycler_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        holder.tv.setText(mList.get(position));

        View itemView = ((LinearLayout) holder.itemView).getChildAt(0);

        if (mOnItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ListViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView_station);
        }
    }

}