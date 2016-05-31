package com.know.zjicmlib.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.know.zjicmlib.listener.OnBooClickListener;
import com.know.zjicmlib.modle.bean.Boo;

import java.util.List;

/**
 * Created by know on 2016/4/23.
 */
public abstract class ListAdapter<T> extends RecyclerView.Adapter<ViewHolderr>{

    public List<T> data;
    int layoutId;

    public ListAdapter(List<T> data, int layoutId) {
        this.data = data;
        this.layoutId = layoutId;
    }

    OnBooClickListener onItemClickListener;

    public void setOnItemClickListener(OnBooClickListener listener){
        onItemClickListener = listener;
    }

    @Override
    public ViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);

        ViewHolderr holderr = new ViewHolderr(view);

        return holderr;
    }

    @Override
    public void onBindViewHolder(ViewHolderr holder, int position) {

        T t = data.get(position);

        bind(holder,t);

        if(onItemClickListener != null){
            holder.itemView.setOnClickListener(view ->
                onItemClickListener.onBooClick(holder.getLayoutPosition())
            );
        }

    }


    public abstract void bind(ViewHolderr holder,T t);

    @Override
    public int getItemCount() {
        return data.size();
    }
}
