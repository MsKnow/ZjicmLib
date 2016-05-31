package com.know.zjicmlib.adapter;

import com.know.zjicmlib.R;
import com.know.zjicmlib.adapter.base.ListAdapter;
import com.know.zjicmlib.adapter.base.ViewHolderr;
import com.know.zjicmlib.modle.bean.Boo;

import java.util.List;

/**
 * Created by know on 2016/5/6.
 */
public class OneBooListAdapter extends ListAdapter<Boo>{

    public OneBooListAdapter(List<Boo> data, int layoutId) {
        super(data, layoutId);
    }

    @Override
    public void bind(ViewHolderr holder, Boo boo) {

        holder.setText(R.id.tv_boo_ones,boo.getId()+"\n"+boo.getFz())
                .setText(R.id.tv_boo_onesr,boo.getFm());
    }
}
