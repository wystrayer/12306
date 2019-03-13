package com.example.a12306_final.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a12306_final.R;
import com.example.a12306_final.javabean.Ord_tickets;

import java.util.List;

/**
 * Created by 老汉 on 2017/12/10.
 */

public class Order_unfinished_Adapter extends RecyclerView.Adapter<Order_unfinished_Adapter.ListViewHolder>{
    private Context mContext;
    private List<Ord_tickets> mList;

    public Order_unfinished_Adapter(Context context, List<Ord_tickets> list) {
        mContext = context;
        mList = list;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public Order_unfinished_Adapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Order_unfinished_Adapter.ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.order_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final Order_unfinished_Adapter.ListViewHolder holder, int position) {

        Ord_tickets ord_tickets=mList.get(position);
        String data=ord_tickets.getUser_name()+"  "+ord_tickets.getUser_ID()+" "+ord_tickets.getFirstPlace()+"->"+ord_tickets.getLastPlace()+
                "  "+ord_tickets.getFirstPlace_Time()+" "+ord_tickets.getType();
        holder.tv.setText(data);

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

    private Order_unfinished_Adapter.OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(Order_unfinished_Adapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ListViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView_order_item);
        }
    }
}
