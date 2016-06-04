package com.know.zjicmlib.adapter;

import com.know.zjicmlib.R;
import com.know.zjicmlib.adapter.base.ListAdapter;
import com.know.zjicmlib.adapter.base.ViewHolderr;
import com.know.zjicmlib.modle.bean.Boo;

import java.util.List;

/**
 * Created by yang on 2016/6/2.
 */
public class SearchListAdapterA extends ListAdapter<Boo> {

    public SearchListAdapterA(List<Boo> data) {
        super(data, R.layout.item_boos_search);
    }

    @Override
    public void bind(ViewHolderr holder, Boo boo) {

        holder.setText(R.id.tv_search_booname,boo.getName())
                .setText(R.id.tv_search_author,boo.getAuthor())
                .setText(R.id.tv_search_booid,"索书号："+boo.getId())
                .setText(R.id.tv_fzm,"剩余/馆藏："+boo.getFz()+"/"+boo.getFm());

    }
}
