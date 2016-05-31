package com.know.zjicmlib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.know.zjicmlib.R;
import com.know.zjicmlib.listener.OnBooClickListener;
import com.know.zjicmlib.modle.bean.Boo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by know on 2016/4/12.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    List<Boo> boos;
    Context context;
    OnBooClickListener onBooClickListener;
    public SearchListAdapter(Context context,List<Boo> boos) {
        this.boos = boos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_boos_search, parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Boo boo = boos.get(position);
        holder.booNameText.setText(boo.getName());
        holder.authorText.setText(boo.getAuthor());
        holder.booIdText.setText("索书号："+boo.getId());
        holder.booFZMText.setText("剩余/馆藏："+boo.getFz()+"/"+boo.getFm());

        holder.itemView.setOnClickListener((view)->{
            onBooClickListener.onBooClick(holder.getLayoutPosition());
        });
    }

    @Override
    public int getItemCount() {
        return boos.size();
    }

    public void setBooClickListener(OnBooClickListener listener){
        onBooClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_search_booname)TextView booNameText;
        @Bind(R.id.tv_search_author)TextView authorText;
        @Bind(R.id.tv_search_booid)TextView booIdText;
        @Bind(R.id.tv_fzm)TextView booFZMText;
        View oneBooView;
        public ViewHolder(View itemView) {
            super(itemView);
            oneBooView = itemView;
            ButterKnife.bind(this, oneBooView);
        }

    }

}
