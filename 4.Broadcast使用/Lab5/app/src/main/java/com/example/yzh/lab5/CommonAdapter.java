package com.example.yzh.lab5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import static android.location.Location.convert;

/**
 * Created by YZH on 2017/10/22.
 */

public abstract class CommonAdapter<M> extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List<Map<String, Object>> mDatas;
    private OnItemClickListener onItemClickListener;
    public CommonAdapter(Context context, int layoutId, List<Map<String, Object>> datas)
    {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });

        }
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount(){ return mDatas.size();}

    public void convert(ViewHolder holder, Map<String,Object> s)
    {

    }

    public List<Map<String,Object>> getList() {
        return mDatas;
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener
    {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemclick)
    {
        this.onItemClickListener =  itemclick;
    }
}
