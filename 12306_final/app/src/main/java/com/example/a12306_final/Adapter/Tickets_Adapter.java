package com.example.a12306_final.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a12306_final.R;
import com.example.a12306_final.javabean.Tickets;

import java.util.List;

/**
 * Created by 老汉 on 2017/12/5.
 */

public class Tickets_Adapter extends RecyclerView.Adapter<Tickets_Adapter.ListViewHolder>{
    private Context mContext;
    private List<Tickets> mList;

    public Tickets_Adapter(Context context, List<Tickets> list) {
        mContext = context;
        mList = list;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public Tickets_Adapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Tickets_Adapter.ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.station_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        String shangwu,wuzuo,yideng,erdeng;
        Tickets tickets=mList.get(position);
        holder.FirstStation_time.setText(tickets.getFirstPlace_Time());
        holder.FirstStation_name.setText(tickets.getFirstPlace());
        holder.LastStation_time.setText(tickets.getLastPlace_Time());
        holder.LastStation_name.setText(tickets.getLastPlace());
        holder.LastStation_type.setText(tickets.getLastPlace_Type());
        holder.station_useTime.setText(tickets.getUseTime());
        holder.station_id.setText(tickets.getStationID());
        if(tickets.getShangwu_num().intValue()>30){
            shangwu="商务:有";
        }else if(tickets.getShangwu_num().intValue()==0) {
            shangwu="商务:无";
        } else {
            shangwu="商务:"+tickets.getShangwu_num().intValue()+"张";
        }
        if(tickets.getYideng_num().intValue()>30){
            yideng="一等:有";
        }else if(tickets.getYideng_num().intValue()==0){
            yideng="一等:无";
        }else{
            yideng="一等:"+tickets.getYideng_num().intValue()+"张";
        }
        if(tickets.getErdeng_num().intValue()>30){
            erdeng="二等:有";
        }else if(tickets.getErdeng_num().intValue()==0){
            erdeng="二等:无";
        }else{
            erdeng="二等:"+tickets.getErdeng_num().intValue()+"张";
        }
        if(tickets.getWuzuo_num().intValue()>30){
            wuzuo="无座:有";
        }else if(tickets.getWuzuo_num().intValue()==0){
            wuzuo="无座:无";
        }else{
            wuzuo="无座:"+tickets.getWuzuo_num().intValue()+"张";
        }
        if(tickets.getFirstPlace_Type().equals("始")){
            holder.FirstStation_type.setBackgroundResource(R.color.stationType_shi);
            holder.FirstStation_type.setText(tickets.getFirstPlace_Type());
        }else if(tickets.getFirstPlace_Type().equals("过")){
            holder.FirstStation_type.setBackgroundResource(R.color.stationType_guo);
            holder.FirstStation_type.setText(tickets.getFirstPlace_Type());
        }else{
            holder.FirstStation_type.setBackgroundResource(R.color.stationType_zhong);
            holder.FirstStation_type.setText(tickets.getFirstPlace_Type());
        }
        if(tickets.getLastPlace_Type().equals("始")){
            holder.LastStation_type.setBackgroundResource(R.color.stationType_shi);
            holder.LastStation_type.setText(tickets.getLastPlace_Type());
        }else if(tickets.getLastPlace_Type().equals("过")){
            holder.LastStation_type.setBackgroundResource(R.color.stationType_guo);
            holder.LastStation_type.setText(tickets.getLastPlace_Type());
        }else{
            holder.LastStation_type.setBackgroundResource(R.color.stationType_zhong);
            holder.LastStation_type.setText(tickets.getLastPlace_Type());
        }
        holder.seat_zhan.setText(wuzuo);
        holder.seat_shangwu.setText(shangwu);
        holder.seat_2.setText(erdeng);
        holder.seat_1.setText(yideng);

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

    private Tickets_Adapter.OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(Tickets_Adapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView station_id,station_useTime;
        TextView FirstStation_type,FirstStation_name,FirstStation_time;
        TextView LastStation_type,LastStation_name,LastStation_time;
        TextView seat_shangwu,seat_1,seat_2,seat_zhan;

        public ListViewHolder(View itemView) {
            super(itemView);
            station_id = (TextView) itemView.findViewById(R.id.station_id);
            station_useTime=(TextView)itemView.findViewById(R.id.station_useTime);
            FirstStation_name=(TextView)itemView.findViewById(R.id.FirstStation_name);
            FirstStation_type=(TextView)itemView.findViewById(R.id.FirstStation_type);
            FirstStation_time=(TextView)itemView.findViewById(R.id.FirstStation_time);
            LastStation_name=(TextView)itemView.findViewById(R.id.LastStation_name);
            LastStation_type=(TextView)itemView.findViewById(R.id.LastStation_type);
            LastStation_time=(TextView)itemView.findViewById(R.id.LastStation_time);
            seat_1=(TextView)itemView.findViewById(R.id.seat_1);
            seat_2=(TextView)itemView.findViewById(R.id.seat_2);
            seat_shangwu=(TextView)itemView.findViewById(R.id.seat_shangwu);
            seat_zhan=(TextView)itemView.findViewById(R.id.seat_zhan);
        }
    }
}
