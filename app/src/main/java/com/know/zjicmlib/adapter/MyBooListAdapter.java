package com.know.zjicmlib.adapter;

import android.widget.Button;

import com.know.zjicmlib.R;
import com.know.zjicmlib.adapter.base.ListAdapter;
import com.know.zjicmlib.adapter.base.ViewHolderr;
import com.know.zjicmlib.listener.OnBooClickListener;
import com.know.zjicmlib.modle.bean.Boo;

import java.util.List;

/**
 * Created by know on 2016/4/24.
 */
public class MyBooListAdapter extends ListAdapter<Boo> {


    OnBooClickListener listener;

    public MyBooListAdapter(List<Boo> data) {
        super(data, R.layout.item_boos_my);
    }

    public void bind(ViewHolderr holderr,Boo boo){

        holderr.setText(R.id.tv_me_name,boo.getName()+"\n"+boo.getAuthor()
                +"\n"+boo.getFz()+"~"+boo.getFm())
                .setButton(R.id.bt_me_continue, "续借");

        holderr.getButton(R.id.bt_me_continue).setOnClickListener(view -> {
            listener.onBooClick(holderr.getLayoutPosition());
        });
    }

    public void setOnBooClickListener(OnBooClickListener listener){
        this.listener = listener;
    }

}
