package com.know.zjicmlib.adapter;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.know.zjicmlib.APP;
import com.know.zjicmlib.R;
import com.know.zjicmlib.adapter.base.ListAdapter;
import com.know.zjicmlib.adapter.base.ViewHolderr;
import com.know.zjicmlib.modle.bean.Ranker;

import java.util.List;

/**
 * Created by yang on 2016/6/7.
 */
public class RankListAdapter extends ListAdapter<Ranker> {

    public RankListAdapter(List<Ranker> data) {
        super(data, R.layout.item_rank);
    }
    public final static Object object = new Object();

    @Override
    public void bind(ViewHolderr holder, Ranker ranker) {
        holder.setText(R.id.tv_item_rank,ranker.getWord()+" ("+ranker.getRank()+")");

        /*if (ranker.getWord().equals("韩剧")) {
            Log.e("holder", " " + holder.getLayoutPosition() + "--->" + ranker.getWord());

            //if (holder.getLayoutPosition() == 0){

            synchronized (ViewHolderr.class){
                if (holder.getLayoutPosition() == 0){*/
                    Glide.with(APP.aContext)
                            .load("http://7xr6e8.com1.z0.glb.clouddn.com/LibImg/rank/"
                            +ranker.getWord()+"_" + holder.getLayoutPosition() + ".jpg")
                            .into(holder.getImageView(R.id.iv_item_rank));

                //}
            /*}
        }*/
    }
}
