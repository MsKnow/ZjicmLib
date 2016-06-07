package com.know.zjicmlib.adapter;

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

    @Override
    public void bind(ViewHolderr holder, Ranker ranker) {
        holder.setText(R.id.tv_item_rank,ranker.getWord()+"("+ranker.getRank()+")");
    }
}
